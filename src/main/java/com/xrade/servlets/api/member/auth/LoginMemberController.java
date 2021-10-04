package com.xrade.servlets.api.member.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.xrade.entity.MemberEntity;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.DataEncrypter;
import com.xrade.security.JwtTokenManager;
import com.xrade.utils.JsonHandler;
import com.xrade.validator.factory.ApiRequestValidatorFactory;
import com.xrade.web.HttpJsonResponse;


/**
 * Servlet implementation class LoginMemberController
 */
@WebServlet("/api/v1/member/auth/login")
public class LoginMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginMemberController() {
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
					entity = ApiRequestValidatorFactory.getLoginMemberRequestValidator().validate(bodyData);
					
					if (entity != null) {
						//Get the hashed password
						String password = entity.getPassword();
						String hash = DataEncrypter.encryptPassword(password);
						entity.setPassword(hash);
						// Process entiy and save it to data base
						boolean selected = MysqlDaoFactory.getMemberDaoRepository().findByEmailPassword(entity);
						// return the entity as json with data updated
						if (selected) {						
							
							JwtTokenManager tokenManager = new JwtTokenManager();
							String token = tokenManager.generateToken( new String[] {entity.getPhone(), entity.getPassword(), String.valueOf(entity.getId())} );
							
							JSONObject data = new JSONObject();
							data.put("token", token );
							data.put("data", entity.toJson());
							
							HttpJsonResponse.create(response)
							.addData("token", token )
							.addData("data", entity.toJson())
							.build()
							.send();

						}else {
							response.setStatus(HttpServletResponse.SC_NOT_FOUND);
							HttpJsonResponse.create(response)
							.addData("message", "No record found")
							.build()
							.send();
						}

					}else {
						
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						HttpJsonResponse.create(response)
						.addData("message", "Unable to handle the request. Process information failed")
						.build()
						.send();
					}

				} catch (Exception e) {
					//Validation scope
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
		}
	}

}
