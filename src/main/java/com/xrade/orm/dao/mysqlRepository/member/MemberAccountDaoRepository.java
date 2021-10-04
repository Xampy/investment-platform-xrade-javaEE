package com.xrade.orm.dao.mysqlRepository.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.xrade.entity.MemberAccountEntity;
import com.xrade.orm.dao.AbstractDaoRepository;

public class MemberAccountDaoRepository extends AbstractDaoRepository<MemberAccountEntity> {
	
	public static final String TABLE_COLUMN_AMOUNT = "amount";
	public static final String TABLE_COLUMN_TRANSACTION_CODE = "transaction_code";
	public static final String TABLE_COLUMN_MEMBER_ID = "member_id";
	
	
	public static final String TABLE_NAME = "member_account";

	public MemberAccountDaoRepository(Connection connect) {
		super(connect);
		this.setTable(TABLE_NAME);
	}

	@Override
	public boolean create(MemberAccountEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query += "("
				+ "amount, transaction_code, member_id )"
				+ " VALUES(?, ?, ?)";
		
		System.out.println("Member Account ADD-CREATE Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, obj.getAmount());
			stm.setString(2, obj.getTransacrionCode());
			stm.setLong(3, obj.getMemberId());
			
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
	public boolean update(MemberAccountEntity obj) {
		String query = "UPDATE " + this.table + " ";
		query += "SET amount=?, SET transaction_code=?, SET member_id=? WHERE id=?";
		
		System.out.println("Member Account update Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, obj.getAmount());
			stm.setString(2, obj.getTransacrionCode());
			stm.setLong(3, obj.getMemberId());
			stm.setLong(4, obj.getId());
			
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
	
	/**
	 * Update the account amount
	 * 
	 * @param obj
	 * @return
	 */
	public boolean updateAmount(MemberAccountEntity obj) {
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

	@Override
	public MemberAccountEntity[] findAll(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberAccountEntity find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
