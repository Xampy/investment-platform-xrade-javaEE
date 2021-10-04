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
import com.xrade.security.guard.HttpRequestGrantedStateListener;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.utils.JsonHandler;
import com.xrade.validator.factory.ApiRequestValidatorFactory;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class AnalysisController
 * 
 * This endpoint is used by members to get analysis list and newer
 */
@WebServlet("/api/v1/analysis")
public class AnalysisController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnalysisController() {
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
						
						
						int limit = -1;
						int offset = -1;
						
						try{
							offset = Integer.parseInt(request.getParameter("offset"));
							limit = Integer.parseInt(request.getParameter("limit"));
						}catch(Exception e){
							
						}
						
						// Validate the request
						try {
							//Validate request here
							
							JSONArray json = new JSONArray();
							
							 AnalysisEntity[] analysis = MysqlDaoFactory
									 .getAnalysisDaoRepository()
									 .findAll(limit < 0?0:limit, offset<0?0:offset);
							 for(AnalysisEntity data: analysis){
								 JSONObject sub_json = data.toJson();
								 JSONObject analysis_json = AnalysisDataEntity.toJson( data.getAnalysisData() );
								 //Need to remove some field
								 analysis_json.remove("profit");
								 analysis_json.remove("capital");
								 analysis_json.remove("loss");
								 analysis_json.remove("stop_loss");
								 analysis_json.remove("take_profit");
								 
								 sub_json.put("analysis",  analysis_json );
								 
								 json.put(sub_json);
							 }
							
							HttpJsonResponse.create(response).send(json);

							

						} catch (Exception e) {
							e.printStackTrace();
							HttpJsonResponse.create(response)
							.addData("message", e.getMessage())
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
		// TODO Auto-generated method stub
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

}
