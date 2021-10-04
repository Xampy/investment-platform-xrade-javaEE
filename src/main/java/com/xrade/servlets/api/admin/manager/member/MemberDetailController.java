package com.xrade.servlets.api.admin.manager.member;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.admin.AdminResourceGuard;
import com.xrade.service.member.MemberService;
import com.xrade.utils.Common;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class MemberDetailController
 */
@WebServlet("/api/v1/admin/manager/member/detail")
public class MemberDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberDetailController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		new AdminResourceGuard(this.getServletContext(), request, response, new ResourceGrantedStateListener() {

			@Override
			public void onGranted(ServletContext context, HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
				int memberId = Integer.parseInt(request.getParameter("id"));

				MemberService memberService = new MemberService();
				MemberEntity[] data = { memberService.getMemberById(memberId) };
				MemberAccountEntity account = (MemberAccountEntity) data[0].getAccount();

				int withdrawRequestCount = memberService.getWithdrawRequestCount(account.getId());
				int depositRequestCount = memberService.getDepositRequestCount(account.getId());

				// Construct the response
				JSONObject stat = new JSONObject();
				stat.put("deposit", depositRequestCount);
				stat.put("withdrawal", withdrawRequestCount);

				response.setHeader("Content-Type", "application/json");
				HttpJsonResponse.create(response)
				.addData("member", new JSONObject((new Gson()).toJson(data[0])))
				.addData("stat", stat)
				.build()
				.send();

			}

			@Override
			public void onDenied(DefaultResourceException e, ServletContext context, HttpServletRequest request,
					HttpServletResponse response) throws ServletException, IOException {
				HttpJsonResponse.create(response)
				.addData("message", e.getMessage())
				.build()
				.send();

			}

		});

		/*
		 * String base = Common.BASE_URL; request.setAttribute("BASE_URL",
		 * base); request.setAttribute("data", data);
		 * request.setAttribute("withdraw_request_count", withdrawRequestCount);
		 * request.setAttribute("deposit_request_count", depositRequestCount);
		 * 
		 * this.getServletContext().getRequestDispatcher(
		 * "/admin/views/member/member.html.jsp").forward(request, response);
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}

}
