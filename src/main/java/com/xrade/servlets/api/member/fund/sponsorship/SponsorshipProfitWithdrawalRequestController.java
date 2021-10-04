package com.xrade.servlets.api.member.fund.sponsorship;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.entity.MemberInterestPaymentAccountEntity;
import com.xrade.entity.MemberInvestmentProfitWithdrawalRequestEntity;
import com.xrade.entity.MemberSponsorshipPaymentAccountEntity;
import com.xrade.entity.MemberSponsorshipProfitWithdrawalRequestEntity;
import com.xrade.entity.MemberWithdrawRequestEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.orm.DatabaseConnection;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.member.ResourceGuard;
import com.xrade.validator.factory.ApiRequestValidatorFactory;
import com.xrade.web.AppConfig;
import com.xrade.web.HttpJsonResponse;

/**
 * Servlet implementation class SponsorshipProfitWithdrawalRequestController
 */
@WebServlet("/api/v1/member/fund/sponsorship-profit/withdrawal")
public class SponsorshipProfitWithdrawalRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SponsorshipProfitWithdrawalRequestController() {
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
		new ResourceGuard(this.getServletContext(), request, response, new ResourceGrantedStateListener() {

			public void onGranted(ServletContext context, HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {

				// Buil our entity
				MemberSponsorshipProfitWithdrawalRequestEntity entity = null;
				// Validate the request
				try {
					entity = ApiRequestValidatorFactory.getMakeSponsorshipProfitWithdrawRequestValidator()
							.validate((JSONObject) request.getAttribute("body"));

					if (entity != null) {

						String[] creds = (String[]) request.getAttribute("credentials");
						int memberId = Integer.parseInt(creds[2]);

						// Check if there was not an unfullfilled request
						// Get unfilled withdraw request [not deposit]
						/*
						 * MemberInvestmentProfitWithdrawalRequestEntity
						 * unfilledRequest = MysqlDaoFactory
						 * .getMemberInvestmentProfitWithdrawRequestDaoRepository
						 * () .findUnfilledByMemberId(memberId);
						 */

						// If there is al least on withdraw request not filled
						// Send forbidden header to header
						// if(unfilledRequest != null){

						// The above rule doesn't work anyore
						// The member can member can make multiple withdrawal
						// requets

						MemberEntity member = MysqlDaoFactory.getMemberDaoRepository().find(memberId);

						// [START] Validate member
						if (member == null) {

							response.setStatus(HttpServletResponse.SC_FORBIDDEN);
							HttpJsonResponse.create(response).addData("message", "Unable to handle the request").build()
									.send();

							return;
						}
						// [END] Validate member

						// Set the amount before and the mount after here
						MemberSponsorshipPaymentAccountEntity sponsorsshipAccount = (MemberSponsorshipPaymentAccountEntity) member
								.getSponsorshipAccount();
						if (sponsorsshipAccount == null) {
							// No need of this
							// A member can ask many withdrawal requel
							// until his amount is unsufiicient

							response.setStatus(HttpServletResponse.SC_FORBIDDEN);
							HttpJsonResponse.create(response)
									.addData("message", "Unable to process the reuest account null").build().send();
						} else {

							// Set the amount before and the mount after here
							/*
							 * MemberSponsorshipPaymentAccountEntity account =
							 * (MemberSponsorshipPaymentAccountEntity)
							 * MysqlDaoFactory .getMemberDaoRepository()
							 * .find(memberId).getSponsorshipAccount();
							 */

							// the sponsorship account is not null
							// update data

							entity.setAmountBefore(sponsorsshipAccount.getAmount());
							entity.setMemberSponsorshipAccountId(sponsorsshipAccount.getId());
							double tmp = sponsorsshipAccount.getAmount() - entity.getAmount();

							// Check if after the withdrawal the amount is not
							// negative
							if (tmp >= 0) {
								if (entity.getAmount() >= AppConfig.BASE_SPONSORSHIP_WITHDRAWAL_AMOUNT) {
									entity.setAmountAfter(tmp);
									
									// Process entiy and save it to data base
									DatabaseConnection.getInstance().setAutoCommit(false);
									boolean saved = MysqlDaoFactory
											.getMemberSponsorshipProfitWithdrawRequestDaoRepository().create(entity);
									// return the entity as json with data
									// updated
									if (saved) {
										
										//[START] save a withdraw global to database
										// Create a withdrawal request on
										// investment profit
										MemberWithdrawRequestEntity withdrawalRequest = new MemberWithdrawRequestEntity();
										withdrawalRequest.setPayment(entity.getPayment());
										withdrawalRequest.setAmount(entity.getAmount());
										withdrawalRequest.setAmountBefore(entity.getAmountBefore());
										withdrawalRequest.setAmountAfter(entity.getAmountAfter());
										withdrawalRequest.setMetadata(entity.getMetadata());
										withdrawalRequest.setMemberAccountId(
												((MemberAccountEntity) member.getAccount()).getId());
										withdrawalRequest.setAccountType("sponsor");
										
										JSONObject extra = new JSONObject();
										extra.put("name",  member.getFirstname() );
										extra.put("acc", sponsorsshipAccount.getId());												
										withdrawalRequest.setExtra(extra.toString());

										saved = MysqlDaoFactory.getMemberWithdrawRequestDaoRepository()
												.create(withdrawalRequest);
										if (saved) {
											DatabaseConnection.getInstance().commit();
											DatabaseConnection.getInstance().setAutoCommit(true);

											response.setStatus(HttpServletResponse.SC_CREATED);
											HttpJsonResponse.create(response).send(entity.toJson());
										} else {
											// We can't create the withdrawal
											// request send error
											DatabaseConnection.getInstance().setAutoCommit(true);
											
											response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
											HttpJsonResponse.create(response)
													.addData("message",
															"Unable to proces the withdrawal request. Please try agin later")
													.build().send();

										}
										//[START] save a withdraw global to database
										
									} else {
										// The withdraw request fails
										// when trying to update data in the
										// database
										
										DatabaseConnection.getInstance().setAutoCommit(true);
										
										response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
										HttpJsonResponse.create(response)
												.addData("message",
														"Unable to proces the with draw request. Please try agin later")
												.build().send();
									}
								} else {
									// Insuficient amount on the account
									// send a forbidenr equest
									response.setStatus(HttpServletResponse.SC_FORBIDDEN);
									HttpJsonResponse.create(response)
											.addData("message",
													"You need to have a base amount of "
															+ AppConfig.BASE_SPONSORSHIP_WITHDRAWAL_AMOUNT + " USD")
											.build().send();
								}
							} else {
								// The amount is then negative
								// send forbidden request
								response.setStatus(HttpServletResponse.SC_FORBIDDEN);
								HttpJsonResponse.create(response)
										.addData("message", "You can't withdrawing more than what you have.").build()
										.send();
							}

						}

					} else {
						response.setStatus(HttpServletResponse.SC_FORBIDDEN);
						HttpJsonResponse.create(response).addData("message", "Unable to process the reuest").build()
								.send();
					}

				} catch (Exception e) {
					e.printStackTrace();
					// Validation catch scope
					// We have error while parsin the request body
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					HttpJsonResponse.create(response).addData("message", e.getMessage()).build().send();

				}

			}

			public void onDenied(DefaultResourceException e, ServletContext context, HttpServletRequest request,
					HttpServletResponse response) throws ServletException, IOException {
				HttpJsonResponse.create(response).addData("message", e.getMessage()).build().send();

			}

		});
	}

}
