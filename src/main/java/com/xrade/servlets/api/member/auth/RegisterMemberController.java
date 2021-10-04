package com.xrade.servlets.api.member.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.xrade.entity.MarketAnalysisEntity;
import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberInterestPaymentAccountEntity;
import com.xrade.entity.MemberRegisterVerificatorEntity;
import com.xrade.entity.MemberSponsorshipPaymentAccountEntity;
import com.xrade.entity.MemberAccountInterestPaymentEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.orm.DatabaseConnection;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.DataEncrypter;
import com.xrade.security.XStringHasher;
import com.xrade.service.mail.factory.MailSenderFactory;
import com.xrade.service.mail.sender.EmailConfirmationMailSender;
import com.xrade.utils.JsonHandler;
import com.xrade.validator.factory.ApiRequestValidatorFactory;
import com.xrade.web.HttpJsonResponse;


/**
 * Servlet implementation class RegisterMemberController
 */
@WebServlet("/api/v1/member/auth/register")
public class RegisterMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterMemberController() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Check permissions before
		// If the actual use is allowed
		// to make that request
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		response.setHeader("Access-Control-Max-Age", "1209600");
		
		try {

			JSONObject bodyData = JsonHandler.toJSON(request.getReader());
			System.out.println(bodyData.toString());

			if (request.getHeader("Content-Type").equals("application/json")) {
				// Buil our entity
				MemberEntity entity = null;
				// Validate the request
				try {
					entity = ApiRequestValidatorFactory.getRegisterMemberRequestValidator().validate(bodyData);

					if (entity != null) {
						//Encrypt the password
						String password = entity.getPassword();
						String hash = DataEncrypter.encryptPassword(password);
						entity.setPassword(hash);
						
						// Process entiy and save it to data base
						
						//Set the user reference value
						int added = (int)(Math.random() * 1000);
						String reference = entity.getFirstname() +  String.valueOf( added );
						
						entity.setReference(reference);
						
						//[START] transaction for creating a new member
						DatabaseConnection.getInstance().setAutoCommit(false);
						
						boolean saved = MysqlDaoFactory.getMemberDaoRepository().create(entity);
						// return the entity as json with data updated
						if (saved) {
							
							//Create account for the user
							MemberAccountEntity account = new MemberAccountEntity();
							account.setMemberId(entity.getId());
							
							saved = MysqlDaoFactory.getMemberAccountDaoRepository().create(account);
							if(saved){
								
								//Create the interest account
								MemberInterestPaymentAccountEntity interestAccount = new MemberInterestPaymentAccountEntity();
								interestAccount.setMemberAccountId(account.getId());
								
								saved = MysqlDaoFactory.getMemberInterestPaymentAccountDaoRepository().create(interestAccount);
								if(saved){
									
									//Create the sponsorship account for  the new user
									MemberSponsorshipPaymentAccountEntity sponsorshipAccount = new MemberSponsorshipPaymentAccountEntity();
									sponsorshipAccount.setMemberAccountId(account.getId());
									
									saved = MysqlDaoFactory.getMemberSponsorshipPaymentAccountDaoRepository().create(sponsorshipAccount);
									if(saved){//Persit the the token and the code sent
										//Genrate the token with jwt
										//[NOT Handled]
										
										String code = String.format("%06d", (int) ((new Random()).nextInt(888888) + 111111));
										System.out.println("Code ! " + code);
										
										
										byte[] array = new byte[15]; // length is bounded by 7
									    new Random().nextBytes(array);
									    String generatedString = new String(array, Charset.forName("UTF-8"));
									    
									    
										String identifier = XStringHasher.hash256(generatedString);
										
										MemberRegisterVerificatorEntity verificator = new MemberRegisterVerificatorEntity();
										verificator.setCode(code);
										verificator.setIdentifier(identifier);
										verificator.setMemberId(entity.getId());
										
										if(MysqlDaoFactory.getMemberRegisterVerificatorDaoRepository().create(verificator)){
											
											
											DatabaseConnection.getInstance().commit();
											DatabaseConnection.getInstance().setAutoCommit(true);
											
											
											EmailConfirmationMailSender  mailer = MailSenderFactory.getEmailConfirmationMailSender();
											//Prepare the message body
											mailer.setCode(code);
											mailer.setRecipientAddress(entity.getEmail());
											mailer.prepareMessage();
											mailer.send();
											
											
											
											entity.setAccount(account);
											HttpJsonResponse.create(response)
											.addData("verification_token", verificator.getIdentifier())
											.addData("data", entity.toJson())
											.build()
											.send();
											
										}else{
											//Can't create the member verification token
											DatabaseConnection.getInstance().rollback();
											DatabaseConnection.getInstance().setAutoCommit(true);
											
											response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
											HttpJsonResponse.create(response)
											.addData("message", "Unable to handle the request")
											.build()
											.send();
										}
										
										
										
										
										
										
										
									}else{
										//Can't create the member sponsorshi^p account
										DatabaseConnection.getInstance().rollback();
										DatabaseConnection.getInstance().setAutoCommit(true);
										
										response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
										HttpJsonResponse.create(response)
										.addData("message", "Unable to handle the request")
										.build()
										.send();
									}
									
								}else{
									//Can't create the member interest account
									DatabaseConnection.getInstance().rollback();
									DatabaseConnection.getInstance().setAutoCommit(true);
									
									response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
									HttpJsonResponse.create(response)
									.addData("message", "Unable to handle the request")
									.build()
									.send();
								}								
								
							}else{
								//Can't create the member account
								DatabaseConnection.getInstance().rollback();
								DatabaseConnection.getInstance().setAutoCommit(true);
								
								response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
								HttpJsonResponse.create(response)
								.addData("message", "Unable to handle the request")
								.build()
								.send();
							}
							
							
						}else{
							//Can't create the member, we rollback data
							DatabaseConnection.getInstance().rollback();
							DatabaseConnection.getInstance().setAutoCommit(true);
							
							response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
							HttpJsonResponse.create(response)
							.addData("message", "Unable to handle the request")
							.build()
							.send();
						}
						//[END] transaction for creating a new member

					}

				} catch (Exception e) {
					e.printStackTrace();

					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					HttpJsonResponse.create(response)
					.addData("message", e.getMessage())
					.build()
					.send();

				}
			} else {
				// Send content type not valid response
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				HttpJsonResponse.create(response)
				.addData("message", "Content type not authorized")
				.build()
				.send();
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

}
