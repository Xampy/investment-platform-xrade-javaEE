package com.xrade.servlets.api.admin.manager.member.fund;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.entity.BackOfficerEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.admin.AdminResourceGuard;
import com.xrade.service.member.MemberService;
import com.xrade.utils.Common;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class MemberInterestPaymentController
 */
@WebServlet("/api/v1/admin/manager/member/interest-payment/process")
public class MemberInterestPaymentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInterestPaymentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/*String base = Common.BASE_URL;
		request.setAttribute("BASE_URL", base);
		request.setAttribute("members", MysqlDaoFactory.getMemberDaoRepository().countRows());
		
		this.getServletContext().getRequestDispatcher("/admin/views/member/interest-payment.html.jsp").forward(request, response);
		*/
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		new AdminResourceGuard(this.getServletContext(), request, response, new ResourceGrantedStateListener(){

			@Override
			public void onGranted(ServletContext context, HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
				
				//Only manager are allowed to pay interest to all
				//members
				// Get the member
				String[] creds = (String[]) request.getAttribute("credentials");
				int officerId = Integer.parseInt(creds[2]);
				BackOfficerEntity backOfficer = MysqlDaoFactory.getBackOfficerDaoRepository().find(officerId);
				boolean isManager = false;
				if(backOfficer != null){
					isManager = backOfficer.getLevel().equals("manager");
					if(isManager){
						MemberService memberService = new MemberService();
						if( memberService.payInterestToMembers()){
							
							//Members were successfully payed
							
						}else{
							//Can't pay all members
							response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
							HttpJsonResponse.create(response)
							.addData("message", "Unable to complete the request. Something went wrong")
							.build()
							.send();
						}
					}else{
						//The back officer is not the manager
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
				.build()
				.send();
				
			}
			
		});
		
		
	}

}
