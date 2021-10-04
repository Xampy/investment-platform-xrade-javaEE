package com.xrade.orm.dao.mysqlRepository.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.xrade.entity.MemberAccountSponsorshipPaymentEntity;
import com.xrade.orm.dao.AbstractDaoRepository;

public class MemberSponsorhipPaymentDaoRepository  extends AbstractDaoRepository<MemberAccountSponsorshipPaymentEntity>{
	
	
	public  static final String TABLE_COLUMN_AMOUNT_BEFORE = "amount_before";
	public  static final String TABLE_COLUMN_AMOUNT_AFTER = "amount_after";
	public  static final String TABLE_COLUMN_DATE = "date_init";
	
	public  static final String TABLE_COLUMN_MEMBER_ACCOUNT_ID = "member_account_id";
	
	public  static final String TABLE_NAME = "member_account_sponsorship_payment";

	
	public MemberSponsorhipPaymentDaoRepository(Connection connect) {
		super(connect);
		this.setTable(TABLE_NAME);
	}

	@Override
	public boolean create(MemberAccountSponsorshipPaymentEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query +=  "(amount_before, amount_after, member_account_id) ";
		query += "VALUES(?, ?, ?)";
		
		System.out.println("Member sponsorship  payment create Query ADD Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, obj.getAmountBefore());
			stm.setDouble(2, obj.getAmountAfter());
			stm.setLong(3, obj.getMemberAccountId());
			
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
	public boolean update(MemberAccountSponsorshipPaymentEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MemberAccountSponsorshipPaymentEntity[] findAll(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberAccountSponsorshipPaymentEntity find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
