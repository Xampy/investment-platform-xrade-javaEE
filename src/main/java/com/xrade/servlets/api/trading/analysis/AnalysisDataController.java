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
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.utils.JsonHandler;
import com.xrade.validator.factory.ApiRequestValidatorFactory;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class SaveAnalysisData
 * 
 * Used only by admins to create, read and update
 * 
 * Delete method is not allowed
 */
@WebServlet("/api/v1/analysis/analysis_data")
public class AnalysisDataController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnalysisDataController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		
		//Check permissions before
		//If the actual use is allowed 
		//to make that request
		
		/*new ResourceGuard(this.getServletContext(), request, response, 
				new ResourceGrantedStateListener(){

					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						
						AnalysisDataEntity entity = null;
						//Validate the request
						try {
							entity = ApiRequestValidatorFactory.getSaveAnalystDataValidator().validate((JSONObject) request.getAttribute("body"));
							
							if(entity != null){
								//Process entiy and save it to data base
								boolean saved = false; // MysqlDaoFactory.getAnalysisDataRepository().create(entity);
								//return the entity as json with data updated
								if (saved){
									PrintWriter res = response.getWriter();
									res.write(entity.toJson().toString());
									res.close();
								}
								
							}else{
								
							}			
							
						} catch (Exception e) {
							e.printStackTrace();
							//Send error occured on validation of the body data
							HttpJsonResponse.create(response)
							.addData("message", e.getMessage())
							.build()
							.send();;			
							
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
		);*/
		
	}

}
