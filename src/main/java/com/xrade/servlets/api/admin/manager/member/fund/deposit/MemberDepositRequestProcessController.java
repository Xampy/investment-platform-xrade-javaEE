package com.xrade.servlets.api.admin.manager.member.fund.deposit;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.entity.MemberDepositRequestEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.entity.MemberWithdrawRequestEntity;
import com.xrade.forms.MemberDepositForm;
import com.xrade.orm.dao.mysqlRepository.member.MemberAccountDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.MemberDepositRequestDaoRepository;
import com.xrade.service.member.MemberAccountManagementService;
import com.xrade.service.member.MemberService;
import com.xrade.utils.Common;

/**
 * Servlet implementation class MemberDepositProcessController
 */
@WebServlet("/manager/member/deposit-request/process")
public class MemberDepositRequestProcessController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDepositRequestProcessController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*String base = Common.BASE_URL;
		request.setAttribute("BASE_URL", base);
		
		
		//Get the url query parameter
		long depositRequestId = Long.parseLong(request.getParameter("deposit_request_id"));
		MemberService service = new MemberService();
		MemberDepositRequestEntity[] data = {service.getMemberDepositRequestById(depositRequestId)};
		request.setAttribute("data", data);
		
		
		
		//Member information
		long accountId = data[0].getMemberAccountId();
		MemberEntity member = (new MemberService()).getMemberByAccountId((int) accountId);
		request.setAttribute("member", member);
		
		this.getServletContext().getRequestDispatcher("/admin/views/member/deposit-proceed.html.jsp").forward(request, response);
		
		*/
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*MemberDepositForm form = new MemberDepositForm();
		
		try {
			form.handleRequest(request);
			MemberAccountManagementService service = new MemberAccountManagementService();
			MemberDepositRequestEntity data = form.getData();
			
			long depositRequestId = Long.parseLong(request.getParameter("deposit_request_id"));
			data.setId(  depositRequestId );
			service.updateAccountAmount( data );
			
			doGet(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print(e.getMessage());
			e.printStackTrace();
			
			request.setAttribute("errors", form.getErrors());
			doGet(request, response);
		}*/
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
	}

}
