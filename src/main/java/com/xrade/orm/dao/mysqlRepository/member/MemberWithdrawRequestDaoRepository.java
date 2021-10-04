package com.xrade.orm.dao.mysqlRepository.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberDepositRequestEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.entity.MemberWithdrawRequestEntity;
import com.xrade.orm.dao.AbstractDaoRepository;

public class MemberWithdrawRequestDaoRepository extends AbstractDaoRepository<MemberWithdrawRequestEntity> {
	
	public  static final String TABLE_COLUMN_PAYMENT =  "payment";
	public  static final String TABLE_COLUMN_ACCOUNT_TYPE =  "account_type";
	public  static final String TABLE_COLUMN_AMOUNT = "amount";
	public  static final String TABLE_COLUMN_AMOUNT_BEFORE = "amount_before";
	public  static final String TABLE_COLUMN_AMOUNT_AFTER = "amount_after";
	public  static final String TABLE_COLUMN_METADATA = "metadata";
	public  static final String TABLE_COLUMN_FILLED = "filled";
	public  static final String TABLE_COLUMN_DATE = "date_init";
	public  static final String TABLE_COLUMN_EXTRA = "extra";
	
	public  static final String TABLE_COLUMN_MEMBER_ACCOUNT_ID = "member_account_id";
	
	public  static final String TABLE_NAME = "member_withdraw_request";
	
	public MemberWithdrawRequestDaoRepository(Connection connect) {
		super(connect);
		this.setTable(TABLE_NAME);
	}

