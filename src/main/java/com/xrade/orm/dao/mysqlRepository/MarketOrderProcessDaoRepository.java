package com.xrade.orm.dao.mysqlRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import com.xrade.entity.MarketOrderProcessEntity;
import com.xrade.orm.dao.AbstractDaoRepository;

public class MarketOrderProcessDaoRepository extends AbstractDaoRepository<MarketOrderProcessEntity> {
	

	public static final String TABLE_COLUMN_LAST_POSITION = "last_position";
	public static final String TABLE_COLUMN_STATUS = "status";
	public static final String TABLE_COLUMN_CLOSE_DATE = "close_date";
	public static final String TABLE_COLUMN_BENEFIT = "benefit";

	public static final String TABLE_COLUMN_MARKET_ORDER_ID = "market_order_id";
	
	public static final String TABLE_NAME = "market_order_process";
	
	public MarketOrderProcessDaoRepository(Connection connect) {
		super(connect);
		this.setTable("market_order_process");
	}

	@Override
	public boolean create(MarketOrderProcessEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query += "("
				+ "last_position, status, close_date, benefit, market_order_id)"
				+ " VALUES(?, 'alive', NULL, ?, ?)";
		
		System.out.println("Market Order ADD Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, obj.getLastPosition());
			stm.setInt(2, obj.getBenefit());
			stm.setLong(3, obj.getMarketOrderId());
			
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
	public boolean update(MarketOrderProcessEntity obj) {
		String query = "UPDATE " + this.table + " ";
		query += "SET last_position=?, status=?, close_date=?, benefit=?";
		query += " WHERE id=?";
		
		System.out.println("Market Order UPDATE Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, obj.getLastPosition());
			stm.setString(2, obj.getStatus());
			stm.setString(3, obj.getCloseDate());
			stm.setInt(4, obj.getBenefit());
			stm.setLong(5, obj.getId());
			
			stm.executeUpdate();
			
			res =  stm.getGeneratedKeys();
			System.out.println( res.getFetchSize() );
			return true;
			
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
	public MarketOrderProcessEntity[] findAll(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MarketOrderProcessEntity find(int id) {
		String query = "SELECT last_position, status, close_date, benefit, market_order_id ";
		query += "FROM " + this.table + " ";
		query += " WHERE id=?";
		
		System.out.println("Market Order PROCESS SELECT Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, id);
			
			res = stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			if(res != null){
				//UYpdate the object id here
				MarketOrderProcessEntity entity = new MarketOrderProcessEntity();
				while(res.next()){
					 entity.setId(id);
					 entity.setLastPosition( res.getDouble(TABLE_COLUMN_LAST_POSITION) );
					 entity.setStatus( res.getString(TABLE_COLUMN_STATUS));
					 entity.setCloseDate( res.getString(TABLE_COLUMN_CLOSE_DATE) );
					 entity.setBenefit(res.getInt(TABLE_COLUMN_BENEFIT) );
					 entity.setMarketOrderId( res.getLong(TABLE_COLUMN_MARKET_ORDER_ID) );					 
				}
				return entity;
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
	 * 
	 * @param obj
	 * @return
	 */
	public MarketOrderProcessEntity close(MarketOrderProcessEntity obj) {
		String query = "UPDATE " + this.table + " ";
		query += "SET last_position=?, status=?, close_date=NOW(), benefit=?";
		query += " WHERE id=?";
		
		System.out.println("Market Order UPDATE-CLOSE Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, obj.getLastPosition());
			stm.setString(2, "closed");
			stm.setInt(3, obj.getBenefit());
			stm.setLong(4, obj.getId());
			
			stm.executeUpdate();
			
			res =  stm.getGeneratedKeys();
			System.out.println( res.getFetchSize() );
			if(res != null && res.next()){
				//UYpdate the object id here
				int id = res.getInt(1);
				System.out.println("Inserted id ---> " + id);
				
				obj.setId(id);
			}
			return  this.find((int) obj.getId());
			
			
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
