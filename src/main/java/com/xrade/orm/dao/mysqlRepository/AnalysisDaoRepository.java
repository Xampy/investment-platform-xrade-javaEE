package com.xrade.orm.dao.mysqlRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.xrade.entity.AnalysisDataEntity;
import com.xrade.entity.AnalysisEntity;
import com.xrade.models.analysis.AnalysisData;
import com.xrade.models.market.MarketTimeframe;
import com.xrade.models.order.OrderType;
import com.xrade.orm.dao.AbstractDaoRepository;

public class AnalysisDaoRepository extends AbstractDaoRepository<AnalysisEntity> {

	public AnalysisDaoRepository(Connection connect) {
		super(connect);
		this.setTable("market_analysis");
	}

	@Override
	public boolean create(AnalysisEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(AnalysisEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Update the analysis available lot
	 * 
	 * @param obj the updated object the new available lot value
	 * @return
	 */
	public boolean updateAvailableLot(AnalysisEntity obj) {
		String query = "UPDATE " + this.table + " ";
		query += "SET available_lot=? WHERE id=?";
		
		System.out.println("Analysis available lot  update Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		try {

			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setInt(1, obj.getAvailableLot());
			stm.setLong(2,  obj.getId());
			
			stm.executeUpdate();
			res = stm.getGeneratedKeys();
			System.out.println(res.getFetchSize());

			if (res != null) {
				
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
	 * 
	 * Set the analysis publish status to true
	 * 
	 * @param obj the AnalysisEntity object updated to true
	 * @return
	 */
	public boolean updatePublished(AnalysisEntity obj) {
		String query = "UPDATE " + this.table + " ";
		query += "SET published=? WHERE id=?";
		
		System.out.println("Analysis published state  update Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		try {

			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setBoolean(1, obj.isPublished());
			stm.setLong(2,  obj.getId());
			
			stm.executeUpdate();
			res = stm.getGeneratedKeys();
			System.out.println(res.getFetchSize());

			if (res != null) {
				
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
	public AnalysisEntity[] findAll(int limit, int offset) {
		ArrayList<AnalysisEntity> analysis = new ArrayList<AnalysisEntity>();

		String query = "SELECT ";
		query += "market_analysis.id AS m_id, ";
		query += "market_analysis.identifier as m_identifier, ";
		query += "market_analysis.market AS  m_market, ";
		query += "market_analysis.published AS  m_published, ";
		query += "market_analysis.total_lot AS m_total_lot, ";
		query += "market_analysis.available_lot AS m_available_lot, ";
		query += "market_analysis.price AS m_price, ";
		query += "market_analysis.max_profit AS m_max_profit, ";
		query += "market_analysis.max_loss AS m_max_loss, ";
		query += "market_analysis.analysis_data_id as m_analysis_data_id, ";

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
		query += "analysis_data.capital AS a_capital, ";
		query += "analysis_data.profit AS a_profit, ";
		query += "analysis_data.loss AS a_loss, ";
		query += "analysis_data.created_at AS a_created_at ";

		query += "FROM market_analysis ";
		query += "INNER JOIN analysis_data ";
		query += "ON market_analysis.analysis_data_id=analysis_data.id;";
		System.out.println("Market Analysis SELECT Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;

		try {

			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			res = stm.executeQuery();

			System.out.println(res.getFetchSize());

			if (res != null) {
				while (res.next()) {
					AnalysisEntity an = new AnalysisEntity();

					an.setId(res.getLong("m_id"));
					an.setIdentifier("identifier");
					an.setMarket(res.getString("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_MARKET));
					an.setMaxProfit(res.getInt("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_MAX_PROFIT));
					an.setMaxLoss(res.getInt("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_MAX_LOSS));
					an.setTotalLot(res.getInt("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_TOTAL_LOT));
					an.setAvailableLot( res.getInt("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_AVAILABLE_LOT) );
					an.setPrice(res.getInt("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_PRICE));
					an.setAnalysisDataId(res.getLong("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_ANALYSIS_DATA_ID));
					an.setPublished(res.getBoolean("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_PUBLISHED));

					AnalysisDataEntity data = new AnalysisDataEntity();
					data.setOrder(
							OrderType.valueOf(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_ORDER_TYPE)));
					data.setPosition(res.getDouble("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_POSITION));
					data.setStopLoss(res.getDouble("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_STOP_LOSS));
					data.setTakeProfit(res.getDouble("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_TAKE_PROFIT));
					data.setStartTime(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_START_TIME));
					data.setEndTime(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_END_TIME));
					data.setMarket(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_MARKET));
					data.setTimeframe(MarketTimeframe.valueOf(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_TIMEFRAME)));
					data.setCapital(res.getInt("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_CAPITAL));
					data.setProfit(res.getInt("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_PROFIT));
					data.setLoss(res.getInt("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_LOSS));
					data.setCreatedAt(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_CREATED_AT));

					an.setAnalysisData(data);
					
					System.out.println("Data " + data.getCapital() + " id " + an.getId() + " " + data.getCreatedAt() );

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

	public AnalysisEntity find(long analysisId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnalysisEntity find(int id) {
		String query = "SELECT ";
		query += "market_analysis.id AS m_id, ";
		query += "market_analysis.identifier as m_identifier, ";
		query += "market_analysis.market AS  m_market, ";
		query += "market_analysis.published AS  m_published, ";
		query += "market_analysis.total_lot AS m_total_lot, ";
		query += "market_analysis.available_lot AS m_available_lot, ";
		query += "market_analysis.price AS m_price, ";
		query += "market_analysis.max_profit AS m_max_profit, ";
		query += "market_analysis.max_loss AS m_max_loss, ";
		query += "market_analysis.analysis_data_id as m_analysis_data_id, ";

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
		query += "analysis_data.capital AS a_capital, ";
		query += "analysis_data.profit AS a_profit, ";
		query += "analysis_data.loss AS a_loss, ";
		query += "analysis_data.created_at AS a_created_at ";

		query += "FROM market_analysis ";
		query += "INNER JOIN analysis_data ";
		query += "ON market_analysis.analysis_data_id=analysis_data.id ";
		
		query += "WHERE market_analysis.id=?";
		
		System.out.println("Market Analysis SELECT Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;

		try {

			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setLong(1, id);
			
			res = stm.executeQuery();

			System.out.println(res.getFetchSize());

			if (res != null) {
				while (res.next()) {
					AnalysisEntity an = new AnalysisEntity();

					an.setId(res.getLong("m_id"));
					an.setIdentifier("identifier");
					an.setMarket(res.getString("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_MARKET));
					an.setMaxProfit(res.getInt("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_MAX_PROFIT));
					an.setMaxLoss(res.getInt("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_MAX_LOSS));
					an.setTotalLot(res.getInt("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_TOTAL_LOT));
					an.setAvailableLot( res.getInt("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_AVAILABLE_LOT) );
					an.setPrice(res.getInt("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_PRICE));
					an.setAnalysisDataId(res.getLong("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_ANALYSIS_DATA_ID));
					an.setPublished(res.getBoolean("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_PUBLISHED));

					AnalysisDataEntity data = new AnalysisDataEntity();
					data.setId(res.getLong("a_id"));
					data.setOrder(
							OrderType.valueOf(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_ORDER_TYPE)));
					data.setPosition(res.getDouble("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_POSITION));
					data.setStopLoss(res.getDouble("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_STOP_LOSS));
					data.setTakeProfit(res.getDouble("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_TAKE_PROFIT));
					data.setStartTime(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_START_TIME));
					data.setEndTime(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_END_TIME));
					data.setMarket(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_MARKET));
					data.setTimeframe(MarketTimeframe.valueOf(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_TIMEFRAME)));
					data.setCapital(res.getInt("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_CAPITAL));
					data.setProfit(res.getInt("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_LOSS));
					data.setLoss(res.getInt("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_LOSS));
					data.setCreatedAt(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_CREATED_AT));
					
					an.setAnalysisData(data);

					return an;
				}

			}
			
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
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
	}
	
	
	
	
	
	public AnalysisEntity[] findAllWithoutMarketAnalysis(int limit, int offset) {
		ArrayList<AnalysisEntity> analysis = new ArrayList<AnalysisEntity>();

		String query = "SELECT ";
		/*query += "market_analysis.id AS m_id, ";
		query += "market_analysis.identifier as m_identifier, ";
		query += "market_analysis.market AS  m_market, ";
		query += "market_analysis.published AS  m_published, ";
		query += "market_analysis.total_lot AS m_total_lot, ";
		query += "market_analysis.available_lot AS m_available_lot, ";
		query += "market_analysis.price AS m_price, ";
		query += "market_analysis.max_profit AS m_max_profit, ";
		query += "market_analysis.max_loss AS m_max_loss, ";
		query += "market_analysis.analysis_data_id as m_analysis_data_id, ";*/

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
		query += "analysis_data.capital AS a_capital, ";
		query += "analysis_data.profit AS a_profit, ";
		query += "analysis_data.loss AS a_loss ";

		query += "FROM analysis_data ";
		query += "LEFT JOIN market_analysis ";
		query += "ON market_analysis.analysis_data_id=analysis_data.id ";
		query += "WHERE market_analysis.id IS NULL;";
		System.out.println("Market Analysis SELECT Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;

		try {

			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			res = stm.executeQuery();

			System.out.println(res.getFetchSize());

			if (res != null) {
				while (res.next()) {
					AnalysisEntity an = new AnalysisEntity();

					/*an.setId(res.getLong("m_id"));
					an.setIdentifier("identifier");
					an.setMarket(res.getString("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_MARKET));
					an.setMaxProfit(res.getInt("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_MAX_PROFIT));
					an.setMaxLoss(res.getInt("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_MAX_LOSS));
					an.setTotalLot(res.getInt("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_TOTAL_LOT));
					an.setAvailableLot( res.getInt("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_AVAILABLE_LOT) );
					an.setPrice(res.getInt("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_PRICE));
					an.setAnalysisDataId(res.getLong("m_" + MarketAnalysisDaoRepository.TABLE_COLUMN_ANALYSIS_DATA_ID));
					// MarketAnalysisDaoRepository.an.setPublished(
					// res.getString( "m_" + TABLE_COLUMN_PUBLISHED ) );
					 */

					AnalysisDataEntity data = new AnalysisDataEntity();
					data.setId(res.getLong("a_id"));
					data.setOrder(
							OrderType.valueOf(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_ORDER_TYPE)));
					data.setPosition(res.getDouble("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_POSITION));
					data.setStopLoss(res.getDouble("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_STOP_LOSS));
					data.setTakeProfit(res.getDouble("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_TAKE_PROFIT));
					data.setStartTime(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_START_TIME));
					data.setEndTime(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_END_TIME));
					data.setMarket(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_MARKET));
					data.setTimeframe(MarketTimeframe.valueOf(res.getString("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_TIMEFRAME)));
					data.setCapital(res.getInt("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_CAPITAL));
					data.setProfit(res.getInt("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_PROFIT));
					data.setLoss(res.getInt("a_" + AnalysisDataDaoRepository.TABLE_COLUMN_LOSS));

					an.setAnalysisData(data);
					
					
					System.out.println("Data " + data.getCapital() + " id " + data.getId());

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
	
	/**
	 * Find all analysis fullfilled
	 * 
	 * @param limit
	 * @param offset
	 * @return
	 */
	public AnalysisEntity[] findAllWithMarketAnalysis(int limit, int offset) {
		return this.findAll(limit, offset);
	}

}
