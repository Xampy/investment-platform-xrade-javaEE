package com.xrade.orm.dao.mysqlRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.xrade.entity.AnalysisDataEntity;
import com.xrade.models.market.MarketTimeframe;
import com.xrade.models.order.OrderType;
import com.xrade.orm.dao.AbstractDaoRepository;

public class AnalysisDataDaoRepository extends AbstractDaoRepository<AnalysisDataEntity> {
	public static final String TABLE_COLUMN_ID = "id";
	public static final String TABLE_COLUMN_POSITION = "position";
	public static final String TABLE_COLUMN_STOP_LOSS = "stop_loss";
	public static final String TABLE_COLUMN_TAKE_PROFIT = "take_profit";
	public static final String TABLE_COLUMN_START_TIME = "start_time";
	public static final String TABLE_COLUMN_END_TIME = "end_time";	
	public static final String TABLE_COLUMN_MARKET = "market";
	public static final String TABLE_COLUMN_TIMEFRAME = "timeframe";
	public static final String TABLE_COLUMN_ORDER_TYPE = "order_type";
	public static final String TABLE_COLUMN_CAPITAL = "capital";
	public static final String TABLE_COLUMN_PROFIT = "profit";
	public static final String TABLE_COLUMN_LOSS = "loss";
	public static final String TABLE_COLUMN_CREATED_AT = "created_at";

	public AnalysisDataDaoRepository(Connection connect) {
		super(connect);
		this.setTable("analysis_data");
	}

	@Override
	public boolean create(AnalysisDataEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query += "("
				+ "position, stop_loss, take_profit, start_time, end_time, "
				+ "market, timeframe, order_type, capital, profit, loss )"
				+ " VALUES(?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?)";
		
		System.out.println("Analysis ADD Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;
		
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, obj.getPosition());
			stm.setDouble(2, obj.getStopLoss());
			stm.setDouble(3, obj.getTakeProfit());
			stm.setString(4, obj.getStartTime());
			stm.setString(5, obj.getEndTime());
			stm.setString(6, obj.getMarket());
			stm.setString(7, obj.getTimeframe().toString());
			stm.setString(8, obj.getOrder().toString());
			stm.setDouble(9, obj.getCapital());
			stm.setDouble(10, obj.getProfit());
			stm.setDouble(11, obj.getLoss());
			
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
		
			return false;
			
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
		            //ignore
		        }
		    }
		}
		
		return false;
	}

	@Override
	public boolean update(AnalysisDataEntity obj) {
		return false;
	}

	@Override
	public AnalysisDataEntity[] findAll(int limit, int offset) {
		String query = "SELECT * FROM " + this.table + " ";
		query += "LIMIT 10;";
		
		System.out.println("Analysis FIND BY ID Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;
		ArrayList<AnalysisDataEntity> analysis = new ArrayList<AnalysisDataEntity>();
		
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			res = stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			while(res != null && res.next()){
				//UYpdate the object id here
				
				AnalysisDataEntity data = new AnalysisDataEntity();
				
				data.setId(res.getLong("id"));
				data.setPosition(res.getDouble(TABLE_COLUMN_POSITION));
				data.setStopLoss(res.getDouble(TABLE_COLUMN_STOP_LOSS));
				data.setTakeProfit(res.getDouble(TABLE_COLUMN_TAKE_PROFIT));
				data.setStartTime(res.getString(TABLE_COLUMN_START_TIME));
				data.setEndTime(res.getString(TABLE_COLUMN_END_TIME));
				data.setMarket(res.getString(TABLE_COLUMN_MARKET));
				data.setTimeframe(MarketTimeframe.valueOf(res.getString(TABLE_COLUMN_TIMEFRAME)));
				data.setOrder(OrderType.valueOf(res.getString(TABLE_COLUMN_ORDER_TYPE)));
				data.setCapital(res.getDouble(TABLE_COLUMN_CAPITAL));
				data.setProfit(res.getDouble(TABLE_COLUMN_PROFIT));
				data.setLoss(res.getDouble(TABLE_COLUMN_LOSS));
				data.setCreatedAt(res.getString(TABLE_COLUMN_CREATED_AT));
				
				analysis.add(data);
			}
		
			return (AnalysisDataEntity[]) analysis.toArray();
			
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
		            //ignore
		        }
		    }
		}
		
		return null;
	}

	public AnalysisDataEntity find(long id) {
		String query = "SELECT * FROM " + this.table + " ";
		query += "WHERE id=?;";
		
		System.out.println("Analysis FIND BY ID Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;
		AnalysisDataEntity data = null;
		
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, id);
			
			res = stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			if(res != null && res.next()){
				//UYpdate the object id here
				int a_id = res.getInt(1);
				System.out.println("Selected id ---> " + id);
				
				data = new AnalysisDataEntity();
				
				data.setId(id);
				data.setPosition(res.getDouble(TABLE_COLUMN_POSITION));
				data.setStopLoss(res.getDouble(TABLE_COLUMN_STOP_LOSS));
				data.setTakeProfit(res.getDouble(TABLE_COLUMN_TAKE_PROFIT));
				data.setStartTime(res.getString(TABLE_COLUMN_START_TIME));
				data.setEndTime(res.getString(TABLE_COLUMN_END_TIME));
				data.setMarket(res.getString(TABLE_COLUMN_MARKET));
				data.setTimeframe(MarketTimeframe.valueOf(res.getString(TABLE_COLUMN_TIMEFRAME)));
				data.setOrder(OrderType.valueOf(res.getString(TABLE_COLUMN_ORDER_TYPE)));
				data.setCapital(res.getDouble(TABLE_COLUMN_CAPITAL));
				data.setProfit(res.getDouble(TABLE_COLUMN_PROFIT));
				data.setLoss(res.getDouble(TABLE_COLUMN_LOSS));
				
			}
		
			return data;
			
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
		            //ignore
		        }
		    }
		}
		
		return data;
	}

	@Override
	public AnalysisDataEntity find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
