package com.xrade.servlets.api.admin.manager;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.exception.DefaultSecurityException;
import com.xrade.security.guard.RouteGrantedStateListener;
import com.xrade.security.guard.admin.ManagerRouteGuard;
import com.xrade.utils.Common;

/**
 * Servlet implementation class ManagerHomeController
 */
@WebServlet("/manager/dashboard")
public class ManagerHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerHomeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*new ManagerRouteGuard(this.getServletContext(), request, response,
				new RouteGrantedStateListener(){

					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						
						
						
					}

					public void onDenied(DefaultSecurityException e, ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						
						response.sendRedirect(context.getContextPath() + "/trader/dashboard");
						
					}
				}
		);*/
		
		String base = Common.BASE_URL;
		request.setAttribute("BASE_URL", base);
		this.getServletContext().getRequestDispatcher("/admin/views/index.jsp").forward(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
