package com.xrade.servlets.api.member.auth;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.xrade.entity.MemberEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.JwtTokenManager;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.web.HttpJsonResponse;
import com.xrade.web.HttpJsonResponseBuilder;

/**
 * Servlet implementation class AuthMemberController
 */
@WebServlet("/api/v1/member/security/data")
public class AuthMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthMemberController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		new ResourceGuard(this.getServletContext(), request, response, 
				new ResourceGrantedStateListener(){

					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						
						
						String[] creds = (String[]) request.getAttribute("credentials");
						int memberId = Integer.parseInt(creds[2]);
						
						
						MemberEntity member =  MysqlDaoFactory.getMemberDaoRepository().find(memberId);
						if(member != null)
							HttpJsonResponse.create(response)
							.addData("data", member.toJson())
							.build()
							.send();
						else{
							response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							HttpJsonResponse.create(response)
							.addData("message", "Unable to process the request")
							.build()
							.send();
						}
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
		
		
		JwtTokenManager jwt = new JwtTokenManager();
		HttpJsonResponseBuilder jsonResponse = new HttpJsonResponseBuilder(response);
		
		try {
			String token = jwt.generateToken();
			
			jsonResponse.addData("token", token);
		} catch (JWTCreationException exception){
		    jsonResponse.reset();
		    jsonResponse.addData("code", -1);
		    jsonResponse.addData("message", "Something went wrong. Please retry later");
		}
		
		jsonResponse.send();
		
		
		
	}

}
