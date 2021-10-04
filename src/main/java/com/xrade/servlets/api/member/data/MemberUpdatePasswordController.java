package com.xrade.servlets.api.member.data;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.xrade.entity.MemberEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.DataEncrypter;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class MemberUpdatePasswordController
 */
@WebServlet("/api/v1/member/security/update/password")
public class MemberUpdatePasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberUpdatePasswordController() {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		new ResourceGuard(this.getServletContext(), request, response, new ResourceGrantedStateListener() {

			@Override
			public void onGranted(ServletContext context, HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {

				// Get the member
				String[] creds = (String[]) request.getAttribute("credentials");
				int memberId = Integer.parseInt(creds[2]);

				MemberEntity member = MysqlDaoFactory.getMemberDaoRepository().find(memberId);

				// Get the body of request and validate it
				JSONObject body = (JSONObject) request.getAttribute("body");
				if (body != null && member != null) {
					// Read the body content
					try {
						String oldPassword = body.getString("old_password");
						String newPassword = body.getString("new_password");

						System.out.println("Data " + oldPassword + " " + newPassword);

						// Hash the old password
						// and check upon the meber password
						if (member.getPassword().equals(DataEncrypter.encryptPassword(oldPassword))) {

							member.setPassword(DataEncrypter.encryptPassword(newPassword));
							if (MysqlDaoFactory.getMemberDaoRepository().updatePassword(member)) {

							} else {
								response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
								HttpJsonResponse.create(response).addData("message", "Unable to handle the request")
										.build().send();
							}

						} else {
							// The possword provided is not the same
							// send forbidden response to
							// the client
							response.setStatus(HttpServletResponse.SC_FORBIDDEN);
							HttpJsonResponse.create(response)
									.addData("message", "Unable to handle the request bad information").build().send();
						}

					} catch (Exception e) {
						// Not found the required field in the body data
						// Then send a bad request to the client
						e.printStackTrace();
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						HttpJsonResponse.create(response)
								.addData("message", "Unable to handle the request fields reuired " + e.getMessage())
								.build().send();
					}
				} else {
					// Body or the member entity is null
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					HttpJsonResponse.create(response).addData("message", "Unable to handle the request invalid data")
							.build().send();
				}

			}

			@Override
			public void onDenied(DefaultResourceException e, ServletContext context, HttpServletRequest request,
					HttpServletResponse response) throws ServletException, IOException {
				HttpJsonResponse.create(response)
				.addData("message", e.getMessage())
				.build().send();

			}

		});

	}

}
