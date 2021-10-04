package com.xrade.service.member;

import java.sql.SQLException;

import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberInterestPaymentAccountEntity;
import com.xrade.entity.MemberAccountInterestPaymentEntity;
import com.xrade.entity.MemberDepositRequestEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.entity.MemberWithdrawRequestEntity;
import com.xrade.orm.DatabaseConnection;
import com.xrade.orm.dao.factory.MysqlDaoFactory;
import com.xrade.web.AppConfig;

public class MemberService {
	
	public MemberEntity[] getMembers(){
		return MysqlDaoFactory.getMemberDaoRepository().findAll(0, 0);
	}

	public MemberEntity getMemberById(int memberId) {
		return MysqlDaoFactory.getMemberDaoRepository().find(memberId);
	}
	
	public MemberEntity getMemberByAccountId(int accountId) {
		return MysqlDaoFactory.getMemberDaoRepository().getMemberByAccountId(accountId);
	}

	
	
	
	
	//Deposit and withdraw

	public int getWithdrawRequestCount(long id) {
		return  MysqlDaoFactory.getMemberWithdrawRequestDaoRepository().countByMemberAccountId((int) id);
	}
	
	public int getDepositRequestCount(long id) {
		return  MysqlDaoFactory.getMemberDepositRequestDaoRepository().countByMemberAccountId((int) id);
	}

	public MemberDepositRequestEntity[] getMembersDepositRequests() {
		return MysqlDaoFactory.getMemberDepositRequestDaoRepository().findAll(0, 0);
	}
	
	public MemberDepositRequestEntity[] getFilledMembersDepositRequests() {
		return MysqlDaoFactory.getMemberDepositRequestDaoRepository().findFilledAll(0, 0);
	}

	public MemberWithdrawRequestEntity[] getMembersWithdrawRequests() {
		return MysqlDaoFactory.getMemberWithdrawRequestDaoRepository().findAll(0, 0);
	}
	

	
	
	public MemberDepositRequestEntity getMemberDepositRequestById(long withdrawRequestId) {
		return MysqlDaoFactory.getMemberDepositRequestDaoRepository().find((int) withdrawRequestId);
	}

	/**
	 * Pay to member their interest
	 * 
	 * It take each member and increase his amount by 1% and save it
	 * after update the total interest payment account
	 * 
	 * if all account are updated then it returns true otherwise
	 * it return false
	 * 
	 * @return state a boolean to specify if all accounts are updated or not
	 */
	public boolean payInterestToMembers() {
		
		
		MemberEntity[] members = this.getMembers();
		
		//Hold the total of member payed
		int memberPayed = 0;
		
		if(members != null && members.length > 0){
			
			try{
				//Start transaction
				DatabaseConnection.getInstance().setAutoCommit(false);
				
				//For each member saved, increase hist benefit amount
				for(MemberEntity member: members){
					MemberAccountEntity account = (MemberAccountEntity) member.getAccount();
					MemberInterestPaymentAccountEntity interestAccount = member.getInterestAccount();
					
					double toAdd =  (0.95) * account.getAmount()* (0 + AppConfig.INTEREST_RATE);
					System.out.println("\nInterest paymet " + toAdd + " $\n");
					double new_amount = account.getAmount() + toAdd;
					
					MemberAccountInterestPaymentEntity payment = new MemberAccountInterestPaymentEntity();
					//Update the payment data
					
					//[START] Not valid line will be replace with interest account
					payment.setAmountAfter(new_amount); //Not valid line
					payment.setAmountBefore(account.getAmount()); //Not valid line
					payment.setMemberAccountId(account.getId());
					//[END] Not valid line will be replace with interest account
					
					
					
					//account.setAmount( new_amount ); not this account
					//Get the lastest amount in the interest payment account
					//and add the new amount
					interestAccount.setAmount(interestAccount.getAmount() + toAdd );
					
					
					
					// MysqlDaoFactory.getMemberAccountDaoRepository().updateAmount(account
					
					//Update the interest payment account amount
					//if an error occured rollback the transaction
					if( MysqlDaoFactory.getMemberInterestPaymentAccountDaoRepository().updateAmount(interestAccount)){
						
						
						//Save the interest payment
						//if an error occured rollback the transaction
						//But if success commit the transaction and return true
						if( MysqlDaoFactory.getMemberInterestPaymentDaoRepository().create(payment)){
							
							//We successfully pay a member
							//Then increase the payed member counter
							memberPayed += 1;
							
						}else {
							//Roll back any transaction in the database
							DatabaseConnection.getInstance().rollback();
							DatabaseConnection.getInstance().setAutoCommit(true);
							return false;
						}
						
						
					}else {
						//Roll back any transaction in the database
						DatabaseConnection.getInstance().rollback();
						DatabaseConnection.getInstance().setAutoCommit(true);
						return false;
					}
				}
				//[END For] Filling each member account
				
				//Commit the transaction  if all members have been
				//payed successfully
				if( memberPayed == members.length){
					
					DatabaseConnection.getInstance().commit();
					
					//Set the auo commit to its 
					//primary state
					DatabaseConnection.getInstance().setAutoCommit(true);
					
					return true;
				}else{
					//Count payed members is not equal
					//to the ouptuted members count from
					//datanase we rollback updates and return failure status
					DatabaseConnection.getInstance().rollback();
					DatabaseConnection.getInstance().setAutoCommit(true);
				}
				
			}catch(Exception e){
				e.printStackTrace();
				try {
					DatabaseConnection.getInstance().rollback();
					DatabaseConnection.getInstance().setAutoCommit(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				return false;
			}
			
		}else{
			//members array is null or the length is not
			//grater than 0
			//then we return failure status
			return false;
		}
		
		return false;
		
		
	}

	
}
