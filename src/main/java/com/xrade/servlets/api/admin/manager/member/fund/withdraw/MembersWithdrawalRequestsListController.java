package com.xrade.servlets.api.admin.manager.member.fund.withdraw;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xrade.entity.MemberDepositRequestEntity;
import com.xrade.entity.MemberWithdrawRequestEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.admin.AdminResourceGuard;
import com.xrade.service.fund.WithdrawalRequestService;
import com.xrade.service.member.MemberService;
import com.xrade.utils.Common;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class MembersWithdrawRequestController
 */
@WebServlet("/api/v1/admin/manager/withdrawal-request/list")
public class MembersWithdrawalRequestsListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MembersWithdrawalRequestsListController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * String base = Common.BASE_URL; MemberService service = new
		 * MemberService(); MemberWithdrawRequestEntity[] data =
		 * service.getMembersWithdrawRequests();
		 * 
		 * 
		 * 
		 * request.setAttribute("BASE_URL", base); request.setAttribute("data",
		 * data);
		 * 
		 * this.getServletContext().getRequestDispatcher(
		 * "/admin/views/member/withdraws.html.jsp").forward(request, response);
		 */

		new AdminResourceGuard(this.getServletContext(), request, response, new ResourceGrantedStateListener() {


			public void onGranted(ServletContext context, HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {

				WithdrawalRequestService withdrawalService = new WithdrawalRequestService();
				MemberWithdrawRequestEntity[] requests = withdrawalService.getUnfilledRequests();

				if (requests != null) {
					response.setHeader("Content-Type", "application/json");
					HttpJsonResponse.create(response).send((new Gson()).toJson(requests));
				} else {
					response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
					HttpJsonResponse.create(response)
					.addData("message", "Unable to complete the request. Something went wrong")
					.build()
					.send();
				}

			}

			
			public void onDenied(DefaultResourceException e, ServletContext context, HttpServletRequest request,
					HttpServletResponse response) throws ServletException, IOException {
				HttpJsonResponse.create(response)
				.addData("message", e.getMessage())
				.build().send();

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
