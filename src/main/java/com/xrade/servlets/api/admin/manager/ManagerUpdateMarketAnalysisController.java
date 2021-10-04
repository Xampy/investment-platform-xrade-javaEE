package com.xrade.servlets.api.admin.manager;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.entity.AnalysisDataEntity;
import com.xrade.entity.AnalysisEntity;
import com.xrade.forms.MarketAnalysisForm;
import com.xrade.service.AnalysisService;
import com.xrade.utils.Common;

/**
 * Servlet implementation class ManagerUpdateMarketAnalysisController
 */
@WebServlet("/manager/market-analysis/update")
public class ManagerUpdateMarketAnalysisController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerUpdateMarketAnalysisController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String base = Common.BASE_URL;
		request.setAttribute("BASE_URL", base);
		
		
		//Get the url query parameter
		long analysisId = Long.parseLong(request.getParameter("analysis_id"));
		
		//Fet data from database
		AnalysisService analysisService = new AnalysisService();
		AnalysisDataEntity data = analysisService.getAnalysisById(analysisId);
		
		
		request.setAttribute("data", data);
		
		this.getServletContext().getRequestDispatcher("/admin/views/market/market-analysis-detail.html.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		//init form
		MarketAnalysisForm form = new MarketAnalysisForm();
		try {
			form.handleRequest(request);
			
			//Persist the data
			AnalysisService analysisService = new AnalysisService();
			analysisService.saveMarketAnalysis(form.getData());
			
			request.setAttribute("success", "Analysis update successfully");
			doGet(request, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print(e.getMessage());
			e.printStackTrace();
			
			request.setAttribute("errors", form.getErrors());
			doGet(request, response);
		}
	
	}

}
