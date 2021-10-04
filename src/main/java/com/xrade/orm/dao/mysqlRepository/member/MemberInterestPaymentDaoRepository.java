package com.xrade.orm.dao.mysqlRepository.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberAccountInterestPaymentEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.orm.dao.AbstractDaoRepository;

public class MemberInterestPaymentDaoRepository  extends AbstractDaoRepository<MemberAccountInterestPaymentEntity>{
	
	public  static final String TABLE_COLUMN_RATE =  "rate";
	public  static final String TABLE_COLUMN_AMOUNT_BEFORE = "amount_before";
	public  static final String TABLE_COLUMN_AMOUNT_AFTER = "amount_after";
	public  static final String TABLE_COLUMN_DATE = "date_init";
	
	public  static final String TABLE_COLUMN_MEMBER_ACCOUNT_ID = "member_account_id";
	
	public  static final String TABLE_NAME = "member_account_interest_payment";

	public MemberInterestPaymentDaoRepository(Connection connect) {
		super(connect);
		this.setTable(TABLE_NAME);
	}

	@Override
	public boolean create(MemberAccountInterestPaymentEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query +=  "(rate, amount_before, amount_after, member_account_id) ";
		query += "VALUES(?, ?, ?, ?)";
		
		System.out.println("Member Interest payment create Query ADD Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, obj.getRate());
			stm.setDouble(2, obj.getAmountBefore());
			stm.setDouble(3, obj.getAmountAfter());
			stm.setLong(4, obj.getMemberAccountId());
			
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
	public boolean update(MemberAccountInterestPaymentEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MemberAccountInterestPaymentEntity[] findAll(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberAccountInterestPaymentEntity find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Get the latest inerest payment data by member
	 * @param member
	 * @return
	 */
	public MemberAccountInterestPaymentEntity[] findAllByMember(MemberEntity member){
		String query = "SELECT * FROM " + this.table + " ";
		query += "WHERE member_account_id=? LIMIT 4"; //Four rows of results is sufficient
		
		System.out.println("Member Interest payment select Query ADD Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, ( (MemberAccountEntity)member.getAccount()).getId());
			
			
			
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			ArrayList<MemberAccountInterestPaymentEntity> payments = new ArrayList<MemberAccountInterestPaymentEntity>();
			
			while(res != null && res.next()){
				//UYpdate the object id here
				int id = res.getInt(1);
				System.out.println("Inserted id ---> " + id);
				
				MemberAccountInterestPaymentEntity payment = new MemberAccountInterestPaymentEntity();
				payment.setAmountAfter(res.getInt(TABLE_COLUMN_AMOUNT_AFTER));
				payment.setAmountBefore(res.getInt(TABLE_COLUMN_AMOUNT_BEFORE));
				payment.setCreatedAt(res.getString(TABLE_COLUMN_DATE));
				payment.setMemberAccountId(res.getLong(TABLE_COLUMN_MEMBER_ACCOUNT_ID));
				payment.setId(res.getLong("id"));
				
				payments.add(payment);
			}
			
			if(payments.size() > 0){
				return payments.toArray(new MemberAccountInterestPaymentEntity[payments.size()]);
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

}
