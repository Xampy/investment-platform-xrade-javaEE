package com.xrade.servlets.api.trading.analysis;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.xrade.entity.AnalysisDataEntity;
import com.xrade.entity.MarketAnalysisEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.utils.JsonHandler;
import com.xrade.validator.factory.ApiRequestValidatorFactory;

/**
 * Servlet implementation class SaveMarketAnalysis
 * 
 * 
 * Only admins are allowed to use this resource
 * make action on market analysis resource like create read.
 *  
 * Update and delete are restricted at some level
 */
@WebServlet("/api/v1/analysis/market_analysis")
public class MarketAnalysisController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MarketAnalysisController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		
		
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
			
			if ( request.getHeader("Content-Type").equals("application/json") ){
				//Buil our entity
				MarketAnalysisEntity entity = null;
				//Validate the request
				try {
					entity = ApiRequestValidatorFactory.getSaveMarketAnalysisRequestValidator().validate(bodyData);
					
					if(entity != null){
						//Process entiy and save it to data base
						boolean saved = MysqlDaoFactory.getMarketAnalysisRepository().create(entity);
						//return the entity as json with data updated
						if (saved){
							PrintWriter res = response.getWriter();
							res.write(entity.toJson().toString());
							res.close();
						}
						
					}
					
					JSONObject json = new JSONObject();
					try {
						
						json.put("message", "Validation success");
						json.put("code", "200");
					} catch (Exception e1) {
						e1.printStackTrace();				
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
			}else {
				//Send content type not valid response
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
	}

}
