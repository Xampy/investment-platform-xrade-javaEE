package com.xrade.orm.dao.mysqlRepository.member.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.xrade.entity.MemberPerfectMoneyTransactionEntity;
import com.xrade.orm.dao.AbstractDaoRepository;

public class MemberPerfectMoneyTransactionDaoRepository
		extends AbstractDaoRepository<MemberPerfectMoneyTransactionEntity> {

	public static final String TABLE_COLUMN_PAYMENT = "payment";
	public static final String TABLE_COLUMN_AMOUNT = "amount";
	public static final String TABLE_COLUMN_AMOUNT_BEFORE = "amount_before";
	public static final String TABLE_COLUMN_AMOUNT_AFTER = "amount_after";
	public static final String TABLE_COLUMN_METADATA = "metadata";
	public static final String TABLE_COLUMN_FILLED = "filled";
	public static final String TABLE_COLUMN_CREATED_AT = "created_at";
	public static final String TABLE_COLUMN_PAYMENT_ID = "payment_id";

	public static final String TABLE_COLUMN_MEMBER_ACCOUNT_ID = "member_account_id";

	public static final String TABLE_NAME = "member_perfect_money_transaction";

	public MemberPerfectMoneyTransactionDaoRepository(Connection connect) {
		super(connect);
		this.setTable(TABLE_NAME);
	}

	@Override
	public boolean create(MemberPerfectMoneyTransactionEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query += "(amount, amount_before, amount_after, metadata, filled, created_at, member_account_id) ";
		query += "VALUES(?, ?, ?, ?, ?, now(), ?)";

		System.out.println("Member perfect money transaction Query ADD Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;

		try {

			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, obj.getAmount());
			stm.setDouble(2, obj.getAmountBefore());
			stm.setDouble(3, obj.getAmountAfter());
			stm.setString(4, obj.getMetadata());
			stm.setBoolean(5, false); /// api/v1/member/fund/deposit
			// stm.setString(7, obj.getDate());
			stm.setLong(6, obj.getMemberAccountId());

			stm.executeUpdate();

			res = stm.getGeneratedKeys();
			System.out.println(res.getFetchSize());

			if (res != null && res.next()) {
				// UYpdate the object id here
				int id = res.getInt(1);
				System.out.println("Inserted perfect money transaction id ---> " + id);
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
	public boolean update(MemberPerfectMoneyTransactionEntity obj) {
		String query = "UPDATE " + this.table + " ";
		query += "SET filled=?, identifier=?, payment_id=? WHERE id=?";

		System.out.println("Member Perfect Money transaction Query UPdate Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;

		try {

			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setBoolean(1, obj.isFilled());
			stm.setString(2,  obj.getIdentifier());
			stm.setString(3, obj.getPaymentId());
			stm.setLong(4, obj.getId());

			int result = stm.executeUpdate();

			res = stm.getGeneratedKeys();
			System.out.println(res.getFetchSize());

			if (res != null && result > 0) {
				// UYpdate the object id here
				//int id = res.getInt(1);
				System.out.println("Ipdate perfect money transaction row count ---> " + result);
				//obj.setId(id);

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
	public MemberPerfectMoneyTransactionEntity[] findAll(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberPerfectMoneyTransactionEntity find(int id) {
		String query = "SELECT * FROM ";
		query += this.table + " ";
		query += "WHERE ";
		query += "id=?";

		System.out.println("Member Withdraw Query SELECT Single Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;

		try {

			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setInt(1, id);

			res = stm.executeQuery();
			System.out.println(res.getFetchSize());

			while (res != null && res.next()) {
				// UYpdate the object id here
				// int id = res.getInt(1);
				System.out.println("Select  id ---> " + id);

				MemberPerfectMoneyTransactionEntity request = new MemberPerfectMoneyTransactionEntity();

				request.setAmount(res.getInt(TABLE_COLUMN_AMOUNT));
				request.setAmountAfter(res.getInt(TABLE_COLUMN_AMOUNT_AFTER));
				request.setAmountBefore(res.getInt(TABLE_COLUMN_AMOUNT_BEFORE));
				request.setCreatedAt(res.getString(TABLE_COLUMN_CREATED_AT));
				request.setFilled(res.getBoolean(TABLE_COLUMN_FILLED));
				request.setId(res.getLong("id"));
				request.setMetadata(TABLE_COLUMN_METADATA);
				request.setMemberAccountId(res.getLong(TABLE_COLUMN_MEMBER_ACCOUNT_ID));
				request.setPaymentId(res.getString(TABLE_COLUMN_PAYMENT_ID));

				return request;

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

	/**
	 * Find a transaction by the identifier
	 * 
	 * @param identifier
	 *            payment identifier
	 * @return
	 */
	public MemberPerfectMoneyTransactionEntity findByIdentifier(String identifier) {
		String query = "SELECT * FROM ";
		query += this.table + " ";
		query += "WHERE ";
		query += "identifier=?";

		System.out.println("Member Withdraw Query SELECT Single Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;

		try {

			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, identifier);

			res = stm.executeQuery();
			System.out.println(res.getFetchSize());

			while (res != null && res.next()) {
				// UYpdate the object id here
				int id = res.getInt(1);
				System.out.println("Select  id ---> " + id);

				MemberPerfectMoneyTransactionEntity request = new MemberPerfectMoneyTransactionEntity();

				request.setAmount(res.getInt(TABLE_COLUMN_AMOUNT));
				request.setAmountAfter(res.getInt(TABLE_COLUMN_AMOUNT_AFTER));
				request.setAmountBefore(res.getInt(TABLE_COLUMN_AMOUNT_BEFORE));
				request.setCreatedAt(res.getString(TABLE_COLUMN_CREATED_AT));
				request.setFilled(res.getBoolean(TABLE_COLUMN_FILLED));
				request.setId(res.getLong("id"));
				request.setMetadata(TABLE_COLUMN_METADATA);
				request.setMemberAccountId(res.getLong(TABLE_COLUMN_MEMBER_ACCOUNT_ID));
				request.setPaymentId(res.getString(TABLE_COLUMN_PAYMENT_ID));

				return request;

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
	
	
	
	/**
	 * Uopdate the transaction filed status. If the filed status
	 * was false it becommes true
	 * 
	 * @param obj
	 * @return
	 */
	public boolean updateFillStatus( MemberPerfectMoneyTransactionEntity obj) {
		String query = "UPDATE " + this.table + " ";
		query += "SET filled=1 WHERE id=?";
		
		System.out.println("Member " + obj.getId() + " Account amount update Query : " + query);
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

}
