package com.xrade.servlets.trader;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.forms.AnalysisDataForm;
import com.xrade.service.AnalysisDataService;
import com.xrade.utils.Common;

/**
 * Servlet implementation class TraderNewAnalysisServlet
 */
@WebServlet("/trader/analysis/new")
public class TraderNewAnalysisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TraderNewAnalysisServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*request.setAttribute("BASE_URL", Common.BASE_URL);	
		this.getServletContext().getRequestDispatcher("/trader/views/new-analysis.html.jsp").forward(request, response);
		*/
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * 
	 * Will enhanced with api structure
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Got the form vvalidator
		/*AnalysisDataForm form = new AnalysisDataForm();
		try {
			form.handleRequest(request);
			AnalysisDataService service = new AnalysisDataService();
			service.saveAnalysisData(form.getData());
			
			request.setAttribute("success", "Analysis created successfully");
			doGet(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("errors", form.getErrors());
			doGet(request, response);
		}*/
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
		
		
	}

}
