package com.xrade.orm.dao.mysqlRepository.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.xrade.entity.MemberDepositRequestEntity;
import com.xrade.entity.MemberInvestmentProfitMergeRequestEntity;
import com.xrade.orm.dao.AbstractDaoRepository;

public class MemberInvestmentProfitMergeRequestDaoRepository extends AbstractDaoRepository<MemberInvestmentProfitMergeRequestEntity> {
	
	public  static final String TABLE_COLUMN_AMOUNT = "amount";
	public  static final String TABLE_COLUMN_AMOUNT_BEFORE = "amount_before";
	public  static final String TABLE_COLUMN_AMOUNT_AFTER = "amount_after";
	public  static final String TABLE_COLUMN_STATUS = "status";
	public  static final String TABLE_COLUMN_CREATED_AT = "created_at";
	
	public  static final String TABLE_COLUMN_INTEREST_ACCOUNT_ID = "interest_account_id";
	
	public  static final String TABLE_NAME = "member_account_interest_payment_account_merge_request";
	
	public MemberInvestmentProfitMergeRequestDaoRepository(Connection connect) {
		super(connect);
		this.setTable(TABLE_NAME);
	}

	@Override
	public boolean create(MemberInvestmentProfitMergeRequestEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query +=  "(amount, amount_before, amount_after, interest_account_id) ";
		query += "VALUES(?, ?, ?, ?)";
		
		System.out.println("Member interest profit merge Query ADD Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, obj.getAmount());
			stm.setDouble(2, obj.getAmountBefore());
			stm.setDouble(3, obj.getAmountAfter());
			stm.setLong(4, obj.getMemberInterestAccountId());
			
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
	public boolean update(MemberInvestmentProfitMergeRequestEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MemberInvestmentProfitMergeRequestEntity[] findAll(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberInvestmentProfitMergeRequestEntity find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	//Added funtions
	
	/**
	 * Get the time elapsed between now and the latestest profit merge request
	 * It returns days
	 * 
	 * @param latestMerge
	 * @return
	 */
	public int getEleapsedDayFromTheLatestMerge( MemberInvestmentProfitMergeRequestEntity latestMerge ){
		String query = "SELECT DATEDIFF(NOW(), DATE(created_at)) as diff FROM ";
		query += this.table + " ";
		query += "WHERE id=?";
		
		System.out.println("Member Lastest profit merge day elapsed Query SELECT ALL Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setLong(1, latestMerge.getId());
			System.out.println("Latest id " + latestMerge.getId());
			
			int days = -1;
			
			res = stm.executeQuery();			
			System.out.println( res.getFetchSize() );
			
			while(res != null && res.next()){
				//UYpdate the object id here
				days = res.getInt("diff");
				break;
				
				
			}
			
			return days;
			
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
		
		return -1;
	}
	
	
	/**
	 * Get the latest profit merge made by the user
	 * @param interestAccountid
	 * @return
	 */
	public MemberInvestmentProfitMergeRequestEntity getLatestProfitMergeByInterestAccount(
			long interestAccountId){
		
		String query = "SELECT * FROM ";
		query += this.table + " ";
		query += "WHERE id=( "; 
		query += "SELECT MAX(id) FROM " + this.table;
		query += " WHERE interest_account_id=?)";
		
		System.out.println("Member Lastest profit merge Query SELECT ALL Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setLong(1, interestAccountId);;
			
			
			res = stm.executeQuery();			
			System.out.println( res.getFetchSize() );
			
			while(res != null && res.next()){
				//UYpdate the object id here
				int id = res.getInt(1);
				System.out.println("Select  id ---> " + id);
				
				MemberInvestmentProfitMergeRequestEntity request = new MemberInvestmentProfitMergeRequestEntity();
				
				request.setId(res.getLong("id"));
				request.setAmount( res.getDouble(TABLE_COLUMN_AMOUNT) );
				request.setAmountBefore( res.getDouble(TABLE_COLUMN_AMOUNT_BEFORE) );
				request.setAmountAfter( res.getDouble(TABLE_COLUMN_AMOUNT_AFTER) );
				request.setStatus( res.getString(TABLE_COLUMN_STATUS) );
				request.setCreatedAt( res.getString(TABLE_COLUMN_CREATED_AT) );
				request.setMemberInterestAccountId( res.getLong(TABLE_COLUMN_INTEREST_ACCOUNT_ID) );
				
				return request;
				
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
		return null;
	}

}
