package com.xrade.service.member;

import java.sql.SQLException;
import java.util.Optional;

import org.json.JSONObject;

import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberAccountSponsorshipPaymentEntity;
import com.xrade.entity.MemberCardTransactionEntity;
import com.xrade.entity.MemberDepositRequestEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.entity.MemberInterestPaymentAccountEntity;
import com.xrade.entity.MemberInvestmentProfitWithdrawalRequestEntity;
import com.xrade.entity.MemberPerfectMoneyTransactionEntity;
import com.xrade.entity.MemberSponsorshipPaymentAccountEntity;
import com.xrade.entity.MemberSponsorshipProfitWithdrawalRequestEntity;
import com.xrade.entity.MemberWithdrawRequestEntity;
import com.xrade.models.user.transaction.MemberMoneyTransaction;
import com.xrade.orm.DatabaseConnection;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.service.mail.factory.MailSenderFactory;
import com.xrade.service.mail.sender.DepositRequestMailSender;
import com.xrade.web.AppConfig;
import com.xrade.web.XPaymentMethodType;

public class MemberAccountManagementService {

	/**
	 * Update the member account amount By deposit request or withdraw request
	 * 
	 * 
	 * @param depositRequest
	 * @return
	 */
	public boolean updateAccountAmount(MemberDepositRequestEntity depositRequest) {
		return MysqlDaoFactory.getMemberAccountManagementRepository().updateAmountByRequest(depositRequest);
	}

	/**
	 * Create de posit trace for the current deposit
	 * 
	 * @param account
	 * @param paymentMethod
	 * @param transaction
	 * @return
	 */
	private MemberDepositRequestEntity createDeposit(MemberEntity member,MemberAccountEntity account,
			XPaymentMethodType paymentMethod, MemberMoneyTransaction transaction) {
		MemberDepositRequestEntity depositTrace = new MemberDepositRequestEntity();
		depositTrace.setMemberAccountId(account.getId());
		depositTrace.setPayment(paymentMethod.toString());
		depositTrace.setAmount(transaction.getAmount());
		depositTrace.setAmountBefore(account.getAmount() - transaction.getAmount());
		depositTrace.setAmountAfter(account.getAmount());
		depositTrace.setFilled(true);
		
		JSONObject extra = new JSONObject();
		extra.put("name",  member.getFirstname() );
		extra.put("acc", account.getId());
		extra.put("pid", AppConfig.BASE_TRANSACTION_NUMBER + transaction.getId());
		depositTrace.setExtra(extra.toString());

		return depositTrace;
	}

	/**
	 * Update the transaction filled state
	 * 
	 * @param transaction
	 * @param paymentMethod
	 * @return
	 */
	private boolean updateTransactionFilledStatus(MemberMoneyTransaction transaction,
			XPaymentMethodType paymentMethod) {

		if (paymentMethod == XPaymentMethodType.PerfectMoney) {
			return MysqlDaoFactory.getMemberPerfectMoneyTransactionDaoRepository()
					.updateFillStatus((MemberPerfectMoneyTransactionEntity) transaction);
		}else if (paymentMethod == XPaymentMethodType.Card){
			return MysqlDaoFactory.getMemberCardTransactionDaoRepository()
					.updateFillStatus((MemberCardTransactionEntity) transaction);
		}

		return false;
	}

