package com.xrade.servlets.api.admin.manager.member.fund.deposit;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.entity.MemberDepositRequestEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.service.member.MemberService;
import com.xrade.utils.Common;

/**
 * Servlet implementation class MembersDepositRequestController
 */
@WebServlet("/manager/member/deposit-request")
public class MembersDepositRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MembersDepositRequestController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return;
		
		/*String base = Common.BASE_URL;
		MemberService service = new MemberService();
		MemberDepositRequestEntity[] data = service.getMembersDepositRequests();
		MemberDepositRequestEntity[] filledRequests = service.getFilledMembersDepositRequests();
		request.setAttribute("filled_requests", filledRequests);
		
		
		request.setAttribute("BASE_URL", base);
		request.setAttribute("data", data);
		
		this.getServletContext().getRequestDispatcher("/admin/views/member/deposits.html.jsp").forward(request, response);
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
