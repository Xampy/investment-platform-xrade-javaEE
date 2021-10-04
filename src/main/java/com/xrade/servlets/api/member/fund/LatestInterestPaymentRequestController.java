package com.xrade.servlets.api.member.fund;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.xrade.entity.MemberAccountInterestPaymentEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.service.member.MemberInterestPaymentService;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class LatestInterestPaymentRequestController
 */
@WebServlet("/api/v1/member/fund/interest-payment/history")
public class LatestInterestPaymentRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LatestInterestPaymentRequestController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		new ResourceGuard(this.getServletContext(), request, response, 
				new ResourceGrantedStateListener(){

					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						
						
						String[] creds = (String[]) request.getAttribute("credentials");
						int memberId = Integer.parseInt(creds[2]);
						
						
						MemberEntity member =  MysqlDaoFactory.getMemberDaoRepository().find(memberId);
						JSONArray data = new JSONArray();
						
						if(member != null){
							MemberInterestPaymentService service = new MemberInterestPaymentService();
							MemberAccountInterestPaymentEntity[] payments = service.getLatestInterestPaymentByMember(member);
							
							if(payments != null)
								for(MemberAccountInterestPaymentEntity payment: payments){
									data.put(payment.toJson());
								}
						}
						
						HttpJsonResponse.create(response).send(data);
						
						
					}

					public void onDenied(DefaultResourceException e, ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						HttpJsonResponse.create(response)
						.addData("message", e.getMessage())
						.build()
						.send();
						
					}
			
				}
		);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

}
