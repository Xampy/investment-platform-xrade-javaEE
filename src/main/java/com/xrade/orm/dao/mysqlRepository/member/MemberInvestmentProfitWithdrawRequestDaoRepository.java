package com.xrade.orm.dao.mysqlRepository.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.xrade.entity.MemberInvestmentProfitWithdrawalRequestEntity;
import com.xrade.entity.MemberWithdrawRequestEntity;
import com.xrade.orm.dao.AbstractDaoRepository;

public class MemberInvestmentProfitWithdrawRequestDaoRepository extends AbstractDaoRepository<MemberInvestmentProfitWithdrawalRequestEntity> {
	
	public  static final String TABLE_COLUMN_PAYMENT =  "payment";
	public  static final String TABLE_COLUMN_AMOUNT = "amount";
	public  static final String TABLE_COLUMN_AMOUNT_BEFORE = "amount_before";
	public  static final String TABLE_COLUMN_AMOUNT_AFTER = "amount_after";
	public  static final String TABLE_COLUMN_METADATA = "metadata";
	public  static final String TABLE_COLUMN_FILLED = "filled";
	public  static final String TABLE_COLUMN_CREATED_AT = "created_at";
	
	public  static final String TABLE_COLUMN_INTEREST_ACCOUNT_ID = "interest_account_id";
	
	public  static final String TABLE_NAME = "member_interest_withdraw_request";
	
	public MemberInvestmentProfitWithdrawRequestDaoRepository(Connection connect) {
		super(connect);
		this.setTable(TABLE_NAME);
	}

	@Override
	public boolean create(MemberInvestmentProfitWithdrawalRequestEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query +=  "(payment, amount, amount_before, amount_after, metadata, filled, created_at, interest_account_id) ";
		query += "VALUES(?, ?, ?, ?, ?, ?, now(), ?)";
		
		System.out.println("Member interest withdrawal Query ADD Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, obj.getPayment());
			stm.setDouble(2, obj.getAmount());
			stm.setDouble(3, obj.getAmountBefore());
			stm.setDouble(4, obj.getAmountAfter());
			stm.setString(5, obj.getMetadata());
			stm.setBoolean(6, obj.isFilled());
			//stm.setString(7, obj.getDate());
			stm.setLong(7, obj.getMemberInterestAccountId());
			
			stm.executeUpdate();
			
			res =  stm.getGeneratedKeys();
			System.out.println( res.getFetchSize() );
			
			if(res != null && res.next()){
				//UYpdate the object id here
				int id = res.getInt(1);
				System.out.println("Inserted id ---> " + id);
				
				obj.setId(id);
				
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		    if (res != null) {
		        try {
		            res.close();
		        } catch (SQLException ex) {
		            // ignore
		        }
		    }

		    if (stm != null) {
		        try {
		            stm.close();
		        } catch (SQLException ex) {
		            // ignore
		        }
		    }
		}
		return false;
	}

	@Override
	public boolean update(MemberInvestmentProfitWithdrawalRequestEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MemberInvestmentProfitWithdrawalRequestEntity[] findAll(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberInvestmentProfitWithdrawalRequestEntity find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	/**
	 * Fint unfilled investment profit withdrawal request by member id
	 * If all request are filled then we can create a new request
	 * otherelse we can't
	 * 
	 * @param memberId the member id
	 * @return
	 */
	public  MemberInvestmentProfitWithdrawalRequestEntity findUnfilledByMemberId(int memberId) {
		String query = "SELECT ";
		query += TABLE_NAME + ".id, ";
		query += TABLE_NAME + ".payment, ";
		query += TABLE_NAME + ".amount, ";
		query += TABLE_NAME + ".amount_before, ";
		query += TABLE_NAME + ".amount_after, ";
		query += TABLE_NAME + ".metadata, ";
		query += TABLE_NAME + ".filled, ";
		query += TABLE_NAME + ".created_at, ";
		query += TABLE_NAME + ".interest_account_id ";
		query += "FROM " + this.table + " ";
		query += "INNER JOIN " + MemberInterestPaymentAccountDaoRepository.TABLE_NAME + " ";
		query += "ON " + this.table  + ".interest_account_id=" + MemberInterestPaymentAccountDaoRepository.TABLE_NAME + ".id ";
		query += "INNER JOIN " + MemberAccountDaoRepository.TABLE_NAME + " ";
		query += "ON " + MemberInterestPaymentAccountDaoRepository.TABLE_NAME  + ".member_account_id=" + MemberAccountDaoRepository.TABLE_NAME + ".id ";
		query += "INNER JOIN " + MemberDaoRepository.TABLE_NAME + " ";
		query += "ON " + MemberAccountDaoRepository.TABLE_NAME  + ".member_id=" + MemberDaoRepository.TABLE_NAME + ".id ";
		query +=  "WHERE ";
		query += MemberDaoRepository.TABLE_NAME + ".id=? ";
		query += "AND " + TABLE_NAME + ".filled=0 LIMIT 1";
		
		System.out.println("Member investment profit Withdraw By member id Query ALL Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);	
			stm.setInt(1, memberId);
			//ArrayList< MemberWithdrawRequestEntity> requests = new ArrayList<MemberWithdrawRequestEntity>();
			
			res = stm.executeQuery();			
			System.out.println( res.getFetchSize() );
			
			while(res != null && res.next()){
				//UYpdate the object id here
				int id = res.getInt(1);
				System.out.println("Select  id ---> " + id);
				
				 MemberInvestmentProfitWithdrawalRequestEntity request = new   MemberInvestmentProfitWithdrawalRequestEntity();
				
				
				request.setAmount( res.getInt(TABLE_COLUMN_AMOUNT) );
				request.setAmountAfter(res.getInt(TABLE_COLUMN_AMOUNT_AFTER) );
				request.setAmountBefore(res.getInt(TABLE_COLUMN_AMOUNT_BEFORE));
				request.setCreatedAt(res.getString(TABLE_COLUMN_CREATED_AT));
				request.setFilled(res.getBoolean(TABLE_COLUMN_FILLED));
				request.setId(res.getLong("id"));
				request.setMetadata(TABLE_COLUMN_METADATA);
				request.setMemberInterestAccountId(res.getLong(TABLE_COLUMN_INTEREST_ACCOUNT_ID));
				request.setPayment(res.getString(TABLE_COLUMN_PAYMENT));
				
				
				//requests.add(request);
				return request;
				
			}
			return null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		    if (res != null) {
		        try {
		            res.close();
		        } catch (SQLException ex) {
		            // ignore
		        }
		    }

		    if (stm != null) {
		        try {
		            stm.close();
		        } catch (SQLException ex) {
		            // ignore
		        }
		    }
		}
		return null;
	}
	
	public boolean updateFilledStatus(MemberWithdrawRequestEntity obj) {
		String query = "UPDATE " + this.table + " ";
		query += "SET filled=1 WHERE id=?";
		
		System.out.println("Member " + obj.getId() + " Account status  update Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setLong(1, obj.getId());
			
			stm.executeUpdate();
			
			res =  stm.getGeneratedKeys();
			System.out.println( res.getFetchSize() );
			
			if(res != null){
				//UYpdate the object id here
				//int id = res.getInt(1);
				//System.out.println("Inserted id ---> " + id);
				
				//obj.setId(id);
				
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {

		    if (res != null) {
		        try {
		            res.close();
		        } catch (SQLException ex) {
		            // ignore
		        }
		    }

		    if (stm != null) {
		        try {
		            stm.close();
		        } catch (SQLException ex) {
		            // ignore
		        }
		    }
		}
		return false;
		
	}

}
