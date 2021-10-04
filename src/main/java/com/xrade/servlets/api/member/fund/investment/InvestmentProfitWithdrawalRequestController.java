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
import com.xrade.entity.MemberEntity;
import com.xrade.entity.MemberInterestPaymentAccountEntity;
import com.xrade.entity.MemberInvestmentProfitWithdrawalRequestEntity;
import com.xrade.entity.MemberWithdrawRequestEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.DatabaseConnection;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.validator.factory.ApiRequestValidatorFactory;
import com.xrade.web.AppConfig;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class InvestmentProfitWithdrawalRequestController
 */
@WebServlet("/api/v1/member/fund/investment-profit/withdrawal")
public class InvestmentProfitWithdrawalRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvestmentProfitWithdrawalRequestController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
						MemberInvestmentProfitWithdrawalRequestEntity entity = null;
						// Validate the request
						try {
							entity = ApiRequestValidatorFactory.getMakeInvestmentProfitWithdrawRequestValidator()
									.validate((JSONObject) request.getAttribute("body"));

							if (entity != null) {
								
								String[] creds = (String[]) request.getAttribute("credentials");
								int memberId = Integer.parseInt(creds[2]);
								
								//Check if there was not an unfullfilled request
								//Get unfilled withdraw request [not deposit]
								/*MemberInvestmentProfitWithdrawalRequestEntity unfilledRequest = MysqlDaoFactory
										.getMemberInvestmentProfitWithdrawRequestDaoRepository()
										.findUnfilledByMemberId(memberId);*/
								
								//If there is al least on withdraw request not filled
								//Send forbidden header to header
								
								//The above rule doesn't work anyore
								//The member can member can make multiple withdrawal requets
								
								MemberEntity member = MysqlDaoFactory
										.getMemberDaoRepository()
										.find(memberId);
								
								//[START] Validate member
								if(member== null){
									
									response.setStatus(HttpServletResponse.SC_FORBIDDEN);
									HttpJsonResponse.create(response)
									.addData("message", 
											"Unable to handle the request")
									.build()
									.send();
									
									return;
								}
								//[END] Validate member
								
								MemberInterestPaymentAccountEntity interestAccount = 
										(MemberInterestPaymentAccountEntity) member.getInterestAccount();
								if(interestAccount== null){
									//No need of this
									//A member can ask many withdrawal requel
									//until his amount is unsufiicient
									
									
									response.setStatus(HttpServletResponse.SC_FORBIDDEN);
									HttpJsonResponse.create(response)
									.addData("message", 
											"Unable to handle the request")
									.build()
									.send();
								}else{
									
									//Set the amount before and the mount after here
									/*MemberInterestPaymentAccountEntity account = (MemberInterestPaymentAccountEntity) MysqlDaoFactory
											.getMemberDaoRepository()
											.find(memberId).getInterestAccount();*/
									
									
									//The interest accountis not null
									//update data
									entity.setAmountBefore(interestAccount.getAmount());
									entity.setMemberInterestAccountId(interestAccount.getId());
									double tmp = interestAccount.getAmount() - entity.getAmount();
									
									//Check if we can can withdrawal the given amount
									if(tmp >=0){
										if(entity.getAmount() >= AppConfig.BASE_WITHDRAWAL_AMOUNT ){
											entity.setAmountAfter( tmp );
											
											// Process entiy and save it to data base
											DatabaseConnection.getInstance().setAutoCommit(false);
											boolean saved = MysqlDaoFactory.getMemberInvestmentProfitWithdrawRequestDaoRepository().create(entity);
											// return the entity as json with data updated
											if (saved) {
												
												//Create a withdrawal request on investment profit
												MemberWithdrawRequestEntity withdrawalRequest = new MemberWithdrawRequestEntity();
												withdrawalRequest.setPayment(entity.getPayment());
												withdrawalRequest.setAmount(entity.getAmount());
												withdrawalRequest.setAmountBefore(0);
												withdrawalRequest.setAmountAfter(0);
												withdrawalRequest.setMetadata(entity.getMetadata());
												withdrawalRequest.setMemberAccountId( ( (MemberAccountEntity) member.getAccount()).getId() );
												withdrawalRequest.setAccountType("profit");
												
												JSONObject extra = new JSONObject();
												extra.put("name",  member.getFirstname() );
												extra.put("acc", interestAccount.getId());												
												withdrawalRequest.setExtra(extra.toString());
												
												saved = MysqlDaoFactory.getMemberWithdrawRequestDaoRepository().create(withdrawalRequest);
												if(saved){
													DatabaseConnection.getInstance().commit();
													DatabaseConnection.getInstance().setAutoCommit(true);
													
													response.setStatus(HttpServletResponse.SC_CREATED);
													HttpJsonResponse.create(response).send(entity.toJson());
												}else{
													//We can't create the withdrawal
													//request send error
													
													DatabaseConnection.getInstance().setAutoCommit(true);
													
													response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
													HttpJsonResponse.create(response)
													.addData("message", 
															"Unable to proces the withdrawal request. Please try agin later")
													.build()
													.send();
													
												}
												
												
											}else{
												//The withdraw request fails
												//when trying to update data in the database
												DatabaseConnection.getInstance().setAutoCommit(true);
												
												response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
												HttpJsonResponse.create(response)
												.addData("message", 
														"Unable to proces the withdrawal request. Please try agin later")
												.build()
												.send();
											}
										}else{
											//Insuficient amount on the account
											//send a forbidenr equest 
											response.setStatus(HttpServletResponse.SC_FORBIDDEN);
											HttpJsonResponse.create(response)
											.addData("message",
													"You need to have a base amount of "+ AppConfig.BASE_WITHDRAWAL_AMOUNT +" USD")
											.build()
											.send();
										}
									}else{
										//The account may dig into a negation value
										//Send forbidden response
										response.setStatus(HttpServletResponse.SC_FORBIDDEN);
										HttpJsonResponse.create(response)
										.addData("message", "You can't withdrawing more than what you have.")
										.build()
										.send();
									}
									

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

					public void onDenied(DefaultResourceException e, ServletContext context, HttpServletRequest request,
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
