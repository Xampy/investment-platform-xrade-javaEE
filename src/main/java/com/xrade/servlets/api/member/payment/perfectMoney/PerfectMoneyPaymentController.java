package com.xrade.servlets.api.member.payment.perfectMoney;

import java.io.IOException;

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
import com.xrade.entity.MemberPerfectMoneyTransactionEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.DatabaseConnection;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.XStringHasher;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.web.AppConfig;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class PerfectMoneyPaymentController
 */
@WebServlet("/api/v1/fill-account/deposit/perfect-money")
public class PerfectMoneyPaymentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerfectMoneyPaymentController() {
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
						

						//Get the member
						String[] creds = (String[]) request.getAttribute("credentials");
						int memberId = Integer.parseInt(creds[2]);
	
						MemberEntity member = MysqlDaoFactory.getMemberDaoRepository().find(memberId);
						
						//Get the body of request and validate it
						JSONObject body = (JSONObject) request.getAttribute("body");
						if(body != null && member != null){
							try{
								double amount = body.getDouble("amount");
								if(amount >= AppConfig.BASE_AMOUNT && amount < 10000){
									//Continue to process the request

									//[START] Create a row in the debit card table
									MemberPerfectMoneyTransactionEntity transaction = new  MemberPerfectMoneyTransactionEntity();
									transaction.setAmount(amount);
									transaction.setAmountBefore( ((MemberAccountEntity)member.getAccount() ).getAmount() );
									transaction.setAmountAfter( ((MemberAccountEntity)member.getAccount() ).getAmount() + amount );
									transaction.setMetadata("Test Perfect money api checkout");
									//transaction.setPaymentId(session.getId());
									transaction.setMemberAccountId(
											((MemberAccountEntity)member.getAccount() ).getId()
									);
									
									//Save the transaction
									try {
										//DatabaseConnection.getInstance().setAutoCommit(false);
										
										if(MysqlDaoFactory.getMemberPerfectMoneyTransactionDaoRepository().create(transaction)){
											//Set the payment id and update the created transaction
											//Generate a sha for the 
											String originalString = "" + transaction.getId();
											String identifier = XStringHasher.hash256(originalString);
											
											//Update the transaction identifier
											transaction.setIdentifier(identifier);
											
											//Update the payment id
											String paymentId = "000" + transaction.getId();
											transaction.setPaymentId(paymentId);
											
											if(MysqlDaoFactory.getMemberPerfectMoneyTransactionDaoRepository().update(transaction)){
												//DatabaseConnection.getInstance().commit();
												//DatabaseConnection.getInstance().setAutoCommit(true);
												
												//Send response
												HttpJsonResponse.create(response)
												.addData("payment_id", transaction.getPaymentId())
												.addData("session_id", transaction.getIdentifier())
												.build()
												.send();
												
											}else{
												//Unable to update the transaction
												//wuth identifier and payment id
												//DatabaseConnection.getInstance().rollback();
												//DatabaseConnection.getInstance().setAutoCommit(true);
												
												response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
												HttpJsonResponse.create(response)
												.addData("message", "Unable to handle uopdate the request")
												.build()
												.send();
											}
										}else{
											//Unable to create the transaction
											//send error
											
											//DatabaseConnection.getInstance().rollback();
											//DatabaseConnection.getInstance().setAutoCommit(true);
											
											response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
											HttpJsonResponse.create(response)
											.addData("message", "Unable to handle create the request")
											.build()
											.send();
										}
									}catch (Exception e1) {
										//Set auto commit gto false failed
										//Then xe send a message
										e1.printStackTrace();
										response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
										HttpJsonResponse.create(response)
										.addData("message", "Unable to handle the request")
										.build()
										.send();
									}
									//[END] Transaction row
									
									
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
			
		});
	}

}
