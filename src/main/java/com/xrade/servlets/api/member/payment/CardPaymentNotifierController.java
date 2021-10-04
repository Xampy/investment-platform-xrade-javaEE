package com.xrade.servlets.api.member.payment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberAccountSponsorshipPaymentEntity;
import com.xrade.entity.MemberCardTransactionEntity;
import com.xrade.entity.MemberDepositRequestEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.entity.MemberSponsorshipPaymentAccountEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.DatabaseConnection;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.service.mail.factory.MailSenderFactory;
import com.xrade.service.mail.sender.DepositRequestMailSender;
import com.xrade.service.member.MemberAccountManagementService;
import com.xrade.web.AppConfig;
import com.xrade.web.HttpJsonResponse;
import com.xrade.web.XPaymentMethodType;

/**
 * Servlet implementation class CardPaymentNotifierController
 */
@WebServlet("/api/v1/fill-account/deposit/bank/check")
public class CardPaymentNotifierController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CardPaymentNotifierController() {
        super();
        // TODO Auto-generated constructor stub
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
						
						//Get the body
						JSONObject body = (JSONObject) request.getAttribute("body");
						try{
							String intentId = body.getString("session");
							
							//Find the transaction with the given intent
							MemberCardTransactionEntity transaction = 
									MysqlDaoFactory.getMemberCardTransactionDaoRepository().findBySessionId(intentId);
							
							//Validate the transaction and it may not be filled
							if(transaction != null && transaction.isFilled() == false){
								//Get the transaction intent from ou Card api
								Stripe.apiKey = 
										"sk_test_51H9HTiI0XdkUGFe3dkNdJFD0pkXiQXmDYzdEoYeL8VVOSnHivCmKi7Du9DFgRr7e8mFfuXQg9o9N58GVoOxb23Wc00ZgjS16xr";

								try {
									//Get the intent
									Session session =
									  Session.retrieve(
									    transaction.getPaymentSessionId()
									  );
									
									//Check the payment status if only the 
									//transaction filled status is false
									if(session.getPaymentStatus().equals("paid") && (transaction.isFilled() == false)){
										//Update the member account
										String[] creds = (String[]) request.getAttribute("credentials");
										int memberId = Integer.parseInt(creds[2]);
										
										MemberEntity member = MysqlDaoFactory.getMemberDaoRepository().find(memberId);
										if(member == null){
											response.setStatus(HttpServletResponse.SC_FORBIDDEN);
											HttpJsonResponse.create(response)
											.addData("message", "Unable to handle the request")
											.build()
											.send();
											
											return;
										}
										
										
										
	
										//UPdate the account in the database
										//start transaction
										//Update the member amount
										MemberAccountManagementService accountService = new MemberAccountManagementService();
										boolean updated = accountService.updateAmountByPayment(
												XPaymentMethodType.Card, transaction, member);
										if(updated){
											//Account amount was updated
											//Send the member data as response
											MemberEntity memberUpdated = MysqlDaoFactory.getMemberDaoRepository().find(memberId);
											HttpJsonResponse.create(response).send(memberUpdated.toJson());
										}else{
											response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
										}
										
										//[BACKUP CODE]
										
										
									}else{
										//Here the session payment status is different
										//from 'paid' or the transaction filled status
										//is already filled or the two case
										//at the same time
										response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
										HttpJsonResponse.create(response)
										.addData("message", "Unable to handle the request")
										.build()
										.send();
									}
									
									
								} catch (StripeException e) {
									e.printStackTrace();
									//Unable to retrieve the payment intent by
									//We send error response
									response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
									HttpJsonResponse.create(response)
									.addData("message", "Unable to handle the request")
									.build()
									.send();
									
								}
								
							}else{
								//We don't find any record of the transaction
								//in our database
								//send error response
								response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
								HttpJsonResponse.create(response)
								.addData("message", "Unable to handle the request")
								.build()
								.send();
							}
							
						}catch(JSONException e){
							//Unable to decode the body of the request, error
							//or an exception occured: Send a bad request respônse
							response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							HttpJsonResponse.create(response)
							.addData("message", "bad request. session field is required")
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

/*
 MemberAccountEntity account = (MemberAccountEntity) member.getAccount();
try {
											DatabaseConnection.getInstance().setAutoCommit(false);
											
											
											//Make sure that the account is not null
											if(account != null){
												
												//Check if it's the first desposit fot the user
												//if case update hios sponsored sponsorship
												//account amount
												boolean isFirstDeposit = false;
												Optional<Boolean> check = 
														MysqlDaoFactory.getMemberDepositRequestDaoRepository()
														.isMemberAccountFirstDeposit((int) account.getId());
												if(check.isPresent()){
													isFirstDeposit = check.get();
												}
												
												//Update the main account amount
												account.setAmount(account.getAmount() + transaction.getAmount());
												if(MysqlDaoFactory.getMemberAccountDaoRepository().updateAmount(account)){
													//update the transaction filled status
													if(MysqlDaoFactory.getMemberCardTransactionDaoRepository().updateFillStatus(transaction)){
														//We succeed to update the transaction
														
														//Save a deposit trace
														MemberDepositRequestEntity depositTrace = new MemberDepositRequestEntity();
														depositTrace.setMemberAccountId(account.getId());
														depositTrace.setPayment("card");
														depositTrace.setAmount(transaction.getAmount());
														depositTrace.setAmountBefore(account.getAmount() - transaction.getAmount());
														depositTrace.setAmountAfter(account.getAmount());
														depositTrace.setFilled(true);
														
														JSONObject extra = new JSONObject();
														extra.put("name",  member.getFirstname() );
														extra.put("acc", account.getId());
														extra.put("pid", AppConfig.BASE_TRANSACTION_NUMBER + transaction.getId());
														depositTrace.setExtra(extra.toString());
														
														if(MysqlDaoFactory.getMemberDepositRequestDaoRepository().create(depositTrace)){
															
															//Here everyThing is OK
															
															//Update the sponsor account if it the first deposit
															//from this user
															if(isFirstDeposit){
																//[START] Pay the sponsorer
																MemberEntity sponsorer = MysqlDaoFactory.getMemberDaoRepository()
																		.findMemberByReference(member.getReference());
																	
																
																MemberSponsorshipPaymentAccountEntity sponsorAccount = sponsorer.getSponsorshipAccount();
																if(sponsorAccount != null){
																	double toAdd = 
																			transaction.getAmount() * AppConfig.SPONSORSHIP_COMMISSION_RATE;
																	
																	if(MysqlDaoFactory.getMemberSponsorshipPaymentAccountDaoRepository().updateAmount(sponsorAccount)){
																		MemberAccountSponsorshipPaymentEntity sponsorPayment = new MemberAccountSponsorshipPaymentEntity();
																		
																		sponsorPayment.setAmountBefore(sponsorAccount.getAmount() - toAdd);
																		sponsorPayment.setAmountAfter(sponsorAccount.getAmount());
																		sponsorPayment.setMemberAccountId(
																				((MemberAccountEntity)sponsorer.getAccount()).getId());
																		
																		if(MysqlDaoFactory.getMemberSponsorhipPaymentDaoRepository().create(sponsorPayment)){
																			
																		}
																	}
																}
																//[END] Pay the sponsorer
															}
															//[END] If is the member deposit
															
															DatabaseConnection.getInstance().commit();
															DatabaseConnection.getInstance().setAutoCommit(true);
															
															//Send Mail to the member about the deposit
															//request
															DepositRequestMailSender depositMailSender = MailSenderFactory.getDepositRequestMailSender();
															depositMailSender.setRecipientAddress(member.getEmail());
															depositMailSender.setAmount(transaction.getAmount());
															depositMailSender.setUsername(member.getFirstname());
															
															depositMailSender.prepareMessage();
															depositMailSender.send();
															
															
															//Send the member data as response
															MemberEntity memberUpdated = MysqlDaoFactory.getMemberDaoRepository().find(memberId);
															HttpJsonResponse.create(response).send(memberUpdated.toJson());
															
														}else{
															//We cant' create the deposit trace
															DatabaseConnection.getInstance().rollback();
															DatabaseConnection.getInstance().setAutoCommit(true);
															
															response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
															HttpJsonResponse.create(response)
															.addData("message", "Unable to handle the request")
															.build()
															.send();
														}
														
														
														
														
													}else{
														DatabaseConnection.getInstance().rollback();
														DatabaseConnection.getInstance().setAutoCommit(true);
														
														response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
														HttpJsonResponse.create(response)
														.addData("message", "Unable to handle the request")
														.build()
														.send();
													}
													
												}else{
													response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
													HttpJsonResponse.create(response)
													.addData("message", "Unable to handle the request")
													.build()
													.send();
												}
											}else{
												//The account is null
												//send forbidden response
												response.setStatus(HttpServletResponse.SC_FORBIDDEN);
												HttpJsonResponse.create(response)
												.addData("message", "Unable to handle the request")
												.build()
												.send();
											}
										} catch (Exception e) {
											//Something went wrong when updating 
											//ACCOUNT data in the database
											try {
												DatabaseConnection.getInstance().rollback();
												DatabaseConnection.getInstance().setAutoCommit(true);
											} catch (SQLException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											
											response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
											HttpJsonResponse.create(response)
											.addData("message", "Unable to handle the request")
											.build()
											.send();
										} 
*/
