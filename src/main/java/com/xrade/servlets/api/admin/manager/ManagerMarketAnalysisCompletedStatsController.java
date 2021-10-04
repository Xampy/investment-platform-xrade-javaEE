package com.xrade.servlets.api.admin.manager;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.entity.AnalysisDataEntity;
import com.xrade.entity.AnalysisEntity;
import com.xrade.service.AnalysisService;
import com.xrade.service.MarketOrderService;
import com.xrade.utils.Common;

/**
 * Servlet implementation class ManagerMarketAnalysisCompletedStatsController
 */
@WebServlet("/manager/market-analysis/completed/stats")
public class ManagerMarketAnalysisCompletedStatsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerMarketAnalysisCompletedStatsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Fetch data from database
		//AnalysisService analysisService = new AnalysisService();
		//		AnalysisEntity[] data = analysisService.getCompletedAnalysis();
						
		String base = Common.BASE_URL;
		request.setAttribute("BASE_URL", base);
		
		//Get the url query parameter
		long analysisId = Long.parseLong(request.getParameter("analysis_id"));
				
		//Fetch data from database
		AnalysisService analysisService = new AnalysisService();
		AnalysisEntity[] data = {analysisService.getFullAnalysisById(analysisId)};
		System.out.println(data[0].getAnalysisData().getCapital() + " Capital");
		
		
		//Count order for the analysis
		MarketOrderService marketOrderService = new MarketOrderService();
		long countOrders = marketOrderService.countOrderBy(data[0].getId()); //Data[0] is the market
		int maxAmount = marketOrderService.getMaxAmountOrderBy(data[0].getId());
		
		request.setAttribute("data", data);
		request.setAttribute("count_order", countOrders);
		request.setAttribute("max_amount", maxAmount);
		
		this.getServletContext().getRequestDispatcher("/admin/views/market/market-analysis-completed-stats.html.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
