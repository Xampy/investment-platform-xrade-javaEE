package com.xrade.orm.dao.mysqlRepository.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.xrade.entity.MemberDepositRequestEntity;

public class MemberAccountManagementRepository {
	
	private Connection connect;

	public MemberAccountManagementRepository(Connection connect) {
		this.connect = connect;
	}
	
	
	/**
	 * Update the member account amount
	 * By deposit request or withdraw request
	 * 
	 * 
	 * @param depositRequest
	 * @return
	 */
	public boolean updateAmountByRequest(MemberDepositRequestEntity depositRequest) {
		/**
		 * Update in non auto commited mode. Commit in the account dao
		 */
		
		System.out.println(depositRequest.toJson().toString());
		
		String query = "UPDATE ";
		query += MemberDepositRequestDaoRepository.TABLE_NAME + " AS d ";
		query += "INNER JOIN " + MemberAccountDaoRepository.TABLE_NAME + " AS a ";
		query += "ON d." + MemberDepositRequestDaoRepository.TABLE_COLUMN_MEMBER_ACCOUNT_ID + "=a.id ";
		query += "SET d.filled=1, a.amount=? ";
		query += "WHERE ";
		query += "d.id=?";
		
		System.out.println("Member Account Update amount Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setDouble(1, depositRequest.getAmountAfter() );
			stm.setLong(2, depositRequest.getId() );
			
			System.out.println("AffectedRows : " + stm.executeUpdate());
			res =  stm.getGeneratedKeys();
			System.out.println( res.getFetchSize() );
			
			if(res != null){
				//UYpdate the depositRequestect id here
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
