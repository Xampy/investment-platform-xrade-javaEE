package com.xrade.servlets.api.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.xrade.entity.BackOfficerEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.forms.BackOfficerLoginForm;
import com.xrade.security.DataEncrypter;
import com.xrade.security.JwtTokenManager;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.admin.AdminNoAuthResourceGuard;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.service.BackOfficerService;
import com.xrade.utils.Common;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class AdminAuthLoginController
 */
@WebServlet("/api/v1/admin/auth/login")
public class AdminAuthLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminAuthLoginController() {
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
		// Need to guard the request
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		new AdminNoAuthResourceGuard(this.getServletContext(), request, response, new ResourceGrantedStateListener() {

			@Override
			public void onGranted(ServletContext context, HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {

				//BackOfficerLoginForm form = new BackOfficerLoginForm();
				BackOfficerEntity entity = new BackOfficerEntity();

				JSONObject body = (JSONObject) request.getAttribute("body");
				if(body != null){
					try{
						String email = body.getString("email");
						String password = body.getString("password");
						
						entity.setEmail(email);
						
						String hash = DataEncrypter.encryptPassword(password);
						entity.setPassword(hash);
						
						BackOfficerService service = new BackOfficerService();
						Optional<BackOfficerEntity> user = service.getUser(entity);
						try{
							entity = user.get();
							
							JwtTokenManager tokenManager = new JwtTokenManager();
							String token = tokenManager.generateToken( new String[] {entity.getLevel(), entity.getPassword(), String.valueOf(entity.getId())} );
							
							
							HttpJsonResponse.create(response)
							.addData("token", token )
							.addData("data", entity.toJson())
							.build()
							.send();
							
						}catch(NoSuchElementException e){
							response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							HttpJsonResponse.create(response)
							.addData("message", "No user records found with provided credentials")
							.build()
							.send();
						}
						
					}catch(Exception  e){
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						HttpJsonResponse.create(response)
						.addData("message", "Unable to handle the request. email and password fileds are required")
						.build()
						.send();
					}
					
				}else{
					//The request body is null
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					HttpJsonResponse.create(response)
					.addData("message", "Unable to handle the request")
					.build()
					.send();
				}

			}

			@Override
			public void onDenied(DefaultResourceException e, ServletContext context, HttpServletRequest request,
					HttpServletResponse response) throws ServletException, IOException {
				// TODO Auto-generated method stub
				HttpJsonResponse.create(response)
				.addData("message", e.getMessage())
				.build()
				.send();

			}

		});

	}

}
