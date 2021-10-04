package com.xrade.orm.dao.mysqlRepository.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.xrade.entity.MemberSponsorshipPaymentAccountEntity;
import com.xrade.orm.dao.AbstractDaoRepository;

public class MemberSponsorshipPaymentAccountDaoRepository extends AbstractDaoRepository<MemberSponsorshipPaymentAccountEntity>{
	
	
	public  static final String TABLE_COLUMN_AMOUNT = "amount";
	public  static final String TABLE_COLUMN_CREATED_AT = "created_at";
	
	public  static final String TABLE_COLUMN_MEMBER_ACCOUNT_ID = "member_account_id";
	
	public  static final String TABLE_NAME = "member_sponsorship_payment_account";
	
	public MemberSponsorshipPaymentAccountDaoRepository(Connection connect) {
		super(connect);
		this.setTable(TABLE_NAME);
	}

	@Override
	public boolean create(MemberSponsorshipPaymentAccountEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query +=  "(amount, member_account_id) ";
		query += "VALUES(?, ?)";
		
		System.out.println("Member Interest payment Account ADD Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, obj.getAmount());
			stm.setLong(2, obj.getMemberAccountId());
			
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
	public boolean update(MemberSponsorshipPaymentAccountEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MemberSponsorshipPaymentAccountEntity[] findAll(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberSponsorshipPaymentAccountEntity find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * Update the account amount
	 * 
	 * @param obj MemberSponsorshipPaymentAccountEntity
	 * @return
	 */
	public boolean updateAmount(MemberSponsorshipPaymentAccountEntity obj) {
		String query = "UPDATE " + this.table + " ";
		query += "SET amount=? WHERE id=?";
		
		System.out.println("Member " + obj.getId() + " Account amount update Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, obj.getAmount());
			stm.setLong(2, obj.getId());
			
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
