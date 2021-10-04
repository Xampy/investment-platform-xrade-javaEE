package com.xrade.orm.dao.mysqlRepository.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.xrade.entity.MemberSponsorshipProfitMergeRequestEntity;
import com.xrade.orm.dao.AbstractDaoRepository;

public class MemberSponsorshipProfitMergeRequestDaoRepository extends AbstractDaoRepository<MemberSponsorshipProfitMergeRequestEntity> {
	
	public  static final String TABLE_COLUMN_AMOUNT = "amount";
	public  static final String TABLE_COLUMN_AMOUNT_BEFORE = "amount_before";
	public  static final String TABLE_COLUMN_AMOUNT_AFTER = "amount_after";
	public  static final String TABLE_COLUMN_STATUS = "status";
	public  static final String TABLE_COLUMN_CREATED_AT = "created_at";
	
	public  static final String TABLE_COLUMN_SPONSORSHIP_ACCOUNT_ID = "sponsorship_account_id";
	
	public  static final String TABLE_NAME = "member_sponsorship_payment_merge_request";
	
	public MemberSponsorshipProfitMergeRequestDaoRepository(Connection connect) {
		super(connect);
		this.setTable(TABLE_NAME);
	}

	@Override
	public boolean create(MemberSponsorshipProfitMergeRequestEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query +=  "(amount, amount_before, amount_after, sponsorship_account_id) ";
		query += "VALUES(?, ?, ?, ?)";
		
		System.out.println("Member interest profit merge Query ADD Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, obj.getAmount());
			stm.setDouble(2, obj.getAmountBefore());
			stm.setDouble(3, obj.getAmountAfter());
			stm.setLong(4, obj.getMemberSponsorshipAccountId());
			
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
	public boolean update(MemberSponsorshipProfitMergeRequestEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MemberSponsorshipProfitMergeRequestEntity[] findAll(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberSponsorshipProfitMergeRequestEntity find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