	@Override
	public boolean create(MemberWithdrawRequestEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query += "(payment, amount, amount_before, amount_after, ";
		query += "metadata, filled, date_init, member_account_id, account_type, extra)";
		query += "VALUES(?, ?, ?, ?, ?, ?, now(), ?, ?, ?)";
		
		System.out.println("Member Withdrawal Query ADD Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, obj.getPayment());
			stm.setDouble(2, obj.getAmount());
			stm.setDouble(3, obj.getAmountBefore());
			stm.setDouble(4, obj.getAmountAfter());
			stm.setString(5, obj.getMetadata());
			stm.setBoolean(6, obj.isFilled());
			//stm.setString(7, obj.getDate());
			stm.setLong(7, obj.getMemberAccountId());
			stm.setString(8, obj.getAccountType());
			stm.setString(9, obj.getExtra());
			
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
	public boolean update(MemberWithdrawRequestEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MemberWithdrawRequestEntity[] findAll(int limit, int offset) {
		String query = "SELECT * FROM ";
		query += this.table + " ";
		
		System.out.println("Member Withdraw Query SELECT ALL Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);			
			ArrayList<MemberWithdrawRequestEntity> requests = new ArrayList< MemberWithdrawRequestEntity>();
			
			res = stm.executeQuery();			
			System.out.println( res.getFetchSize() );
			
			while(res != null && res.next()){
				//UYpdate the object id here
				int id = res.getInt(1);
				System.out.println("Select  id ---> " + id);
				
				MemberWithdrawRequestEntity request = new MemberWithdrawRequestEntity();
				
				
				request.setAmount( res.getInt(TABLE_COLUMN_AMOUNT) );
				request.setAmountAfter(res.getInt(TABLE_COLUMN_AMOUNT_AFTER) );
				request.setAmountBefore(res.getInt(TABLE_COLUMN_AMOUNT_BEFORE));
				request.setDate(res.getString(TABLE_COLUMN_DATE));
				request.setFilled(res.getBoolean(TABLE_COLUMN_FILLED));
				request.setId(res.getLong("id"));
				request.setMetadata(res.getString(TABLE_COLUMN_METADATA));
				request.setMemberAccountId(res.getLong(TABLE_COLUMN_MEMBER_ACCOUNT_ID));
				request.setPayment(res.getString(TABLE_COLUMN_PAYMENT));
				request.setAccountType(res.getString(TABLE_COLUMN_ACCOUNT_TYPE));
				request.setExtra(res.getString(TABLE_COLUMN_EXTRA));
				
				
				requests.add(request);
				
			}
			
			if(requests.size() > 0)
				return requests.toArray(new MemberWithdrawRequestEntity[requests.size()]);
			
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
	 * Get member request on withdrawing
	 * 
	 * @param accountId
	 * @param limit
	 * @param offset
	 * @return
	 */
	public MemberWithdrawRequestEntity[] findByMemberAccountId(int accountId, int limit, int offset) {
		String query = "SELECT * FROM ";
		query += this.table + " ";
		query +=  "WHERE ";
		query += "member_account_id=?";
		
		System.out.println("Member Withdraw By account id Query ALL Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);	
			stm.setInt(1, accountId);
			ArrayList<MemberWithdrawRequestEntity> requests = new ArrayList< MemberWithdrawRequestEntity>();
			
			res = stm.executeQuery();			
			System.out.println( res.getFetchSize() );
			
			while(res != null && res.next()){
				//UYpdate the object id here
				int id = res.getInt(1);
				System.out.println("Select  id ---> " + id);
				
				MemberWithdrawRequestEntity request = new MemberWithdrawRequestEntity();
				
				
				request.setAmount( res.getInt(TABLE_COLUMN_AMOUNT) );
				request.setAmountAfter(res.getInt(TABLE_COLUMN_AMOUNT_AFTER) );
				request.setAmountBefore(res.getInt(TABLE_COLUMN_AMOUNT_BEFORE));
				request.setDate(res.getString(TABLE_COLUMN_DATE));
				request.setFilled(res.getBoolean(TABLE_COLUMN_FILLED));
				request.setId(res.getLong("id"));
				request.setMetadata(res.getString(TABLE_COLUMN_METADATA));
				request.setMemberAccountId(res.getLong(TABLE_COLUMN_MEMBER_ACCOUNT_ID));
				request.setPayment(res.getString(TABLE_COLUMN_PAYMENT));
				request.setAccountType(res.getString(TABLE_COLUMN_ACCOUNT_TYPE));
				request.setExtra(res.getString(TABLE_COLUMN_EXTRA));
				
				
				requests.add(request);
				
			}
			return requests.toArray(new MemberWithdrawRequestEntity[requests.size()]);
			
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
	public MemberWithdrawRequestEntity find(int id) {
		String query = "SELECT * FROM ";
		query += this.table + " ";
		query +=  "WHERE ";
		query += "id=?";
		
		System.out.println("Member Withdraw Query SELECT Single Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setInt(1, id);
			ArrayList<MemberWithdrawRequestEntity> requests = new ArrayList< MemberWithdrawRequestEntity>();
			
			res = stm.executeQuery();			
			System.out.println( res.getFetchSize() );
			
			while(res != null && res.next()){
				//UYpdate the object id here
				//int id = res.getInt(1);
				System.out.println("Select  id ---> " + id);
				
				MemberWithdrawRequestEntity request = new MemberWithdrawRequestEntity();
				
				
				request.setAmount( res.getInt(TABLE_COLUMN_AMOUNT) );
				request.setAmountAfter(res.getInt(TABLE_COLUMN_AMOUNT_AFTER) );
				request.setAmountBefore(res.getInt(TABLE_COLUMN_AMOUNT_BEFORE));
				request.setDate(res.getString(TABLE_COLUMN_DATE));
				request.setFilled(res.getBoolean(TABLE_COLUMN_FILLED));
				request.setId(res.getLong("id"));
				request.setMetadata(res.getString(TABLE_COLUMN_METADATA));
				request.setMemberAccountId(res.getLong(TABLE_COLUMN_MEMBER_ACCOUNT_ID));
				request.setPayment(res.getString(TABLE_COLUMN_PAYMENT));
				request.setAccountType(res.getString(TABLE_COLUMN_ACCOUNT_TYPE));
				request.setExtra(res.getString(TABLE_COLUMN_EXTRA));
				
				MemberAccountEntity account = new MemberAccountEntity();
				
				request.setAccount(account);
				
				
				requests.add(request);
				
			}
			return requests.get(0);
			
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
	 * Count member total request
	 * 
	 * @param accountId the id of the associated account
	 * @return
	 */
	public int countByMemberAccountId(int accountId) {
		String query = "SELECT COUNT(*) as total FROM ";
		query += this.table + " ";
		query +=  "WHERE ";
		query += "member_account_id=?";
		
		System.out.println("Member Withdraw By account id Query ALL Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);	
			stm.setInt(1, accountId);
			
			res = stm.executeQuery();			
			System.out.println( res.getFetchSize() );
			
			while(res != null && res.next()){
				//UYpdate the object id here
				int id = res.getInt(1);
				return res.getInt("total");
				
			}
			return 0;
			
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
	
	
	/**
	 * Fint unfilled deposit request by member id
	 * If all request are filled then we can create a new request
	 * 
	 * @param memberId the member id
	 * @return
	 */
	public MemberWithdrawRequestEntity findUnfilledByMemberId(int memberId) {
		String query = "SELECT ";
		query += TABLE_NAME + ".id, ";
		query += TABLE_NAME + ".payment, ";
		query += TABLE_NAME + ".amount, ";
		query += TABLE_NAME + ".amount_before, ";
		query += TABLE_NAME + ".amount_after, ";
		query += TABLE_NAME + ".metadata, ";
		query += TABLE_NAME + ".filled, ";
		query += TABLE_NAME + ".date_init, ";
		query += TABLE_NAME + ".member_account_id ";
		query += "FROM " + this.table + " ";
		query += "INNER JOIN " + MemberAccountDaoRepository.TABLE_NAME + " ";
		query += "ON " + this.table  + ".member_account_id=" + MemberAccountDaoRepository.TABLE_NAME + ".id ";
		query += "INNER JOIN " + MemberDaoRepository.TABLE_NAME + " ";
		query += "ON " + MemberAccountDaoRepository.TABLE_NAME  + ".member_id=" + MemberDaoRepository.TABLE_NAME + ".id ";
		query +=  "WHERE ";
		query += MemberDaoRepository.TABLE_NAME + ".id=? ";
		query += "AND " + TABLE_NAME + ".filled=0 LIMIT 1";
		
		System.out.println("Member Withdraw By member id Query ALL Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);	
			stm.setInt(1, memberId);
			ArrayList< MemberWithdrawRequestEntity> requests = new ArrayList<MemberWithdrawRequestEntity>();
			
			res = stm.executeQuery();			
			System.out.println( res.getFetchSize() );
			
			while(res != null && res.next()){
				//UYpdate the object id here
				int id = res.getInt(1);
				System.out.println("Select  id ---> " + id);
				
				 MemberWithdrawRequestEntity request = new  MemberWithdrawRequestEntity();
				
				
				request.setAmount( res.getInt(TABLE_COLUMN_AMOUNT) );
				request.setAmountAfter(res.getInt(TABLE_COLUMN_AMOUNT_AFTER) );
				request.setAmountBefore(res.getInt(TABLE_COLUMN_AMOUNT_BEFORE));
				request.setDate(res.getString(TABLE_COLUMN_DATE));
				request.setFilled(res.getBoolean(TABLE_COLUMN_FILLED));
				request.setId(res.getLong("id"));
				request.setMetadata(res.getString(TABLE_COLUMN_METADATA));
				request.setMemberAccountId(res.getLong(TABLE_COLUMN_MEMBER_ACCOUNT_ID));
				request.setPayment(res.getString(TABLE_COLUMN_PAYMENT));
				request.setAccountType(res.getString(TABLE_COLUMN_ACCOUNT_TYPE));
				request.setExtra(res.getString(TABLE_COLUMN_EXTRA));
				
				
				requests.add(request);
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
	 * Get unfilled all unfilled withdrawal request 
	 * 
	 * @param limit
	 * @param offset
	 * @return array of unfilled request or null
	 */
	public MemberWithdrawRequestEntity[] getUnfilled(int limit, int offset) {
		String query = "SELECT * FROM ";
		query += this.table + " ";
		query += "WHERE filled=0 ORDER BY date_init DESC LIMIT 100";
		
		System.out.println("Member Withdraw Query SELECT ALL Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);			
			ArrayList<MemberWithdrawRequestEntity> requests = new ArrayList< MemberWithdrawRequestEntity>();
			
			res = stm.executeQuery();			
			System.out.println( res.getFetchSize() );
			
			while(res != null && res.next()){
				//UYpdate the object id here
				int id = res.getInt(1);
				System.out.println("Select  id ---> " + id);
				
				MemberWithdrawRequestEntity request = new MemberWithdrawRequestEntity();
				
				
				request.setAmount( res.getInt(TABLE_COLUMN_AMOUNT) );
				request.setAmountAfter(res.getInt(TABLE_COLUMN_AMOUNT_AFTER) );
				request.setAmountBefore(res.getInt(TABLE_COLUMN_AMOUNT_BEFORE));
				request.setDate(res.getString(TABLE_COLUMN_DATE));
				request.setFilled(res.getBoolean(TABLE_COLUMN_FILLED));
				request.setId(res.getLong("id"));
				request.setMetadata(res.getString(TABLE_COLUMN_METADATA));
				request.setMemberAccountId(res.getLong(TABLE_COLUMN_MEMBER_ACCOUNT_ID));
				request.setPayment(res.getString(TABLE_COLUMN_PAYMENT));
				request.setAccountType(res.getString(TABLE_COLUMN_ACCOUNT_TYPE));
				request.setExtra(res.getString(TABLE_COLUMN_EXTRA));
				
				
				requests.add(request);
				
			}
			
			if(requests.size() > 0)
				return requests.toArray(
						new MemberWithdrawRequestEntity[requests.size()]);
			
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

	public boolean updateFilledStatus(MemberWithdrawRequestEntity obj) {
		String query = "UPDATE " + this.table + " ";
		query += "SET filled=1, amount_before=?, amount_after=? WHERE id=?";
		
		System.out.println("Member " + obj.getId() + " Account status  update Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, obj.getAmountBefore());
			stm.setDouble(2, obj.getAmountAfter());
			stm.setLong(3, obj.getId());
			
			int rows = stm.executeUpdate();
			
			res =  stm.getGeneratedKeys();
			System.out.println( res.getFetchSize() );
			
			if(res != null && rows > 0){
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
	 * Get the lastes withdrawal made by the member
	 * @param member
	 * @return
	 */
	public MemberWithdrawRequestEntity[] getLatestWithdrawalByMember(MemberEntity member){
		String query = "SELECT * FROM ";
		query += this.table + " ";
		query += "WHERE member_account_id=? ";
		query += "ORDER BY date_init DESC LIMIT 10";
		
		
		System.out.println("Member Withdraw Query SELECT ALL Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setLong(1, ((MemberAccountEntity)member.getAccount()).getId()  );
			ArrayList<MemberWithdrawRequestEntity> requests = new ArrayList< MemberWithdrawRequestEntity>();
			
			res = stm.executeQuery();			
			System.out.println( res.getFetchSize() );
			
			while(res != null && res.next()){
				//UYpdate the object id here
				int id = res.getInt(1);
				System.out.println("Select  id ---> " + id);
				
				MemberWithdrawRequestEntity request = new MemberWithdrawRequestEntity();
				
				
				request.setAmount( res.getInt(TABLE_COLUMN_AMOUNT) );
				request.setAmountAfter(res.getInt(TABLE_COLUMN_AMOUNT_AFTER) );
				request.setAmountBefore(res.getInt(TABLE_COLUMN_AMOUNT_BEFORE));
				request.setDate(res.getString(TABLE_COLUMN_DATE));
				request.setFilled(res.getBoolean(TABLE_COLUMN_FILLED));
				request.setId(res.getLong("id"));
				request.setMetadata(res.getString(TABLE_COLUMN_METADATA));
				request.setMemberAccountId(res.getLong(TABLE_COLUMN_MEMBER_ACCOUNT_ID));
				request.setPayment(res.getString(TABLE_COLUMN_PAYMENT));
				request.setAccountType(res.getString(TABLE_COLUMN_ACCOUNT_TYPE));
				request.setExtra(res.getString(TABLE_COLUMN_EXTRA));
				
				
				requests.add(request);
				
			}
			
			//if(requests.size() > 0)
				return requests.toArray(
						new MemberWithdrawRequestEntity[requests.size()]);
			
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
