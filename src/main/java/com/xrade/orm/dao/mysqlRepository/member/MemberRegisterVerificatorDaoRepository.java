package com.xrade.orm.dao.mysqlRepository.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberRegisterVerificatorEntity;
import com.xrade.orm.dao.AbstractDaoRepository;

public class MemberRegisterVerificatorDaoRepository extends AbstractDaoRepository<MemberRegisterVerificatorEntity> {
	
	public static final String TABLE_COLUMN_IDENTIFIER = "identifier";
	public static final String TABLE_COLUMN_CODE = "code";
	public static final String TABLE_COLUMN_USED = "used";
	public  static final String TABLE_COLUMN_CREATED_AT = "created_at";
	
	public static final String TABLE_COLUMN_MEMBER_ID = "member_id";
	
	public static final String TABLE_NAME = "member_verificator";
	
	public MemberRegisterVerificatorDaoRepository(Connection connect) {
		super(connect);
		this.setTable(TABLE_NAME);
	}

	@Override
	public boolean create(MemberRegisterVerificatorEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query += "("
				+ "identifier, code, member_id )"
				+ " VALUES(?, ?, ?)";
		
		System.out.println("Member Account ADD-CREATE Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, obj.getIdentifier());
			stm.setString(2, obj.getCode());
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
	public boolean update(MemberRegisterVerificatorEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MemberRegisterVerificatorEntity[] findAll(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberRegisterVerificatorEntity find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * Update the member verification code used
	 * state
	 * 
	 * @param obj
	 * @return
	 */
	public boolean updateUsedtate(MemberRegisterVerificatorEntity obj) {
		String query = "UPDATE " + this.table + " ";
		query += "SET used=1 WHERE id=?";
		
		System.out.println("Member " + obj.getId() + " verification used state update Query : " + query);
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
	
	
	
	/**
	 * Find a verificator record by identifier and code
	 * @param obj
	 * @return
	 */
	public boolean findByIdentifierByCode(MemberRegisterVerificatorEntity obj) {
		String query = "SELECT * FROM " + this.table + " ";
		query += "WHERE identifier=? AND code=? AND used=0 LIMIT 1";
		
		System.out.println("Member verification used state selectQuery : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, obj.getIdentifier());
			stm.setString(2, obj.getCode());
			
			
			
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			while(res != null && res.next()){
				//Update the object id here
				int id = res.getInt(1);
				System.out.println("Selected id ---> " + id);
				
				 obj.setCode(res.getString(TABLE_COLUMN_CODE));
				 obj.setId(res.getLong("id"));
				 obj.setIdentifier(TABLE_COLUMN_IDENTIFIER);
				 obj.setUsed(res.getBoolean(TABLE_COLUMN_USED));
				 obj.setMemberId(res.getLong(TABLE_COLUMN_MEMBER_ID));
				
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

}
