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
 * Servlet implementation class CloseMarketOrder
 */
@WebServlet("/api/v1/order/close")
public class CloseMarketOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CloseMarketOrderController() {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		new ResourceGuard(this.getServletContext(), request, response, 
				new ResourceGrantedStateListener(){

					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						// Buil our entity
						MarketOrderProcessEntity entity = null;
						// Validate the request
						try {
							entity = ApiRequestValidatorFactory.getUpdateMarketOrderProcessValidator()
									.validate((JSONObject) request.getAttribute("body"));

							if (entity != null) {
								// Make necesary controls before saving order
								// check member amount is necessary
								// check availbale lot also is nessessary
								// Process entiy and save it to data base
								entity = MysqlDaoFactory.getMarketOrderProcessDaoRepository().close(entity);
								// return the entity as json with data updated
								if (entity != null) {
									HttpJsonResponse.create(response).send(entity.toJson());
								}
							}
								

						} catch (Exception e) {
							//Validation catch scope
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

}
