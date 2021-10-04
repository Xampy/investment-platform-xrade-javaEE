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
import com.xrade.utils.JsonHandler;

/**
 * Servlet implementation class FullAnalysisController
 * 
 * This endpoint is used by the admins to get only analysis in full form
 * 
 * GET Method
 */
@WebServlet("/api/v1/analysis/full")
public class FullAnalysisController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FullAnalysisController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		/*new ResourceGuard(this.getServletContext(), request, response, 
				new ResourceGrantedStateListener(){

					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						// TODO Auto-generated method stub
						
					}

					public void onDenied(DefaultResourceException e, ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						// TODO Auto-generated method stub
						
					}
			
				}
		);

		try {

			JSONObject bodyData = JsonHandler.toJSON(request.getReader());
			System.out.println(bodyData.toString());

			if (request.getHeader("Content-Type").equals("application/json")) {
				// Buil our entity
				AnalysisDataEntity entity = null;
				// Validate the request
				try {
					// Validate request here

					JSONArray json = new JSONArray();

					AnalysisEntity[] analysis = MysqlDaoFactory.getAnalysisDaoRepository().findAll(0, 0);
					for (AnalysisEntity data : analysis) {
						JSONObject sub_json = data.toJson();
						sub_json.put("analysis", AnalysisDataEntity.toJson(data.getAnalysisData()));

						json.put(sub_json);
					}

					PrintWriter res = response.getWriter();
					res.write(json.toString());
					res.close();

				} catch (Exception e) {
					e.printStackTrace();

					JSONObject json = new JSONObject();
					try {

						json.put("message", e.getMessage());
						json.put("code", "-1");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					PrintWriter res = response.getWriter();
					res.write(json.toString());
					res.close();

				}
			} else {
				// Send content type not valid response
				JSONObject json = new JSONObject();
				try {

					json.put("message", "Content type not authorized");
					json.put("code", "-1");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				PrintWriter res = response.getWriter();
				res.write(json.toString());
				res.close();
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}*/
		
		response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);

	}

}
