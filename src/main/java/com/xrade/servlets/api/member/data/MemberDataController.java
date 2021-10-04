package com.xrade.servlets.api.member.data;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.xrade.entity.MemberEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class MemberDataController
 */
@WebServlet("/api/v1/member/account")
public class MemberDataController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDataController() {
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

					@Override
					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						
						//Get the member
						String[] creds = (String[]) request.getAttribute("credentials");
						int memberId = Integer.parseInt(creds[2]);
	
						MemberEntity member = MysqlDaoFactory.getMemberDaoRepository().find(memberId);
						
						
						if( member != null){
							HttpJsonResponse.create(response)
							.send(member.toJson().toString());
						}else{
							//The member is null
							//ass no record was not found
							response.setStatus(HttpServletResponse.SC_FORBIDDEN);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

}
