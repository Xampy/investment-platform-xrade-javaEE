package com.xrade.orm.dao.mysqlRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.xrade.entity.MarketOrderEntity;
import com.xrade.entity.MarketOrderProcessEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.models.order.OrderType;
import com.xrade.orm.dao.AbstractDaoRepository;

public class MarketOrderDaoRepository extends AbstractDaoRepository<MarketOrderEntity> {
	
	
	public static final String TABLE_COLUMN_POSITION = "position";
	public static final String TABLE_COLUMN_STOP_LOSS = "stop_loss";
	public static final String TABLE_COLUMN_TAKE_PROFIT = "take_profit";
	public static final String TABLE_COLUMN_LOT = "lot";
	public static final String TABLE_COLUMN_ORDER_TYPE = "order_type";
	public static final String TABLE_COLUMN_AMOUNT = "amount";
	public static final String TABLE_COLUMN_CREATED_AT = "created_at";
	
	public static final String TABLE_COLUMN_MARKET_ANALYSIS_ID = "market_analysis_id";
	public static final String TABLE_COLUMN_MEMBER_ID = "member_id";
	
	public static final String TABLE_NAME = "market_order";
	
	
	public MarketOrderDaoRepository(Connection connect) {
		super(connect);
		this.setTable("market_order");
	}
	
	
	@Override
	public boolean create(MarketOrderEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query += "("
				+ "position, stop_loss, take_profit, lot, order_type, market_analysis_id, member_id, amount )"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		
		System.out.println("Market Order ADD Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, obj.getPosition());
			stm.setInt(2, obj.getStopLoss());
			stm.setInt(3, obj.getTakeProfit());
			stm.setInt(4, obj.getLot());
			stm.setString(5, obj.getType().toString());
			stm.setLong(6, obj.getMarketAnalysisId());
			stm.setLong(7, obj.getMemberId());
			stm.setLong(8, obj.getAmount());
			
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
	@Override
	public boolean update(MarketOrderEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public MarketOrderEntity[] findAll(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public MarketOrderEntity find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	//Custom function on repository
	/**
	 * Count order by market analysis id
	 * 
	 * @param  marketAanalysisId 
	 * @return long count 
	 */
	public long countOrderBy(long marketAnalysisId) {
		String query = "SELECT COUNT(*) as total FROM " + this.table + " ";
		query += "WHERE market_analysis_id=?";
		
		System.out.println("Market Order SELECT WITH Count Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setLong(1, marketAnalysisId);
			
			
			
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			if(res != null && res.next()){
				//UYpdate the object id here
				int count = (int) res.getLong("total");
				System.out.println("Count orders ---> " + count);
				return count;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
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
	
	/**
	 * 
	 * Get the greatest amount used on the
	 * market analysis
	 * 
	 * @param marketAnalysisId
	 * @return int amount
	 */
	public int getMaxAmountOrderBy(long marketAnalysisId) {
		String query = "SELECT MAX(amount) as amount FROM " + this.table + " ";
		query += "WHERE market_analysis_id=?";
		
		System.out.println("Market Order SELECT WITH Max amount Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setLong(1, marketAnalysisId);
			
			
			
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			if(res != null && res.next()){
				//UYpdate the object id here
				int count = (int) res.getLong("amount");
				System.out.println("max amount order ---> " + count);
				return count;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
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

	/**
	 * 
	 * Get all the order orodered by the current member
	 * at the current day
	 * 
	 * @param member
	 */
	public MarketOrderEntity[] findForDayByMember(MemberEntity member) {
		String query = "SELECT ";
		query += TABLE_NAME + ".id as o_id, ";
		query += TABLE_NAME + ".position as o_position, ";
		query += TABLE_NAME + ".stop_loss as o_stop_loss, ";
		query += TABLE_NAME + ".take_profit as o_take_profit, ";
		query += TABLE_NAME + ".lot as o_lot, ";
		query += TABLE_NAME + ".order_type as o_order_type, ";
		query += TABLE_NAME + ".amount as o_amount, ";
		query += TABLE_NAME + ".created_at as o_created_at, ";
		query += TABLE_NAME + ".market_analysis_id as o_market_analysis_id, ";
		query += TABLE_NAME + ".member_id as o_member_id, ";
		
		query += "op.id as op_id, ";
		query += "op.last_position as op_last_position, ";
		query += "op.status as op_position, ";
		query += "op.close_date as op_close_date, ";
		query += "op.benefit as op_benefit ";
		
		query += "FROM " + TABLE_NAME + " ";
		query += "INNER JOIN " + MarketOrderProcessDaoRepository.TABLE_NAME + " as op ";
		query += "ON op." + MarketOrderProcessDaoRepository.TABLE_COLUMN_MARKET_ORDER_ID + "="  + TABLE_NAME + ".id ";
		query += "WHERE " + TABLE_NAME + ".member_id=? ";
		query += "AND DATE(" + TABLE_NAME + ".created_at)=CURDATE()"; //Only this day
		
		
		System.out.println("Market Order SELECT day ly amount Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setLong(1, 1); //member.getId()
			
			
			
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			ArrayList<MarketOrderEntity> orders = new ArrayList<MarketOrderEntity>();
			
			while(res != null && res.next()){
				
				MarketOrderEntity order = new MarketOrderEntity();
				order.setId(res.getLong("o_id"));
				order.setPosition(res.getDouble("o_" + TABLE_COLUMN_POSITION));
				order.setStopLoss(res.getInt("o_" + TABLE_COLUMN_STOP_LOSS));
				order.setTakeProfit(res.getInt("o_" + TABLE_COLUMN_TAKE_PROFIT));
				order.setLot(res.getInt("o_" + TABLE_COLUMN_LOT));
				order.setType(OrderType.valueOf( res.getString("o_" + TABLE_COLUMN_ORDER_TYPE)) );
				order.setAmount(res.getInt("o_" + TABLE_COLUMN_AMOUNT));
				order.setMarketAnalysisId(res.getLong( "o_" + TABLE_COLUMN_MARKET_ANALYSIS_ID));
				order.setMemberId(res.getLong("o_" + TABLE_COLUMN_MEMBER_ID));
				order.setCreatedAt(res.getString("o_" + TABLE_COLUMN_CREATED_AT));
				
				System.out.println("Date " + order.getCreatedAt());
				
				MarketOrderProcessEntity process = new MarketOrderProcessEntity();
				process.setId(res.getLong("op_id"));
				process.setCloseDate(res.getString("op_" + MarketOrderProcessDaoRepository.TABLE_COLUMN_CLOSE_DATE));
				process.setBenefit(res.getInt("op_" + MarketOrderProcessDaoRepository.TABLE_COLUMN_BENEFIT));
				process.setLastPosition(res.getDouble("op_" + MarketOrderProcessDaoRepository.TABLE_COLUMN_LAST_POSITION));
				
				System.out.println("Date " + process.getCloseDate());
				order.setProcess(process);
				
				orders.add(order);
			}
			return orders.toArray(new MarketOrderEntity[orders.size()]);
			
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
	
	
	
	
}
