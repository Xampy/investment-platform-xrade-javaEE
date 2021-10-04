package com.xrade.servlets.api.trading.order;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.xrade.entity.MarketOrderEntity;
import com.xrade.entity.MarketOrderProcessEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.utils.JsonHandler;
import com.xrade.validator.factory.ApiRequestValidatorFactory;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class SaveOrderProcess
 */
@WebServlet("/api/v1/order/process")
public class OrderProcessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderProcessController() {
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
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		new ResourceGuard(this.getServletContext(), request, response, 
				new ResourceGrantedStateListener(){

					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						
						// Buil our entity
						MarketOrderProcessEntity entity = null;
						// Validate the request
						try {
							entity = ApiRequestValidatorFactory.getSaveMarketOrderProcessRequestValidator()
									.validate((JSONObject) request.getAttribute("body"));

							if (entity != null) {
								// Process entiy and save it to data base
								boolean saved = MysqlDaoFactory.getMarketOrderProcessDaoRepository().create(entity);
								// return the entity as json with data updated
								if (saved) {
									response.setStatus(HttpServletResponse.SC_CREATED);
									HttpJsonResponse.create(response).send(entity.toJson());
								}

							}

						} catch (Exception e) {
							e.printStackTrace(); //Validation catch scope
							response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							HttpJsonResponse.create(response)
							.addData("message", e.getMessage())
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
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		new ResourceGuard(this.getServletContext(), request, response, 
				new ResourceGrantedStateListener(){

					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						
						// Buil our entity
						MarketOrderProcessEntity entity = null;
						// Validate the request
						try {
							entity = ApiRequestValidatorFactory.getSaveMarketOrderProcessRequestValidator()
									.validate((JSONObject) request.getAttribute("body"));

							if (entity != null) {
								
								
								
								
								// Process entiy and save it to data base
								boolean saved = MysqlDaoFactory.getMarketOrderProcessDaoRepository().update(entity);
								// return the entity as json with data updated
								if (saved) {
									response.setStatus(HttpServletResponse.SC_CREATED);
									HttpJsonResponse.create(response).send(entity.toJson());
								}else{
									response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
									HttpJsonResponse.create(response)
									.addData("message", 
											"Unable to proces the request. Please try again later")
									.build()
									.send();
								}

							}

						} catch (Exception e) {
							e.printStackTrace(); //Validation catch scope
							HttpJsonResponse.create(response)
							.addData("message", e.getMessage())
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

}
