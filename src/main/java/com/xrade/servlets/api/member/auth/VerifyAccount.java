package com.xrade.servlets.api.member.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.entity.MemberRegisterVerificatorEntity;
import com.xrade.orm.DatabaseConnection;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class VerifyAccount
 */
@WebServlet("/api/v1/member/security/verify-account")
public class VerifyAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		response.setHeader("Access-Control-Max-Age", "1209600");

		try {

			//JSONObject bodyData = JsonHandler.toJSON(request.getReader());
			//System.out.println(bodyData.toString());

			if (request.getHeader("Content-Type").equals("application/json")) {
				try {
					
					//String reference = bodyData.getString("reference");
					String identifier = request.getParameter("verification_token");
					String code = request.getParameter("code");
					
					MemberRegisterVerificatorEntity verificator = new MemberRegisterVerificatorEntity();
					verificator.setIdentifier(identifier);
					verificator.setCode(code);
					
					boolean verify_found = MysqlDaoFactory.getMemberRegisterVerificatorDaoRepository()
							.findByIdentifierByCode(verificator);
					//If we find a record
					//compare the code
					if(verify_found){
						
						//Check if the member verified state is false
						//[ Not handled ]
						
						//Update the verifictor state and the member verified state
						
						DatabaseConnection.getInstance().setAutoCommit(false);
						
						if( MysqlDaoFactory.getMemberRegisterVerificatorDaoRepository().updateUsedtate(verificator)){
							if(MysqlDaoFactory.getMemberDaoRepository().updateVerifiedtate(verificator.getMemberId())){
								
								DatabaseConnection.getInstance().commit();;
								DatabaseConnection.getInstance().setAutoCommit(true);
								
							}else{
								//Unable to update the member verified status
								
								DatabaseConnection.getInstance().rollback();
								DatabaseConnection.getInstance().setAutoCommit(true);
								
								response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
								HttpJsonResponse.create(response)
								.addData("message", "Unable to handle the request")
								.build()
								.send();
							}
						}else{
							//Unable to update the verificator used state
							
							DatabaseConnection.getInstance().rollback();
							DatabaseConnection.getInstance().setAutoCommit(true);
							
							response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
							HttpJsonResponse.create(response)
							.addData("message", "Unable to handle the request")
							.build()
							.send();
						}
						
					}else{
						//No record found
						//send bad request
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						HttpJsonResponse.create(response)
						.addData("message", "Unable to handle the request")
						.build()
						.send();
					}

				} catch (Exception e) {
					//Validation scope on getting the
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
				.addData("message", "Missed Content type or Content Type is not authorized. It must be json")
				.build()
				.send();
			}

		} catch (Exception e2) {
			e2.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			HttpJsonResponse.create(response)
			.addData("message", "Unable to handle the request")
			.build()
			.send();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

}
