package com.xrade.servlets.api.member.fund;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberDepositRequestEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.entity.MemberWithdrawRequestEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class MemberWithdrawalHistoryController
 */
@WebServlet("/api/v1/member/fund/withdrawal/history")
public class MemberWithdrawalHistoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberWithdrawalHistoryController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		new ResourceGuard(this.getServletContext(), request, response, new ResourceGrantedStateListener() {

			
			public void onGranted(ServletContext context, HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
				// TODO Auto-generated method stub

				String[] creds = (String[]) request.getAttribute("credentials");
				int memberId = Integer.parseInt(creds[2]);
				MemberEntity member = MysqlDaoFactory.getMemberDaoRepository().find(memberId);

				if (member != null && ((MemberAccountEntity) member.getAccount() != null)) {
					MemberWithdrawRequestEntity[] requests = MysqlDaoFactory.getMemberWithdrawRequestDaoRepository()
							.getLatestWithdrawalByMember(member);
					JSONArray res = new JSONArray();
					for (MemberWithdrawRequestEntity req : requests) {
						JSONObject json = new JSONObject();

						json.put("payment", req.getPayment());
						json.put("amount_before", req.getAmountBefore());
						json.put("amount_after", req.getAmountAfter());
						json.put("amount", req.getAmount());
						json.put("filled", req.isFilled());
						json.put("date", req.getDate());

						res.put(json);
					}

					HttpJsonResponse.create(response).send(res);

				}
			}

			public void onDenied(DefaultResourceException e, ServletContext context, HttpServletRequest request,
					HttpServletResponse response) throws ServletException, IOException {
				// TODO Auto-generated method stub
				HttpJsonResponse.create(response).addData("message", e.getMessage()).build().send();
			}

		});
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

}
