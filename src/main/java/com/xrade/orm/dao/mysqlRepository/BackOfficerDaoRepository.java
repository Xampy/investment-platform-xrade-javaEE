package com.xrade.orm.dao.mysqlRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.xrade.entity.BackOfficerEntity;
import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.orm.dao.AbstractDaoRepository;

public class BackOfficerDaoRepository extends AbstractDaoRepository<BackOfficerEntity>{
	
	
	public static final String TABLE_COLUMN_EMAIL = "email";
	public static final String TABLE_COLUMN_PASSWORD = "password";
	public static final String TABLE_COLUMN_LEVEL = "level";
	public static final String TABLE_COLUMN_TOKEN = "token";
	
	public BackOfficerDaoRepository(Connection connect) {
		super(connect);
		this.setTable("back_officer");
	}
	
	@Override
	public boolean create(BackOfficerEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean update(BackOfficerEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public BackOfficerEntity[] findAll(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public BackOfficerEntity find(int id) {
		String query = "SELECT * ";
		query += "FROM " + this.table + " ";
		query += "WHERE " ;
		query += "id=?";
		
		System.out.println("Back Office User SELECT Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setInt(1, id);
			
			
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			if(res != null && res.next()){
				//Update the object id here
				int boId = res.getInt(1);
				System.out.println("Selected id ---> " + boId);
				
				BackOfficerEntity obj = new BackOfficerEntity();
				obj.setId( res.getLong("id") );
				obj.setEmail(res.getString(TABLE_COLUMN_EMAIL));
				obj.setPassword(res.getString(TABLE_COLUMN_PASSWORD));
				obj.setLevel(res.getString(TABLE_COLUMN_LEVEL));
				obj.setToken(res.getString(TABLE_COLUMN_TOKEN));
				
				
				return obj;
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
	
	/**
	 * Fin a back officer by it email and password
	 * 
	 * @param obj
	 * @return
	 */
	public boolean findByEmailPassword(BackOfficerEntity obj) {
		String query = "SELECT * ";
		query += "FROM " + this.table + " ";
		query += "WHERE " ;
		query += "email=? AND password =?";
		
		System.out.println("Back Office User SELECT Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, obj.getEmail());
			stm.setString(2, obj.getPassword());
			
			
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			if(res != null && res.next()){
				//Update the object id here
				int id = res.getInt(1);
				System.out.println("Selected id ---> " + id);
				
				obj.setId( res.getLong("id") );
				obj.setEmail(res.getString(TABLE_COLUMN_EMAIL));
				obj.setPassword(res.getString(TABLE_COLUMN_PASSWORD));
				obj.setLevel(res.getString(TABLE_COLUMN_LEVEL));
				obj.setToken(res.getString(TABLE_COLUMN_TOKEN));
				
				
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
