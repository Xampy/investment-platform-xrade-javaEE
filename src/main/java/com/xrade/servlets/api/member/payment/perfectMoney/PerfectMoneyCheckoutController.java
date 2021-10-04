package com.xrade.servlets.api.member.payment.perfectMoney;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.stripe.model.issuing.Transaction;
import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.entity.MemberPerfectMoneyTransactionEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.security.XStringHasher;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.utils.JsonHandler;
import com.xrade.web.AppConfig;
import com.xrade.web.HttpJsonResponse;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Servlet implementation class PerfectMoneyCheckoutController
 * 
 * Get the perfect money checkout
 * Need the payment_id and the session_id as identifier of the transaction
 */
@WebServlet("/api/v1/fill-account/deposit/perfect-money/checkout")
public class PerfectMoneyCheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final OkHttpClient httpClient = new OkHttpClient();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PerfectMoneyCheckoutController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		
		/*new ResourceGuard(this.getServletContext(), request, response, 
				new ResourceGrantedStateListener(){

					@Override
					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						
						
						try{
							String paymentId = request.getParameter("payment_id");
							String sessionId = request.getParameter("session_id");
							
							//Check params empty state
							MemberPerfectMoneyTransactionEntity transaction = 
									MysqlDaoFactory.getMemberPerfectMoneyTransactionDaoRepository()
										.findByIdentifier(sessionId);
							
							//If the transaction is null
							//and the payment_id is not equal to the 
							//provided payment id send forbidden response
							if(transaction != null && transaction.getPaymentId().equals(paymentId)){
								
								//Get the member
								String[] creds = (String[]) request.getAttribute("credentials");
								int memberId = Integer.parseInt(creds[2]);
			
								MemberEntity member = MysqlDaoFactory.getMemberDaoRepository()
										.find(memberId);
								
								
								
								//Get the body of request and validate it
								JSONObject body = (JSONObject) request.getAttribute("body");
								if(member != null){
									//Process the request
									
									//[START] Process the request
									try {										
										// form parameters
										RequestBody formBody = new FormBody.Builder()
												.add("PAYEE_ACCOUNT", "U29385398")
												.add("PAYEE_NAME", "BlueShip LLC USD")
												.add("PAYMENT_AMOUNT", String.valueOf(transaction.getAmount()) )
												.add("PAYMENT_UNITS", "USD")
												.add("PAYMENT_ID", transaction.getPaymentId())
												.add("STATUS_URL", "http://localhost:8080/xrade/api/v1/fill-account/deposit/perfect-money/check")
												.add("PAYMENT_URL", "http://localhost:8080/xrade/api/v1/fill-account/deposit/perfect-money/check")
												.add("PAYMENT_URL_METHOD", "POST")
												.add("NOPAYMENT_URL", "http://localhost:8080/xrade/api/v1/fill-account/deposit/perfect-money/check")
												.add("NOPAYMENT_URL_METHOD", "POST")
												.add("BAGGAGE_FIELDS", "ORDER_NUM CUST_NUM")
												.add("ORDER_NUM", "9801121")
												.add("CUST_NUM", "2067609")
												.add("PAYMENT_METHOD", "PerfectMoney account")
												.build();
										
										
										
										Request externalRequest = new Request.Builder()
								                .url("https://perfectmoney.is/api/step1.asp")
								                .addHeader("User-Agent", "OkHttp Bot")
								                .post(formBody)
								                .build();
										
										try(Response externalResponse = httpClient.newCall(externalRequest).execute()){
													
								            if (externalResponse.isSuccessful()){
								            	// Get response body
								            	//String resBody = externalResponse.body().string();
									            //System.out.println(resBody);
								            	
									            System.out.println(externalResponse.header("URL"));
									            response.setContentType("text/html");
									            //Send the response to the client
									            PrintWriter res = response.getWriter();
									            res.write(externalResponse.toString());		        
									            res.close();
								            }else{
								            	System.out.println("No success");
								            	response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
								            }

								            
								        }catch (Exception e) {
								        	e.printStackTrace();
											response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
										}
										

									} catch (Exception e) {
										e.printStackTrace();
										response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									}
									//[END] Process the request
									
								}else{
									//The member is null or the body is null
									response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									HttpJsonResponse.create(response)
									.addData("message","Unable to handle the request")
									.build()
									.send();
								}
								
							}else{
								//There is an error on transaction
								//and payment id side
								response.setStatus(HttpServletResponse.SC_FORBIDDEN);
							}
						}catch(Exception e){
							//Session and payment are not there
							response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							HttpJsonResponse.create(response)
							.addData("message","Unable to handle the request")
							.build()
							.send();
						}
						
						
						
						
					}

					@Override
					public void onDenied(DefaultResourceException e, ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						HttpJsonResponse.create(response)
						.addData("message", e.getMessage())
						.build()
						.send();
						
					}
			
		});*/

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

}
