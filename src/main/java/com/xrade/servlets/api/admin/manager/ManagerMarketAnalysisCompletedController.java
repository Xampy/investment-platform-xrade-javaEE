package com.xrade.servlets.api.admin.manager;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.entity.AnalysisEntity;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.service.AnalysisService;
import com.xrade.utils.Common;

/**
 * Servlet implementation class ManagerMarketAnalysisCompletedController
 */
@WebServlet("/manager/market-analysis/completed")
public class ManagerMarketAnalysisCompletedController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerMarketAnalysisCompletedController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Fetch data from database
		AnalysisService analysisService = new AnalysisService();
		AnalysisEntity[] data = analysisService.getCompletedAnalysis();
				
		String base = Common.BASE_URL;
		request.setAttribute("BASE_URL", base);

		request.setAttribute("data", data);
		this.getServletContext().getRequestDispatcher("/admin/views/market/market-analysis-completed.html.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		if( request.getParameter("closing").equals("true")){
			System.out.println(request.getParameter("an_id"));
			int analysisId = Integer.parseInt(request.getParameter("an_id"));
			if(analysisId > 0){
				AnalysisEntity analysis = MysqlDaoFactory.getAnalysisDaoRepository().find(analysisId);
				analysis.setPublished(true);
				MysqlDaoFactory.getAnalysisDaoRepository().updatePublished(analysis);
			}
		}
		
		doGet(request, response);
	}

}
