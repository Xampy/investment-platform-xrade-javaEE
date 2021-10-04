package com.xrade.servlets.api.member.fund;

import java.io.IOException;
import java.io.PrintWriter;

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
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.utils.JsonHandler;
import com.xrade.validator.factory.ApiRequestValidatorFactory;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class MakeDepositRequestController
 */
@WebServlet("/api/v1/member/fund/deposit")
public class MakeDepositRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MakeDepositRequestController() {
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
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return;
		
		/*new ResourceGuard(this.getServletContext(), request, response, 
				new ResourceGrantedStateListener(){

					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						
						// Buil our entity
						MemberDepositRequestEntity entity = null;
						// Validate the request
						try {
							entity = ApiRequestValidatorFactory.getMakeDepositRequestValidator()
									.validate((JSONObject) request.getAttribute("body"));

							if (entity != null) {
								String[] creds = (String[]) request.getAttribute("credentials");
								int memberId = Integer.parseInt(creds[2]);
								
								//Check if there was not an unfullfilled request
								//Get unfilled deposit request
								MemberDepositRequestEntity unfilledRequest = MysqlDaoFactory
										.getMemberDepositRequestDaoRepository()
										.findUnfilledByMemberId(memberId);
								if(unfilledRequest != null){
									response.setStatus(HttpServletResponse.SC_FORBIDDEN);
									HttpJsonResponse.create(response)
									.addData("message", 
											"There is still an unfilled  fund request." +
											" Refer to admin to process it before make a new request")
									.build()
									.send();
								}else{
									
									//Set the amount before and the mount after here
									MemberEntity member = MysqlDaoFactory
											.getMemberDaoRepository()
											.find(memberId);
									MemberAccountEntity account = (MemberAccountEntity) member.getAccount();
									
									entity.setAmountBefore(account.getAmount());
									entity.setAmountAfter( entity.getAmount() + account.getAmount() );
									entity.setMemberAccountId(account.getId());
									
									
									// Process entiy and save it to data base
									boolean saved = MysqlDaoFactory.getMemberDepositRequestDaoRepository().create(entity);
									// return the entity as json with data updated
									if (saved) {
										response.setStatus(HttpServletResponse.SC_CREATED);
										HttpJsonResponse.create(response).send(entity.toJson());
									}else{
										response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
										HttpJsonResponse.create(response)
										.addData("message", 
												"Unable to proces the with draw request. Please try agin later")
										.build()
										.send();
									}

								}
								

							}

						} catch (Exception e) {
							e.printStackTrace();
							//Validation catch scope
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
		);*/
	}

}