	/**
	 * Update the member account after successfully payment ib deposit
	 * 
	 * @param paymentMethod
	 * @param transaction
	 * @param member
	 * @return
	 */
	public boolean updateAmountByPayment(XPaymentMethodType paymentMethod, MemberMoneyTransaction transaction,
			MemberEntity member) {

		MemberAccountEntity account = (MemberAccountEntity) member.getAccount();

		try {
			DatabaseConnection.getInstance().setAutoCommit(false);

			// Make sure that the account is not null
			if (account != null) {

				// Check if it's the first desposit fot the user
				// if case update hios sponsored sponsorship
				// account amount
				boolean isFirstDeposit = false;
				Optional<Boolean> check = MysqlDaoFactory.getMemberDepositRequestDaoRepository()
						.isMemberAccountFirstDeposit((int) account.getId());
				if (check.isPresent()) {
					isFirstDeposit = check.get();
				}

				account.setAmount(account.getAmount() + transaction.getAmount());
				if (MysqlDaoFactory.getMemberAccountDaoRepository().updateAmount(account)) {
					// update the transaction filled status
					if (this.updateTransactionFilledStatus(transaction, paymentMethod)) {
						// We succeed to update the transaction

						// Save a deposit trace

						// Create deposit
						MemberDepositRequestEntity depositTrace = 
								this.createDeposit(member, account, paymentMethod, transaction);

						if (MysqlDaoFactory.getMemberDepositRequestDaoRepository().create(depositTrace)) {

							// Here everyThing is OK

							// Update the sponsor account if it the first
							// deposit
							// from this user
							if (isFirstDeposit) {
								// [START] Pay the sponsorer
								MemberEntity sponsorer = MysqlDaoFactory.getMemberDaoRepository()
										.findMemberByReference(member.getReference());

								MemberSponsorshipPaymentAccountEntity sponsorAccount = sponsorer
										.getSponsorshipAccount();
								if (sponsorAccount != null) {
									double toAdd = transaction.getAmount() * AppConfig.SPONSORSHIP_COMMISSION_RATE;

									if (MysqlDaoFactory.getMemberSponsorshipPaymentAccountDaoRepository()
											.updateAmount(sponsorAccount)) {
										MemberAccountSponsorshipPaymentEntity sponsorPayment = new MemberAccountSponsorshipPaymentEntity();

										sponsorPayment.setAmountBefore(sponsorAccount.getAmount() - toAdd);
										sponsorPayment.setAmountAfter(sponsorAccount.getAmount());
										sponsorPayment.setMemberAccountId(
												((MemberAccountEntity) sponsorer.getAccount()).getId());

										if (MysqlDaoFactory.getMemberSponsorhipPaymentDaoRepository()
												.create(sponsorPayment)) {

										}
									}
								}
								// [END] Pay the sponsorer
							}

							DatabaseConnection.getInstance().commit();
							DatabaseConnection.getInstance().setAutoCommit(true);

							// Send Mail to the member about the deposit
							// request
							DepositRequestMailSender depositMailSender = MailSenderFactory
									.getDepositRequestMailSender();
							depositMailSender.setRecipientAddress(member.getEmail());
							depositMailSender.setAmount(transaction.getAmount());
							depositMailSender.setUsername(member.getFirstname());

							depositMailSender.prepareMessage();
							depositMailSender.send();

							return true;

						} else {
							// We cant' create the deposit trace
							DatabaseConnection.getInstance().rollback();
							DatabaseConnection.getInstance().setAutoCommit(true);

							return false;
						}

					} else {
						DatabaseConnection.getInstance().rollback();
						DatabaseConnection.getInstance().setAutoCommit(true);

						return false;
					}

				} else {
					// Can't update the amount
					return false;
				}
			} else {
				// The account is null

				return false;
			}

		} catch (Exception e) {
			// Something went wrong when updating
			// ACCOUNT data in the database
			try {
				DatabaseConnection.getInstance().rollback();
				DatabaseConnection.getInstance().setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return false;
		}

	}

	// #########################################
	// #
	// #
	// # Withdrawaling process
	// #
	// #
	// #####################################################################

	/**
	 * Process a withdrawal on the main account
	 * 
	 * @param account
	 * @param request
	 * @return an array of 2 dimension containning art 0 the status and at 1 the
	 *         message
	 * @throws SQLException
	 */
	private String[] handleAccountWithdrawalRequest(MemberAccountEntity account, MemberWithdrawRequestEntity request)
			throws SQLException {

		String[] result = new String[2];

		if (request.isFilled() == false) {
			// Chec the amount after withdrawaling
			double new_amount = account.getAmount() - request.getAmount();
			if (new_amount >= AppConfig.BASE_AMOUNT) {
				// After withdrawal there will remain
				// the minimum on the account then we process
				// the withdrawal request
				request.setAmountBefore(account.getAmount());
				account.setAmount(new_amount);
				request.setAmountAfter(account.getAmount());
				if (MysqlDaoFactory.getMemberAccountDaoRepository().updateAmount(account)) {

					// Try to update the wrequest filled status
					if (MysqlDaoFactory.getMemberWithdrawRequestDaoRepository().updateFilledStatus(request)) {
						DatabaseConnection.getInstance().commit();
						DatabaseConnection.getInstance().setAutoCommit(true);

						result[0] = "1";
						result[1] = "Success";

					} else {
						// we xan't update the filled status
						DatabaseConnection.getInstance().rollback();
						DatabaseConnection.getInstance().setAutoCommit(true);

						result[0] = "0";
						result[1] = "Unable to handle the request";
					}

				} else {
					// we xan't update the account amount
					result[0] = "0";
					result[1] = "Unable to handle the request";
				}

			}else{
				//Not sufficient amount for withdrawaling
				result[0] = "0";
				result[1] = "Unable to handle the request...Insuficient Amount";
			}
		} else {
			// Request was already filled
			result[0] = "0";
			result[1] = "Unable to handle the request. Already filled";
		}

		return result;
	}

	/**
	 * Process a withdrawal on the profit payment account
	 * 
	 * @param account
	 * @param request
	 * @return an array of 2 dimension containning art 0 the status and at 1 the
	 *         message
	 * @throws SQLException
	 */
	private String[] handleProfitAccountWithdrawalRequest(MemberInterestPaymentAccountEntity account,
			MemberWithdrawRequestEntity request, MemberInvestmentProfitWithdrawalRequestEntity profitWithdrawalRequest)
			throws SQLException {

		String[] result = new String[2];

		if (request.isFilled() == false) {

			// Chec the amount after withdrawaling
			double new_amount = account.getAmount() - request.getAmount();
			if (new_amount >= 0) {
				// After withdrawal there will remain
				// the minimum on the account then we process
				// the withdrawal request
				request.setAmountBefore(account.getAmount());
				account.setAmount(new_amount);
				request.setAmountAfter(account.getAmount());
				if (MysqlDaoFactory.getMemberInterestPaymentAccountDaoRepository().updateAmount(account)) {

					// Try to update the wrequest filled status
					if (MysqlDaoFactory.getMemberWithdrawRequestDaoRepository().updateFilledStatus(request)) {
						DatabaseConnection.getInstance().commit();
						DatabaseConnection.getInstance().setAutoCommit(true);

						result[0] = "1";
						result[1] = "Success";

					} else {
						// we xan't update the filled status
						DatabaseConnection.getInstance().rollback();
						DatabaseConnection.getInstance().setAutoCommit(true);

						result[0] = "0";
						result[1] = "Unable to handle the request";
					}

				} else {
					// we xan't update the account amount
					result[0] = "0";
					result[1] = "Unable to handle the request";
				}

			}else{
				//Not sufficient amount for withdrawaling
				result[0] = "0";
				result[1] = "Unable to handle the request...Insuficient Amount";
			}
		} else {
			// Request was already filled
			result[0] = "0";
			result[1] = "Unable to handle the request. Already filled";
		}

		return result;
	}
	
	
	/**
	 * Process a withdrawal on the profit payment account
	 * 
	 * @param account
	 * @param request
	 * @return an array of 2 dimension containning art 0 the status and at 1 the
	 *         message
	 * @throws SQLException
	 */
	private String[] handleSponsorshipAccountWithdrawalRequest(MemberSponsorshipPaymentAccountEntity account,
			MemberWithdrawRequestEntity request, MemberSponsorshipProfitWithdrawalRequestEntity profitWithdrawalRequest)
			throws SQLException {

		String[] result = new String[2];

		if (request.isFilled() == false) {

			// Chec the amount after withdrawaling
			double new_amount = account.getAmount() - request.getAmount();
			if (new_amount >= 0) {
				// After withdrawal there will remain
				// the minimum on the account then we process
				// the withdrawal request
				request.setAmountBefore(account.getAmount());
				account.setAmount(new_amount);
				request.setAmountAfter(account.getAmount());
				if (MysqlDaoFactory.getMemberSponsorshipPaymentAccountDaoRepository().updateAmount(account)) {

					// Try to update the wrequest filled status
					if (MysqlDaoFactory.getMemberWithdrawRequestDaoRepository().updateFilledStatus(request)) {
						DatabaseConnection.getInstance().commit();
						DatabaseConnection.getInstance().setAutoCommit(true);

						result[0] = "1";
						result[1] = "Success";

					} else {
						// we xan't update the filled status
						DatabaseConnection.getInstance().rollback();
						DatabaseConnection.getInstance().setAutoCommit(true);

						result[0] = "0";
						result[1] = "Unable to handle the request";
					}

				} else {
					// we xan't update the account amount
					result[0] = "0";
					result[1] = "Unable to handle the request";
				}

			}else{
				//Not sufficient amount for withdrawaling
				result[0] = "0";
				result[1] = "Unable to handle the request...Insuficient Amount";
			}
		} else {
			// Request was already filled
			result[0] = "0";
			result[1] = "Unable to handle the request. Already filled";
		}

		return result;
	}

	/**
	 * Update the member account after successfully payment ib deposit
	 * 
	 * @param paymentMethod
	 * @param withdrawalRequest
	 * @param extra
	 *            it concern withdrawal which is not on the investment amount
	 *            account
	 * @param member
	 * @return an array of 2 dimension containning art 0 the status and at 1 the
	 *         message
	 */
	public String[] updateAmountByWithdrawal(MemberWithdrawRequestEntity withdrawalRequest, Object requestExtra,
			MemberEntity member) {

		MemberAccountEntity account = (MemberAccountEntity) member.getAccount();

		try {
			DatabaseConnection.getInstance().setAutoCommit(false);

			// Make sure that the account is not null
			if (account != null) {

				// Check the withdrawal request type
				switch (withdrawalRequest.getAccountType()) {
				case "invest":
					// We need to withdrawal on
					return this.handleAccountWithdrawalRequest(account, withdrawalRequest);
				case "profit":
					return this.handleProfitAccountWithdrawalRequest(member.getInterestAccount(), withdrawalRequest, null);
				case "sponsor":
					return this.handleSponsorshipAccountWithdrawalRequest(member.getSponsorshipAccount(), withdrawalRequest, null);
				default:
					// We got an expected value
					return null;
				}
			} else {
				return null;
			}

		} catch (Exception e) {
			// Something went wrong when updating
			// ACCOUNT data in the database
			try {
				DatabaseConnection.getInstance().rollback();
				DatabaseConnection.getInstance().setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return null;
		}

	}

}
