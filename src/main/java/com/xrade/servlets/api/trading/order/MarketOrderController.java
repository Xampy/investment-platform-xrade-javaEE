package com.xrade.servlets.api.trading.order;

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

import com.xrade.entity.AnalysisEntity;
import com.xrade.entity.MarketOrderEntity;
import com.xrade.entity.MarketOrderProcessEntity;
import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.DatabaseConnection;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.utils.JsonHandler;
import com.xrade.validator.factory.ApiRequestValidatorFactory;
import com.xrade.web.AppConfig;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class SaveMarketOrder
 */
@WebServlet("/api/v1/order")
public class MarketOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MarketOrderController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *      
	 *  Get market order made by the member
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		new ResourceGuard(this.getServletContext(), request, response, 
				new ResourceGrantedStateListener(){

					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						
						
						String[] creds = (String[]) request.getAttribute("credentials");
						int memberId = Integer.parseInt(creds[2]);
						
						
						MemberEntity member =  MysqlDaoFactory.getMemberDaoRepository().find(memberId);
						if(member != null){
							//Get all the orders of the day
							MarketOrderEntity[] orders = MysqlDaoFactory.getMarketOrderDaoRepository().findForDayByMember(member);
							if(orders != null){
								JSONArray data = new JSONArray();
								for(MarketOrderEntity order: orders){
									JSONObject json_order = order.toJson();
									MarketOrderProcessEntity orderProcess = (MarketOrderProcessEntity)order.getProcess();
									json_order.put("process", orderProcess.toJson());
									data.put( json_order );
								}
								
								HttpJsonResponse.create(response).send(data);
							}else {
								response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
								HttpJsonResponse.create(response)
								.addData("message", "Can't handle the request. Please try again later")
								.build()
								.send();
							}
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
						MarketOrderEntity entity = null;
						// Validate the request
						try {
							entity = ApiRequestValidatorFactory.getSaveMarketOrderRequestValidato()
									.validate((JSONObject) request.getAttribute("body"));

							if (entity != null) {
								//Make necesary controls before saving order
								String[] creds = (String[]) request.getAttribute("credentials");
								int memberId = Integer.parseInt(creds[2]);
								
								//check member amount is necessary
								MemberAccountEntity account = (MemberAccountEntity) MysqlDaoFactory
										.getMemberDaoRepository()
										.find(memberId).getAccount();
								
								double tmp_amount = account.getAmount() - entity.getAmount() ;
								
								if( tmp_amount > AppConfig.BASE_AMOUNT){
									
									//check availbale lot also is nessessary
									AnalysisEntity analysis = MysqlDaoFactory
											.getAnalysisDaoRepository().find( (int) entity.getMarketAnalysisId());
									if(analysis == null){										
										response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
										HttpJsonResponse.create(response)
										.addData("message", 
												"Unable to proces the order request[Bad analysis] . Please try again later")
										.build()
										.send();
										return;
									}
									
									int tmp_lot = analysis.getAvailableLot() - entity.getLot();
									if(tmp_lot >= 0){ // && analysis.isPublished()
										//We've bought the lot
										
										//Start the transaction
										DatabaseConnection.getInstance().setAutoCommit(false);
										
										
										//update the available lot
										analysis.setAvailableLot(tmp_lot);
										if(MysqlDaoFactory.getAnalysisDaoRepository().updateAvailableLot(analysis)){
											//Update the user account amount
											account.setAmount(tmp_amount);
											boolean updated = MysqlDaoFactory.getMemberAccountDaoRepository()
												.updateAmount(account);
											if(updated){
												// Process entiy and save it to data base
												boolean saved = MysqlDaoFactory.getMarketOrderDaoRepository().create(entity);
												// return the entity as json with data updated
												if (saved) {
													
													DatabaseConnection.getInstance().commit();
													DatabaseConnection.getInstance().setAutoCommit(true);;
													
													response.setStatus(HttpServletResponse.SC_CREATED);
													HttpJsonResponse.create(response).send(entity.toJson());
												}else{
													response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
													HttpJsonResponse.create(response)
													.addData("message", 
															"Unable to proces the order request [Can't create order]. Please try again later")
													.build()
													.send();
												}
											}else{
												//We can't process
												DatabaseConnection.getInstance().rollback();
												
												response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
												HttpJsonResponse.create(response)
												.addData("message", 
														"Unable to proces the order request  [Can't update member amount ] . Please try again later")
												.build()
												.send();
											}
										}else{
											//We can't process the update of the analysis
											DatabaseConnection.getInstance().rollback();
											
											response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
											HttpJsonResponse.create(response)
											.addData("message", 
													"Unable to proces the order request  [Can't update analysis ]. Please try again later")
											.build()
											.send();
										}
										
										
									}else{
										//We can't process
										response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
										HttpJsonResponse.create(response)
										.addData("message", 
												"Unable to proces the order request  [Insufficient lot] . Please try agin later")
										.build()
										.send();
									}
									
									
								}else{
									
								}
								

							}

						} catch (Exception e) {
							e.printStackTrace(); //Validation catcj scope
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
	
	

}
