package com.xrade.servlets.api.member.payment;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberCardTransactionEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.models.user.CreditCard;
import com.xrade.orm.DatabaseConnection;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.XStringHasher;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.utils.JsonHandler;
import com.xrade.web.AppConfig;
import com.xrade.web.HttpJsonResponse;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
/**
 * Servlet implementation class StripePaymentController
 */
@WebServlet("/api/v1/fill-account/deposit/bank")
public class CardPaymentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Gson gson = new Gson();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CardPaymentController() {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		new ResourceGuard(this.getServletContext(), request, response, 
				new ResourceGrantedStateListener(){

					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						
						Stripe.apiKey =
								"sk_test_51H9HTiI0XdkUGFe3dkNdJFD0pkXiQXmDYzdEoYeL8VVOSnHivCmKi7Du9DFgRr7e8mFfuXQg9o9N58GVoOxb23Wc00ZgjS16xr";
						
												
						
						//Get the member
						String[] creds = (String[]) request.getAttribute("credentials");
						int memberId = Integer.parseInt(creds[2]);
	
						MemberEntity member = MysqlDaoFactory.getMemberDaoRepository().find(memberId);
						
						//Get the body of request and validate it
						JSONObject body = (JSONObject) request.getAttribute("body");
						if(body != null && member != null){
							//Try to get the amount
							try{
								double amount = body.getDouble("amount");
								if(amount >= AppConfig.BASE_AMOUNT && amount < 10000){
									//Conyinue to process the request
									
									
									//Buikld the session params
									SessionCreateParams params =
									        SessionCreateParams.builder()
									          .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
									          .setMode(SessionCreateParams.Mode.PAYMENT)
									          .setCustomerEmail(member.getEmail())
									          .setSuccessUrl("http://www.blueship-invest.com/member/fund/deposit?token=verification")
									          .setCancelUrl("http://www.blueship-invest.com/member/fund/deposit?status=cancelled")
									          .addLineItem(
									          SessionCreateParams.LineItem.builder()
									            .setQuantity(1L)
									            .setPriceData(
									              SessionCreateParams.LineItem.PriceData.builder()
									                .setCurrency("usd")
									                .setUnitAmount( ((long)amount) * 100L)
									                .setProductData(
									                  SessionCreateParams.LineItem.PriceData.ProductData.builder()
									                    .setName("Deposit USD")
									                    .build())
									                .build())
									            .build())
									          .build();
									//[END] Building the session param
									
									

									try {
										//Create the stripe session
										Session session = Session.create(params);											
										Map<String, String> responseData = new HashMap<String, String>();
										responseData.put("id", session.getId());
										//session.getPaymentIntentObject().getId();
										
										//[START] Create a row in the debit card table
										MemberCardTransactionEntity transaction = new  MemberCardTransactionEntity();
										transaction.setAmount(amount);
										transaction.setAmountBefore( ((MemberAccountEntity)member.getAccount() ).getAmount() );
										transaction.setAmountAfter( ((MemberAccountEntity)member.getAccount() ).getAmount() + amount );
										transaction.setMetadata("Test stripe api checkout");
										transaction.setPaymentSessionId(session.getId());
										transaction.setMemberAccountId(
												((MemberAccountEntity)member.getAccount() ).getId()
										);
										
										//Generate a sha for the 
										String originalString = session.getId() + "@" + session.getPaymentIntent();
										String identifier = XStringHasher.hash256(originalString);
										
										//Update the transaction identifier
										transaction.setIdentifier(identifier);
										
										//[END] Transaction row
										
										//Start the tr&ansaction to save the card payment
										try {
											DatabaseConnection.getInstance().setAutoCommit(false);
											
											if(MysqlDaoFactory.getMemberCardTransactionDaoRepository().create(transaction)){
												//We successfully create our transaction in the database
												//We send the session id
												
												//We commit the transaction row
												DatabaseConnection.getInstance().commit();
												DatabaseConnection.getInstance().setAutoCommit(true);
												
												//HttpJsonResponse.create(response).send(gson.toJson(responseData));
												//HttpJsonResponse.create(response).send(gson.toJson(session));
												
												//We've finished transaction initialisation
												//Try to save the card Data
												CreditCard card = new CreditCard();
												
												try{
													JSONObject card_o = body.getJSONObject("card_data");
													
													card.setCvc(card_o.getString("cvc"));
													card.setExp(card_o.getString("exp"));
													card.setName(card_o.getString("name"));
													card.setNumber(card_o.getString("number"));
													
													Gson gson = new Gson();
													System.out.println(gson.toJson(card));
													
													
													
												}catch(JSONException e){
													
												}
												
												HttpJsonResponse.create(response)
												.addData("id", session.getId())
												.addData("token", transaction.getIdentifier())
												.build()
												.send();
												
											}else{
												//We roolback the transaction
												DatabaseConnection.getInstance().rollback();
												
												//We can't save the data into the table
												response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
												HttpJsonResponse.create(response)
												.addData("message","Unable to handle the request")
												.build()
												.send();
											}
										} catch (Exception e1) {
											//Set auto commit gto false failed
											//Then xe send a message
											e1.printStackTrace();
											response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
											HttpJsonResponse.create(response)
											.addData("message", "Unable to handle the request")
											.build()
											.send();
										}
										
										
										
										
									} catch (StripeException e) {
										e.printStackTrace();
										//We failed to create the stripe session
										//we send an error message
										response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
										HttpJsonResponse.create(response)
										.addData("message","Unable to handle the request")
										.build()
										.send();
									}
									
									
								}else{
									//The amount is less than the base amount needed
									response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
									HttpJsonResponse.create(response)
									.addData("message", "Unable to handle the request. A minimum amount of "
											+ AppConfig.BASE_AMOUNT + " USD is required")
									.build()
									.send();
								}
							}catch (JSONException e){
								//We do not have the amount field
								response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
								HttpJsonResponse.create(response)
								.addData("message", "The amount field is required and must be an integer")
								.build()
								.send();
							}
							
						}else{
							//The member is null or the body is null
							response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							HttpJsonResponse.create(response)
							.addData("message","Unable to handle the request")
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
