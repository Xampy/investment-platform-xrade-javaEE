package com.xrade.servlets.api.admin.manager.member;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xrade.entity.AnalysisEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.admin.AdminNoAuthResourceGuard;
import com.xrade.security.guard.admin.AdminResourceGuard;
import com.xrade.service.AnalysisService;
import com.xrade.service.member.MemberService;
import com.xrade.utils.Common;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class MembersListController
 */
@WebServlet("/api/v1/admin/manager/member/list")
public class MembersListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MembersListController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// this.getServletContext().getRequestDispatcher("/admin/views/member/members.html.jsp").forward(request,
		// response);

		new AdminResourceGuard(this.getServletContext(), request, response, new ResourceGrantedStateListener() {

			
			public void onGranted(ServletContext context, HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {

				Gson gson = new Gson();

				MemberService memberService = new MemberService();
				MemberEntity[] data = memberService.getMembers();

				response.setHeader("Content-Type", "application/json");
				HttpJsonResponse.create(response).send(gson.toJson(data).toString());

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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

	}

}
