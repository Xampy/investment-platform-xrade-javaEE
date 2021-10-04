package com.xrade.servlets.api.member.fund;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberDepositRequestEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.entity.MemberWithdrawRequestEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.validator.factory.ApiRequestValidatorFactory;
import com.xrade.web.AppConfig;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class MakeWithdrawRequestController
 */
@WebServlet("/api/v1/member/fund/investment/withdrawal")
public class MakeWithdrawRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeWithdrawRequestController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
						MemberWithdrawRequestEntity entity = null;
						// Validate the request
						try {
							entity = ApiRequestValidatorFactory.getMakeWithdrawRequestValidator()
									.validate((JSONObject) request.getAttribute("body"));
							
							//[START] processing entity and save
							if (entity != null) {
								
								String[] creds = (String[]) request.getAttribute("credentials");
								int memberId = Integer.parseInt(creds[2]);
								
								//Check if there was not an unfullfilled request
								//Get unfilled withdraw request [not deposit]
								/*MemberWithdrawRequestEntity unfilledRequest = MysqlDaoFactory
										.getMemberWithdrawRequestDaoRepository()
										.findUnfilledByMemberId(memberId);*/
								
								//If there is al least on withdraw request not filled
								//Send forbidden header to header
								//if(unfilledRequest != null){
								
								MemberEntity member = MysqlDaoFactory.getMemberDaoRepository().find(memberId);
								if(member == null){
									response.setStatus(HttpServletResponse.SC_FORBIDDEN);
									HttpJsonResponse.create(response)
									.addData("message", 
											"Unable to handle the request")
									.build()
									.send();
								}
								MemberAccountEntity account = (MemberAccountEntity) member.getAccount();
								
								if(account == null){
									response.setStatus(HttpServletResponse.SC_FORBIDDEN);
									HttpJsonResponse.create(response)
									.addData("message", 
											"Unable to handle the request")
									.build()
									.send();
								}else{
									
									//Set the amount before and the mount after here
									/*MemberAccountEntity account = (MemberAccountEntity) MysqlDaoFactory
											.getMemberDaoRepository()
											.find(memberId).getAccount();*/
									
									//The account is not null
									
									
									//[START] withdrawal need to processed befave have
									//after and before value
									entity.setAmountBefore(0);
									entity.setMemberAccountId(account.getId());
									//[END] withdrawal need to processed befave have
									//after and before value
									
									JSONObject extra = new JSONObject();
									extra.put("name",  member.getFirstname() );
									extra.put("acc", account.getId());
									System.out.println(extra.toString());
									
									entity.setExtra(extra.toString());
									
									
									double tmp = account.getAmount() - entity.getAmount();
									
									if(tmp > 0 ){
										entity.setAmountAfter( 0 );
										
										// Process entiy and save it to data base
										entity.setAccountType("invest");
										boolean saved = MysqlDaoFactory.getMemberWithdrawRequestDaoRepository().create(entity);
										// return the entity as json with data updated
										if (saved) {
											response.setStatus(HttpServletResponse.SC_CREATED);
											HttpJsonResponse.create(response).send(entity.toJson());
										}else{
											//The withdraw request fails
											//when trying to update data in the database
											response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
											HttpJsonResponse.create(response)
											.addData("message", 
													"Unable to proces the with draw request. Please try agin later")
											.build()
											.send();
										}
									}else{
										//Insuficient amount on the account
										//send a forbidenr equest 
										response.setStatus(HttpServletResponse.SC_FORBIDDEN);
										HttpJsonResponse.create(response)
										.addData("message", 
												"You hava no sufficient amount for withdrawing." +
												"You need to have a base amount of " + AppConfig.BASE_AMOUNT + "$")
										.build()
										.send();
									}
									
								}

							}else{
								//Entity is null send error response
								response.setStatus(HttpServletResponse.SC_FORBIDDEN);
								HttpJsonResponse.create(response)
								.addData("message", 
										"You hava no sufficient amount for withdrawing." +
										"You need to have a base amount of " + AppConfig.BASE_AMOUNT + "$")
								.build()
								.send();
							}
							//[END] processing entity and save

						} catch (Exception e) {
							e.printStackTrace();
							//Validation catch scope
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
