package com.xrade.orm.dao.mysqlRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.xrade.entity.AnalysisEntity;
import com.xrade.entity.MarketAnalysisEntity;
import com.xrade.models.analysis.AnalysisData;
import com.xrade.models.market.MarketTimeframe;
import com.xrade.models.order.OrderType;
import com.xrade.orm.dao.AbstractDaoRepository;

public class MarketAnalysisDaoRepository extends AbstractDaoRepository<MarketAnalysisEntity>  {
	
	public static final String TABLE_COLUMN_MARKET = "market";
	public static final String TABLE_COLUMN_TOTAL_LOT = "total_lot";
	public static final String TABLE_COLUMN_AVAILABLE_LOT = "available_lot";
	public static final String TABLE_COLUMN_PRICE = "price";
	public static final String TABLE_COLUMN_MAX_PROFIT = "max_profit";
	public static final String TABLE_COLUMN_MAX_LOSS = "max_loss";
	
	public static final String TABLE_COLUMN_ANALYSIS_DATA_ID = "analysis_data_id";
	public static final String TABLE_COLUMN_PUBLISHED = "published";
	
	public MarketAnalysisDaoRepository(Connection connect) {
		super(connect);
		this.setTable("market_analysis");
	}

	@Override
	public boolean create(MarketAnalysisEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query += "("
				+ "market, total_lot, price, max_profit, max_loss, analysis_data_id, available_lot )"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?)";
		
		System.out.println("Market Analysis ADD Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, obj.getMarket());
			stm.setDouble(2, obj.getTotalLot());
			stm.setInt(3, obj.getPrice());
			stm.setInt(4, obj.getMaxProfit());
			stm.setInt(5, obj.getMaxLoss());
			stm.setLong(6, obj.getAnalysisDataId());
			stm.setDouble(7, obj.getTotalLot());
			
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
	public boolean update(MarketAnalysisEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MarketAnalysisEntity[] findAll(int limit, int offset) {
		
		ArrayList<AnalysisEntity> analysis = new ArrayList<AnalysisEntity>();
		
		String query = "SELECT";
		query += "market_analysis.id AS m_id, ";
		query += "market_analysis.identifier as m_identifier, ";
		query += "market_analysis.market AS  m_market, ";
		query += "market_analysis.published AS  m_published, ";
		query += "market_analysis.total_lot AS m_total_lot, ";
		query += "market_analysis.price AS m_price, ";
		query += "market_analysis.max_profit AS m_max_profit, ";
		query += "market_analysis.max_loss AS m_max_loss, ";
		query += "market_analysis.analysis_data_id as a_id, ";
		
		query += "analysis_data.id AS a_id, ";
		query += "analysis_data.identifier AS a_identifier, ";
		query += "analysis_data.position AS a_position, ";
		query += "analysis_data.stop_loss AS a_stop_loss, ";
		query += "analysis_data.take_profit AS a_take_profit, ";
		query += "analysis_data.start_time AS a_start_time, ";
		query += "analysis_data.end_time AS a_end_time, ";
		query += "analysis_data.market AS a_market, ";
		query += "analysis_data.timeframe AS a_timeframe, ";
		query += "analysis_data.order_type AS a_order_type, ";
		query += "analysis_data.capital AS a_captal, ";
		query += "analysis_data.profit AS a_profit, ";
		query += "analysis_data.loss AS a_loss ";
		
		query += "FROM market_analysis "; 
		query += "INNER JOIN analysis_data ";
		query += "ON market_analysis.analysis_data_id=analysis_data.id;";
		System.out.println("Market Analysis ADD Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			res = stm.executeQuery();
			
			System.out.println( res.getFetchSize() );
			
			if(res != null){
				while(res.next()){
					AnalysisEntity an = new AnalysisEntity();
					
					an.setId( res.getLong("m_id") );
					an.setIdentifier("identifier");
					an.setMarket( res.getString("m_" + TABLE_COLUMN_MARKET) );
					an.setMaxProfit( res.getInt("m_" + TABLE_COLUMN_MAX_PROFIT) );
					an.setMaxLoss( res.getInt("m_" + TABLE_COLUMN_MAX_LOSS) );
					an.setTotalLot( res.getInt( "m_" +  TABLE_COLUMN_TOTAL_LOT) );
					an.setPrice( res.getInt("m_" + TABLE_COLUMN_PRICE) );
					an.setAnalysisDataId( res.getLong("m_" + TABLE_COLUMN_ANALYSIS_DATA_ID) );
					an.setPublished( Boolean.parseBoolean(res.getString( "m_" + TABLE_COLUMN_PUBLISHED) ) );
					
					AnalysisData data = new AnalysisData();
					data.setOrder( OrderType.valueOf( res.getString( "a_" + AnalysisDataDaoRepository.TABLE_COLUMN_ORDER_TYPE ) ) );
					data.setPosition( res.getDouble( "a_" +  AnalysisDataDaoRepository.TABLE_COLUMN_POSITION ) );
					data.setStopLoss( res.getDouble( "a_" +  AnalysisDataDaoRepository.TABLE_COLUMN_STOP_LOSS) );
					data.setTakeProfit( res.getDouble( "a_" + AnalysisDataDaoRepository.TABLE_COLUMN_TAKE_PROFIT ) );
					data.setStartTime( res.getString( "a_" + AnalysisDataDaoRepository.TABLE_COLUMN_START_TIME ) );;
					data.setEndTime( res.getString( "a_"  + AnalysisDataDaoRepository.TABLE_COLUMN_END_TIME) );;
					data.setMarket( res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_MARKET) );;
					data.setTimeframe( MarketTimeframe.valueOf(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_TIMEFRAME)) );
					data.setCapital( res.getInt("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_CAPITAL) );;
					data.setProfit( res.getInt("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_LOSS) );;
					data.setLoss( res.getInt("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_LOSS) );
					
					an.setAnalysisData(data);
					
					analysis.add(an);
				}
				
			}
			
			AnalysisEntity[] a = new AnalysisEntity[analysis.size()];
			return analysis.toArray(a);
			
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

	@Override
	public MarketAnalysisEntity find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	

}
