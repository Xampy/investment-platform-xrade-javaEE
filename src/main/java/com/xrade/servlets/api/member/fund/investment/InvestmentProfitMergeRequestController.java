package com.xrade.servlets.api.member.fund.investment;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberInterestPaymentAccountEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.entity.MemberInvestmentProfitMergeRequestEntity;
import com.xrade.entity.MemberInvestmentProfitWithdrawalRequestEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.DatabaseConnection;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.validator.factory.ApiRequestValidatorFactory;
import com.xrade.web.AppConfig;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class InvestmentProfitMergeRequestController
 */
@WebServlet("/api/v1/member/fund/investment-profit/merge")
public class InvestmentProfitMergeRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InvestmentProfitMergeRequestController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		new ResourceGuard(this.getServletContext(), request, response, 
				new ResourceGrantedStateListener(){

					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						
						// Buil our entity
						MemberInvestmentProfitMergeRequestEntity entity = null;
						// Validate the request
						try {
							
							entity = ApiRequestValidatorFactory.getMakeInvestmentProfitMergeRequestValidator()
									.validate((JSONObject) request.getAttribute("body"));

							if (entity != null) {
								
								String[] creds = (String[]) request.getAttribute("credentials");
								int memberId = Integer.parseInt(creds[2]);
								
								//Get the member
								MemberEntity member = MysqlDaoFactory.getMemberDaoRepository().find(memberId);
								if(member != null){
									//The two account
									MemberAccountEntity account = (MemberAccountEntity) member.getAccount();
									MemberInterestPaymentAccountEntity interestAccount = 
											(MemberInterestPaymentAccountEntity) member.getInterestAccount();
									
									if(interestAccount != null && account != null){
										
										//[ START [Description Block] control 20 work days passed before making a new
										//profit merge request]
										
										//Get the latest profit merge request
										MemberInvestmentProfitMergeRequestEntity latestMerge = 
												MysqlDaoFactory.getMemberInvestmentProfitMergeRequestDaoRepository()
												.getLatestProfitMergeByInterestAccount(interestAccount.getId());
										
										if(latestMerge != null){
											//Get the elapsed since the the latest profit merge
											int day_eleapsed = 
													MysqlDaoFactory.getMemberInvestmentProfitMergeRequestDaoRepository()
													.getEleapsedDayFromTheLatestMerge(latestMerge);
											
											System.out.println("Day elepsed " + day_eleapsed);
											//Validate the eleapsed date
											if(day_eleapsed < 0){
												//Send expectation fault
												response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
												HttpJsonResponse.create(response)
												.addData("message", 
														"Unable to proces the merge request. Please try agin later")
												.build()
												.send();
												return;
												
											}else if (day_eleapsed < 28){
												//We did not complete tje paused period
												response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
												HttpJsonResponse.create(response)
												.addData("message", 
														"You don't vomplete your 20 days break"
													+   "\nYour last merge request was on " + latestMerge.getCreatedAt())
												.build()
												.send();
												
												return;
											}
										}
										//Else the last merge is null we continue
										//[ END [Description Block] control 20 work days passed before making a new profit merge request]
										
										//Verify the interest accoutnt amount
										double interestTmp = interestAccount.getAmount() - entity.getAmount();
										if(interestTmp >= 0){
											if(entity.getAmount() >= AppConfig.BASE_AMOUNT){
												
												//Update values
												entity.setAmountBefore(account.getAmount());
												
												//Increase the investment account 
												account.setAmount(account.getAmount() + entity.getAmount());
												
												interestAccount.setAmount(interestAccount.getAmount() - entity.getAmount());
												entity.setAmountAfter(account.getAmount());
												entity.setMemberInterestAccountId(interestAccount.getId());
												
												//Persit updates into the database
												DatabaseConnection.getInstance().setAutoCommit(false);
												
												//Update the member investment account
												if(MysqlDaoFactory.getMemberAccountDaoRepository().updateAmount(account)){
													//Create the merge request
													if(MysqlDaoFactory.getMemberInvestmentProfitMergeRequestDaoRepository().create(entity)){
														//Update the member investment profit account
														if(MysqlDaoFactory.getMemberInterestPaymentAccountDaoRepository()
																.updateAmount(interestAccount)){
															
															DatabaseConnection.getInstance().commit();
															DatabaseConnection.getInstance().setAutoCommit(true);
															//Get the updated member with its updated accounts
															member = MysqlDaoFactory.getMemberDaoRepository().find(memberId);
															
															HttpJsonResponse.create(response).send(member.toJson());
														}else{
															//Unable to update the investment profit account
															//Unable to create the merge request data
															DatabaseConnection.getInstance().rollback();
															DatabaseConnection.getInstance().setAutoCommit(true);
															
															response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
															HttpJsonResponse.create(response)
															.addData("message", 
																	"Unable to proces the merge request. Please try agin later")
															.build()
															.send();
														}
														
														
													}else{
														//Unable to create the merge request data
														DatabaseConnection.getInstance().rollback();
														DatabaseConnection.getInstance().setAutoCommit(true);
														
														response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
														HttpJsonResponse.create(response)
														.addData("message", 
																"Unable to proces the merge request. Please try agin later")
														.build()
														.send();
													}
													
												}else{
													//unable to update the account amount
													DatabaseConnection.getInstance().rollback();
													DatabaseConnection.getInstance().setAutoCommit(true);
													
													response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
													HttpJsonResponse.create(response)
													.addData("message", 
															"Unable to proces the merge request. Please try agin later")
													.build()
													.send();
												}
												
												
											}else{
												//We have not the sufficent amount to
												//merge to the investment account
												response.setStatus(HttpServletResponse.SC_FORBIDDEN);
												HttpJsonResponse.create(response)
												.addData("message", 
														"You hava no sufficient investment profit amount to merge." +
														"You need to have a base amount of " + AppConfig.BASE_AMOUNT + "$")
												.build()
												.send();
											}
										}else{
											//We don't have the sufficient amount for the request
											//send forbidden status
											response.setStatus(HttpServletResponse.SC_FORBIDDEN);
											HttpJsonResponse.create(response)
											.addData("message", 
													"You can't merge more than you profit amount.Please try agin later")
											.build()
											.send();
										}
									}else{
										//One of our accounts is ull
										response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
										HttpJsonResponse.create(response)
										.addData("message", 
												"Unable to proces the merge request. Please try agin later")
										.build()
										.send();
									}
									
									
								}else{
									//We c'ant find member
									response.setStatus(HttpServletResponse.SC_FORBIDDEN);
									HttpJsonResponse.create(response)
									.addData("message", 
											"Unable to proces the merge request. Please try agin later")
									.build()
									.send();
								}

							}

						} catch (Exception e) {
							e.printStackTrace();
							//Validation catch scope
							//We have error while parsin the request body
							response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							HttpJsonResponse.create(response)
							.addData("message", e.getMessage())
							.build()
							.send();

						}
						
					}

					public void onDenied(DefaultResourceException e, 
							ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						HttpJsonResponse.create(response)
						.addData("message", e.getMessage())
						.build()
						.send();
						
					}
			
				}
		);
	}

}
