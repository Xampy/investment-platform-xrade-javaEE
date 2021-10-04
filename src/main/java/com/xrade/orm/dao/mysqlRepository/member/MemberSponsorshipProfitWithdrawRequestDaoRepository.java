package com.xrade.orm.dao.mysqlRepository.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.xrade.entity.MemberInvestmentProfitWithdrawalRequestEntity;
import com.xrade.entity.MemberSponsorshipProfitWithdrawalRequestEntity;
import com.xrade.entity.MemberWithdrawRequestEntity;
import com.xrade.orm.dao.AbstractDaoRepository;

public class MemberSponsorshipProfitWithdrawRequestDaoRepository extends AbstractDaoRepository<MemberSponsorshipProfitWithdrawalRequestEntity> {
	
	
	public  static final String TABLE_COLUMN_PAYMENT =  "payment";
	public  static final String TABLE_COLUMN_AMOUNT = "amount";
	public  static final String TABLE_COLUMN_AMOUNT_BEFORE = "amount_before";
	public  static final String TABLE_COLUMN_AMOUNT_AFTER = "amount_after";
	public  static final String TABLE_COLUMN_METADATA = "metadata";
	public  static final String TABLE_COLUMN_FILLED = "filled";
	public  static final String TABLE_COLUMN_CREATED_AT = "created_at";
	
	public  static final String TABLE_COLUMN_SPONSOSHIP_ACCOUNT_ID = "sponsorship_account_id";
	
	public  static final String TABLE_NAME = "member_sponsorship_payment_withdraw_request";
	
	
	public MemberSponsorshipProfitWithdrawRequestDaoRepository(Connection connect) {
		super(connect);
		this.setTable(TABLE_NAME);
	}

	@Override
	public boolean create(MemberSponsorshipProfitWithdrawalRequestEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query +=  "(payment, amount, amount_before, amount_after, metadata, filled, created_at, sponsorship_account_id) ";
		query += "VALUES(?, ?, ?, ?, ?, ?, now(), ?)";
		
		System.out.println("Member sponsorship withdrawal Query ADD Query : " + query);
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
			stm.setLong(7, obj.getMemberSponsorshipAccountId());
			
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
	public boolean update(MemberSponsorshipProfitWithdrawalRequestEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MemberSponsorshipProfitWithdrawalRequestEntity[] findAll(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberSponsorshipProfitWithdrawalRequestEntity find(int id) {
		// TODO Auto-generated method stub
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
