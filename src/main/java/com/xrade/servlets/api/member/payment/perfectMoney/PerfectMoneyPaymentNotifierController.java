package com.xrade.servlets.api.member.payment.perfectMoney;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.entity.MemberEntity;
import com.xrade.entity.MemberPerfectMoneyTransactionEntity;
import com.xrade.forms.PerfectMoneyPaymentStatusForm;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.service.member.MemberAccountManagementService;
import com.xrade.service.perfectMoney.PerfectMoneyService;
import com.xrade.validator.factory.FormValidatorFactory;
import com.xrade.web.HttpJsonResponse;
import com.xrade.web.XPaymentMethodType;

/**
 * Servlet implementation class PerfectMoneyPaymentNotifierController
 */
@WebServlet("/api/v1/fill-account/deposit/perfect-money/check")
public class PerfectMoneyPaymentNotifierController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerfectMoneyPaymentNotifierController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Handle a perfect payment status
		
		//Validate hte form sent
		try{
			//Validate the form sent
			PerfectMoneyPaymentStatusForm form = FormValidatorFactory
					.getPerfectMoneyPaymentStatusFormValidator().validate(request);
			
			
			//Get the transaction by session id and
			//payment id
			MemberPerfectMoneyTransactionEntity transaction = 
					MysqlDaoFactory.getMemberPerfectMoneyTransactionDaoRepository()
						.findByIdentifier(form.getSessionId());
			
			//If the transaction is null
			//and the payment_id is not equal to the 
			//provided payment id send forbidden response
			if(transaction != null && transaction.isFilled() == false 
					&& transaction.getPaymentId().equals(transaction.getPaymentId())){
				
				//Validate the v2 has
				PerfectMoneyService perfectMoneyService = new PerfectMoneyService();
				String v2HashGenerated = perfectMoneyService.generateV2Hash(form);
				
				if(v2HashGenerated.equals(form.getV2Hash())){
					//Hash is validated
					//Update the member account amount
					MemberEntity member = MysqlDaoFactory.getMemberDaoRepository()
							.getMemberByAccountId(transaction.getMemberAccountId());
					
					if(member != null){
						
						
						//Update the member amount
						MemberAccountManagementService accountService = new MemberAccountManagementService();
						boolean updated = accountService.updateAmountByPayment(
								XPaymentMethodType.PerfectMoney, transaction, member);
						if(updated){
							//Account amount was updated
							//Send the member data as response
							MemberEntity memberUpdated = MysqlDaoFactory.getMemberDaoRepository().find((int) member.getId());
							HttpJsonResponse.create(response).send(memberUpdated.toJson());
						}else{
							response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
						}	
					}else{
						//We don'have member 
						//or error occured
						response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					}
					
				}else{
					//The hash is not equal
					//send forbidden request
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				}
				
				
			}else{
				//Transaction infortaion is null
				//or transaction already filled
				//or the payment id not the same as in database
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
			
			
		}catch(Exception e){
			//Form is invalid
			//throw forbidden status
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}

}
