package com.xrade.orm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDaoRepository<T> {
	
	protected Connection connect;
	protected String table;
	
	public AbstractDaoRepository(Connection connect){
		this.connect = connect;
	}
	
	public abstract boolean create(T obj);
	public abstract boolean update(T obj);
	public abstract T[] findAll(int limit, int offset);
	public abstract T find(int id);
	
	public int countRows(){
		String query = "SELECT COUNT(*) as total FROM " + this.table + ";";
		
		PreparedStatement stm = null;
		ResultSet res = null;	
		try {

			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			
			res = stm.executeQuery();
			System.out.println(res.getFetchSize());

			if (res != null && res.next()) {
				
				return res.getInt("total");
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

		return 0;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}
}
