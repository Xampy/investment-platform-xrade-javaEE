package com.xrade.servlets.api.admin.manager.member.fund.withdraw;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.xrade.entity.MemberEntity;
import com.xrade.entity.MemberWithdrawRequestEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.admin.AdminResourceGuard;
import com.xrade.service.fund.WithdrawalRequestService;
import com.xrade.service.member.MemberService;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class MemberWithrawalRequestDetailController
 */
@WebServlet("/api/v1/admin/manager/withdrawal-request/detail")
public class MemberWithrawalRequestDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberWithrawalRequestDetailController() {
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
		
		new AdminResourceGuard(this.getServletContext(), request, response, new ResourceGrantedStateListener() {

			
			public void onGranted(ServletContext context, HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
				
				// Get the url query parameter
				long withdrawRequestId = Long.parseLong(request.getParameter("id"));

				WithdrawalRequestService withdrawalService = new WithdrawalRequestService();
				MemberWithdrawRequestEntity withdrawalRequest = withdrawalService.getWithdrawRequestById(withdrawRequestId);
				
				if(withdrawalRequest != null){
					// Member information
					long accountId = withdrawalRequest.getMemberAccountId();
					MemberEntity member = (new MemberService()).getMemberByAccountId((int) accountId);
					if(member != null){
						HttpJsonResponse.create(response)
						.addData("request", new JSONObject((new Gson()).toJson(withdrawalRequest).toString()))
						.addData("member", new JSONObject((new Gson()).toJson(member).toString()))
						.build()
						.send();
					}else{
						//The member is null
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						HttpJsonResponse.create(response)
						.addData("message", "Unable to handle the request")
						.build().send();
					}		
					
				}else{
					//The request is null
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					HttpJsonResponse.create(response)
					.addData("message", "Unable to handle the request. No records founc for withdrawal credentials")
					.build().send();
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
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);

	}

}
