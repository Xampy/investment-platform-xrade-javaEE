package com.xrade.servlets.trader;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.entity.AnalysisDataEntity;
import com.xrade.entity.AnalysisEntity;
import com.xrade.service.AnalysisDataService;
import com.xrade.service.AnalysisService;
import com.xrade.utils.Common;

/**
 * Servlet implementation class TraderAnalysisController
 */
@WebServlet("/trader/analysis")
public class TraderAnalysisController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TraderAnalysisController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Fetch data from database
		/*AnalysisService analysisService = new AnalysisService();
		AnalysisEntity[] data = analysisService.getUncompletedAnalysis();
				
						
		String base = Common.BASE_URL;
		request.setAttribute("BASE_URL", base);

		request.setAttribute("data", data);
		this.getServletContext().getRequestDispatcher("/trader/views/analysis.html.jsp").forward(request, response);
		*/
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
