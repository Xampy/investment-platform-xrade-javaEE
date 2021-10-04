package com.xrade.servlets.api.trading.analysis;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.xrade.entity.AnalysisDataEntity;
import com.xrade.entity.AnalysisEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class AnalysisAvailableLotController
 */
@WebServlet("/api/v1/analysis/single")
public class AnalysisSingleController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnalysisSingleController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		new ResourceGuard(this.getServletContext(), request, response, 
				new ResourceGrantedStateListener(){

					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						
						
						int id = -1;
						try {
							id = Integer.parseInt(request.getParameter("id"));
							if (id < 0){
								response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
								HttpJsonResponse.create(response)
								.addData("message", "Invalid analysis id - Negative values are not allowed")
								.build()
								.send();
							}else {
								AnalysisEntity analysis = MysqlDaoFactory.getAnalysisDaoRepository().find(id);							
								if(analysis != null){
								
									JSONObject sub_json = analysis.toJson();
									JSONObject analysis_json = AnalysisDataEntity.toJson(analysis.getAnalysisData());
									
									// Need to remove some field
									analysis_json.remove("profit");
									analysis_json.remove("capital");
									analysis_json.remove("loss");
									analysis_json.remove("stop_loss");
									analysis_json.remove("take_profit");
					
									sub_json.put("analysis", analysis_json);
									
									HttpJsonResponse.create(response).send(sub_json);
								}else {
									HttpJsonResponse.create(response)
									.addData("message", "No record found for the provided id")
									.build()
									.send();
								}
							}
						} catch (NumberFormatException e) {
							response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							HttpJsonResponse.create(response)
							.addData("message", "Bad format analysis id or mmissed id - Only numbers are allowed")
							.build()
							.send();
						}
						
					}

					public void onDenied(DefaultResourceException e, ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						//Send the error mesage
						HttpJsonResponse.create(response)
						.addData("message", e.getMessage())
						.build()
						.send();
						
					}
			
				}
		);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

	}

}
