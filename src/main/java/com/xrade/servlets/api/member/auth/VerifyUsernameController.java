package com.xrade.servlets.api.member.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class VerifyUsernameController
 */
@WebServlet("/api/v1/member/security/check/username")
public class VerifyUsernameController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyUsernameController() {
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
					String username = request.getParameter("username");
					boolean selected = MysqlDaoFactory.getMemberDaoRepository().findByUsername(username);
					// return the entity as json with data updated
					if (selected) {						
						HttpJsonResponse.create(response)
						.addData("status", "valid")
						.build()
						.send();
					}else {
						HttpJsonResponse.create(response)
						.addData("status", "invalid")
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
