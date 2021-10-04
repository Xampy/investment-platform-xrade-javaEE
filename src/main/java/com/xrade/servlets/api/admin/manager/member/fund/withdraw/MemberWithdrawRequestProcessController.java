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
import com.xrade.entity.BackOfficerEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.entity.MemberWithdrawRequestEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.admin.AdminResourceGuard;
import com.xrade.service.fund.WithdrawalRequestService;
import com.xrade.service.member.MemberAccountManagementService;
import com.xrade.service.member.MemberService;
import com.xrade.utils.Common;
import com.xrade.web.HttpJsonResponse;
import com.xrade.web.XPaymentMethodType;

/**
 * Servlet implementation class MemberWithdrawProcessController
 */
@WebServlet("/api/v1/admin/manager/withdrawal-request/process")
public class MemberWithdrawRequestProcessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberWithdrawRequestProcessController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		new AdminResourceGuard(this.getServletContext(), request, response, new ResourceGrantedStateListener(){

			@Override
			public void onGranted(ServletContext context, HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
				
				
				
				String[] creds = (String[]) request.getAttribute("credentials");
				int officerId = Integer.parseInt(creds[2]);
				BackOfficerEntity backOfficer = MysqlDaoFactory.getBackOfficerDaoRepository().find(officerId);
				boolean isCashier = false;
				if(backOfficer != null){
					isCashier = backOfficer.getLevel().equals("cashier");
					if(isCashier){
						//[START] cashier process the withdrawal process
						// Handle withdraw request updating here
						// Get the url query parameter
						long withdrawRequestId = Long.parseLong(request.getParameter("id"));

						WithdrawalRequestService withdrawalService = new WithdrawalRequestService();
						MemberWithdrawRequestEntity withdrawalRequest = withdrawalService.getWithdrawRequestById(withdrawRequestId);

						// Member information
						long accountId = withdrawalRequest.getMemberAccountId();
						MemberEntity member = (new MemberService()).getMemberByAccountId((int) accountId);

						// Member Account mamnager service
						MemberAccountManagementService accountMangerService = new MemberAccountManagementService();
						String[] result = null;
						
						
						switch (withdrawalRequest.getAccountType()) {
							case "invest":
								// We need to withdrawal on
								result = accountMangerService.updateAmountByWithdrawal(withdrawalRequest, null, member);
								break;
							case "profit":
								result = accountMangerService.updateAmountByWithdrawal(withdrawalRequest, null, member);
								break; 
							case "sponsor":
								result = accountMangerService.updateAmountByWithdrawal(withdrawalRequest, null, member);
								break;
							default:
								// We got an expected value
								result = null;
						}

						if (result != null  && result.length == 2) {
							if(result[0] == "1"){
								response.setStatus(HttpServletResponse.SC_OK);
							}else{
								response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
							}
							
							HttpJsonResponse.create(response)
							.addData("message", result[1])
							.build()
							.send();
						} else {
							// We have an error while processing
							response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
							HttpJsonResponse.create(response)
							.addData("message", "Unable to handle the request")
							.build()
							.send();
						}
						//[END] cashier process the withdrawal process
					}else{
						//The back officer is not a cashier
						response.setStatus(HttpServletResponse.SC_FORBIDDEN);
						HttpJsonResponse.create(response)
						.addData("message", "Unable to handle the request. You have not the permission to do this")
						.build()
						.send();
					}
				}else{
					//The backofficer is null
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
				HttpJsonResponse.create(response)
				.addData("message", e.getMessage())
				.build().send();
				
			}
			
		});

		

	}

}
