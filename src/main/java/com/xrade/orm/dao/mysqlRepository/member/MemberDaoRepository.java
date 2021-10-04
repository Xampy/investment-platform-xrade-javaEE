package com.xrade.orm.dao.mysqlRepository.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.xrade.entity.MemberAccountEntity;
import com.xrade.entity.MemberInterestPaymentAccountEntity;
import com.xrade.entity.MemberRegisterVerificatorEntity;
import com.xrade.entity.MemberSponsorshipPaymentAccountEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.models.user.MemberGrade;
import com.xrade.orm.dao.AbstractDaoRepository;

public class MemberDaoRepository extends AbstractDaoRepository<MemberEntity>{
	
	public static final String TABLE_COLUMN_LASTNAME = "lastname";
	public static final String TABLE_COLUMN_FIRSTNAME = "firstname";
	public static final String TABLE_COLUMN_EMAIL = "email";
	public static final String TABLE_COLUMN_COUNTRY = "country";
	public static final String TABLE_COLUMN_PHONE = "phone";
	public static final String TABLE_COLUMN_POINT = "point";
	public static final String TABLE_COLUMN_GRADE = "grade";
	public static final String TABLE_COLUMN_REFERENCE = "reference";
	public static final String TABLE_COLUMN_REFERENCED = "referenced";
	public static final String TABLE_COLUMN_CREATED_AT = "created_at";
	public static final String TABLE_COLUMN_VERIFIED = "verified";
	
	public static final String TABLE_COLUMN_PASSWORD = "password";
	
	public static final String TABLE_NAME = "member";

	public MemberDaoRepository(Connection connect) {
		super(connect);
		this.setTable("member");
	}

	@Override
	public boolean create(MemberEntity obj) {
		String query = "INSERT INTO " + this.table + " ";
		query += "("
				+ "lastname, firstname, email, phone, reference, referenced, password, country)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		
		System.out.println("Member ADD Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, obj.getLastname());
			stm.setString(2, obj.getFirstname());
			stm.setString(3, obj.getEmail());
			stm.setString(4, obj.getPhone());
			stm.setString(5, obj.getReference());
			stm.setString(6, obj.getReferenced());
			stm.setString(7, obj.getPassword());
			stm.setString(8, obj.getCountry());
			
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
	public boolean update(MemberEntity obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MemberEntity[] findAll(int limit, int offset) {		
		String query = "SELECT ";
		query += "member.id as m_id, ";
		query += "member.lastname as m_lastname, ";
		query += "member.firstname as m_firstname, ";
		query += "member.email as m_email, ";
		query += "member.phone as m_phone, ";
		query += "member.grade as m_grade, ";
		query += "member.point as m_point, ";
		query += "member.reference as m_reference, ";
		query += "member.referenced as m_referenced, ";
		query += "member.created_at as m_created_at, ";
		query += "member.verified as m_verified, ";
		query += "member_account.id as a_id, ";
		query += "member_account.amount as a_amount, ";
		query += "member_account.transaction_code as a_transaction_code, ";
		query += "member_account.member_id as a_member_id, ";
		query += "ipa.amount as ipa_amount, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "ipa.id as ipa_id, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "spa.amount as spa_amount, "; //Table alias using MemberSsponsorshipPaymentAccountDaoRepository
		query += "spa.id as spa_id "; //Table alias using MemberSponsorshipPaymentAccountDaoRepository
		query += "FROM " + this.table + " ";
		query += "INNER JOIN member_account ";
		query += "ON member.id=member_account.member_id ";
		query += "INNER JOIN " + MemberInterestPaymentAccountDaoRepository.TABLE_NAME + " as ipa ";
		query += "ON ipa.member_account_id=member_account.id ";
		query += "INNER JOIN " + MemberSponsorshipPaymentAccountDaoRepository.TABLE_NAME + " as spa ";
		query += "ON spa.member_account_id=member_account.id ";
		//query += "WHERE " ;
		//query += " phone=? AND password =?";
		
		System.out.println("Member SELECT ALL Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;
		
		ArrayList<MemberEntity> members = new ArrayList<MemberEntity>();
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);			
			
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			while(res != null && res.next()){
				//Update the object id here
				int id = res.getInt(1);
				System.out.println("Selected id ---> " + id);
				MemberEntity member = new MemberEntity();
				
				
				member.setId( res.getLong("m_id") );
				member.setEmail(res.getString( "m_" + TABLE_COLUMN_EMAIL));
				member.setFirstname(res.getString("m_" + TABLE_COLUMN_FIRSTNAME));
				member.setLastname(res.getString("m_" + TABLE_COLUMN_LASTNAME));
				member.setPoint(res.getInt("m_" + TABLE_COLUMN_POINT));
				member.setGrade(MemberGrade.valueOf(res.getString("m_" + TABLE_COLUMN_GRADE)));
				member.setPhone(res.getString("m_" + TABLE_COLUMN_PHONE));
				member.setReference(res.getString("m_" + TABLE_COLUMN_REFERENCE));
				member.setReferenced(res.getString("m_" + TABLE_COLUMN_REFERENCED));
				member.setCreatedAt(res.getString("m_" + TABLE_COLUMN_CREATED_AT));
				member.setVerified(res.getBoolean("m_" + TABLE_COLUMN_VERIFIED));
				
				
				//Create account for the user
				MemberAccountEntity account = new MemberAccountEntity();
				account.setId(res.getLong("a_id"));
				account.setMemberId(member.getId());
				account.setAmount(res.getInt("a_amount"));
				account.setTransacrionCode(res.getString("a_transaction_code"));
				
				//Create the interest account
				MemberInterestPaymentAccountEntity interestAccount = new MemberInterestPaymentAccountEntity();
				interestAccount.setAmount(res.getDouble("ipa_amount"));
				interestAccount.setMemberAccountId(account.getId());
				interestAccount.setId(res.getLong("ipa_id"));
				
				//Create the sponsorship account
				MemberSponsorshipPaymentAccountEntity sponsorshipAccount = new MemberSponsorshipPaymentAccountEntity();
				sponsorshipAccount.setAmount(res.getDouble("spa_amount"));
				sponsorshipAccount.setMemberAccountId(account.getId());
				sponsorshipAccount.setId(res.getLong("spa_id"));
				
				member.setAccount(account);
				member.setInterestAccount(interestAccount);
				member.setSponsorshipAccount(sponsorshipAccount);
				members.add(member);
				
				
				
			}
			
			if(members.size() > 0){
				return  members.toArray( new MemberEntity[members.size()] );
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

	@Override
	public MemberEntity find(int id) {
		String query = "SELECT ";
		query += "member.id as m_id, ";
		query += "member.lastname as m_lastname, ";
		query += "member.firstname as m_firstname, ";
		query += "member.email as m_email, ";
		query += "member.grade as m_grade, ";
		query += "member.point as m_point, ";
		query += "member.phone as m_phone, ";
		query += "member.password as m_password, ";
		query += "member.reference as m_reference, ";
		query += "member.referenced as m_referenced, ";
		query += "member.created_at as m_created_at, ";
		query += "member.verified as m_verified, ";
		query += "member_account.id as a_id, ";
		query += "member_account.amount as a_amount, ";
		query += "member_account.transaction_code as a_transaction_code, ";
		query += "member_account.member_id as a_member_id, ";
		query += "ipa.amount as ipa_amount, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "ipa.id as ipa_id, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "spa.amount as spa_amount, "; //Table alias using MemberSsponsorshipPaymentAccountDaoRepository
		query += "spa.id as spa_id "; //Table alias using MemberSponsorshipPaymentAccountDaoRepository
		query += "FROM " + this.table + " ";
		query += "INNER JOIN member_account ";
		query += "ON member.id=member_account.member_id ";
		query += "INNER JOIN " + MemberInterestPaymentAccountDaoRepository.TABLE_NAME + " as ipa ";
		query += "ON ipa.member_account_id=member_account.id ";
		query += "INNER JOIN " + MemberSponsorshipPaymentAccountDaoRepository.TABLE_NAME + " as spa ";
		query += "ON spa.member_account_id=member_account.id ";
		query += "WHERE " ;
		query += "member.id=?;";
		
		System.out.println("Member SELECT ALL Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);			
			stm.setInt(1, id);
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			while(res != null && res.next()){
				//Update the object id here
				//int id = res.getInt(1);
				System.out.println("Selected id ---> " + id);
				MemberEntity member = new MemberEntity();
				
				
				member.setId( res.getLong("m_id") );
				member.setPassword(res.getString("m_" + TABLE_COLUMN_PASSWORD));
				member.setEmail(res.getString( "m_" + TABLE_COLUMN_EMAIL));
				member.setFirstname(res.getString("m_" + TABLE_COLUMN_FIRSTNAME));
				member.setLastname(res.getString("m_" + TABLE_COLUMN_LASTNAME));
				member.setPhone(res.getString("m_" + TABLE_COLUMN_PHONE));
				member.setPoint(res.getInt("m_" + TABLE_COLUMN_POINT));
				member.setGrade(MemberGrade.valueOf(res.getString("m_" + TABLE_COLUMN_GRADE)));
				member.setReference(res.getString("m_" + TABLE_COLUMN_REFERENCE));
				member.setReferenced(res.getString("m_" + TABLE_COLUMN_REFERENCED));
				member.setCreatedAt(res.getString("m_" + TABLE_COLUMN_CREATED_AT));
				member.setVerified(res.getBoolean("m_" + TABLE_COLUMN_VERIFIED));
				
				
				//Create account for the user
				MemberAccountEntity account = new MemberAccountEntity();
				account.setId(res.getLong("a_id"));
				account.setMemberId(member.getId());
				account.setAmount(res.getInt("a_amount"));
				account.setTransacrionCode(res.getString("a_transaction_code"));
				
				//Create the interest account
				MemberInterestPaymentAccountEntity interestAccount = new MemberInterestPaymentAccountEntity();
				interestAccount.setAmount(res.getDouble("ipa_amount"));
				interestAccount.setMemberAccountId(account.getId());
				interestAccount.setId(res.getLong("ipa_id"));
				
				//Create the sponsorship account
				MemberSponsorshipPaymentAccountEntity sponsorshipAccount = new MemberSponsorshipPaymentAccountEntity();
				sponsorshipAccount.setAmount(res.getDouble("spa_amount"));
				sponsorshipAccount.setMemberAccountId(account.getId());
				sponsorshipAccount.setId(res.getLong("spa_id"));
				
				member.setAccount(account);
				member.setInterestAccount(interestAccount);
				member.setSponsorshipAccount(sponsorshipAccount);
				
				return member;
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
	 * Find a member by phone and password
	 * @param obj MemberEntity object with phone and password pre-filled
	 * @return
	 */
	public boolean findByPhonePassword(MemberEntity obj) {
		String query = "SELECT ";
		query += "member.id as m_id, ";
		query += "member.lastname as m_lastname, ";
		query += "member.firstname as m_firstname, ";
		query += "member.grade as m_grade, ";
		query += "member.point as m_point, ";
		query += "member.email as m_email, ";
		query += "member.reference as m_reference, ";
		query += "member.referenced as m_referenced, ";
		query += "member_account.amount as a_amount, ";
		query += "member.created_at as m_created_at, ";
		query += "member.verified as m_verified, ";
		query += "member_account.transaction_code as a_transaction_code, ";
		query += "member_account.member_id as a_member_id, ";
		query += "ipa.amount as ipa_amount, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "ipa.id as ipa_id, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "spa.amount as spa_amount, "; //Table alias using MemberSsponsorshipPaymentAccountDaoRepository
		query += "spa.id as spa_id "; //Table alias using MemberSponsorshipPaymentAccountDaoRepository
		query += "FROM " + this.table + " ";
		query += "INNER JOIN member_account ";
		query += "ON member.id=member_account.member_id ";
		query += "INNER JOIN " + MemberInterestPaymentAccountDaoRepository.TABLE_NAME + " as ipa ";
		query += "ON ipa.member_account_id=member_account.id ";
		query += "INNER JOIN " + MemberSponsorshipPaymentAccountDaoRepository.TABLE_NAME + " as spa ";
		query += "ON spa.member_account_id=member_account.id ";
		query += "WHERE " ;
		query += " phone=? AND password =?";
		
		System.out.println("Member SELECT Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, obj.getPhone());
			stm.setString(2, obj.getPassword());
			
			
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			if(res != null && res.next()){
				//Update the object id here
				int id = res.getInt(1);
				System.out.println("Selected id ---> " + id + " " + res.getDouble("a_amount"));
				
				obj.setId( res.getLong("m_id") );
				obj.setEmail(res.getString( "m_" + TABLE_COLUMN_EMAIL));
				obj.setFirstname(res.getString("m_" + TABLE_COLUMN_FIRSTNAME));
				obj.setPoint(res.getInt("m_" + TABLE_COLUMN_POINT));
				obj.setGrade(MemberGrade.valueOf(res.getString("m_" + TABLE_COLUMN_GRADE)));
				obj.setLastname(res.getString("m_" + TABLE_COLUMN_LASTNAME));
				obj.setReference(res.getString("m_" + TABLE_COLUMN_REFERENCE));
				obj.setReferenced(res.getString("m_" + TABLE_COLUMN_REFERENCED));
				obj.setCreatedAt(res.getString("m_" + TABLE_COLUMN_CREATED_AT));
				obj.setVerified(res.getBoolean("m_" + TABLE_COLUMN_VERIFIED));
				
				
				//Create account for the user
				MemberAccountEntity account = new MemberAccountEntity();
				account.setMemberId(obj.getId());
				account.setAmount(res.getDouble("a_amount"));
				account.setTransacrionCode(res.getString("a_transaction_code"));
				
				//Create the interest account
				MemberInterestPaymentAccountEntity interestAccount = new MemberInterestPaymentAccountEntity();
				interestAccount.setAmount(res.getDouble("ipa_amount"));
				interestAccount.setMemberAccountId(account.getId());
				interestAccount.setId(res.getLong("ipa_id"));
				
				//Create the sponsorship account
				MemberSponsorshipPaymentAccountEntity sponsorshipAccount = new MemberSponsorshipPaymentAccountEntity();
				sponsorshipAccount.setAmount(res.getDouble("spa_amount"));
				sponsorshipAccount.setMemberAccountId(account.getId());
				sponsorshipAccount.setId(res.getLong("spa_id"));
				
				obj.setAccount(account);
				obj.setInterestAccount(interestAccount);
				obj.setSponsorshipAccount(sponsorshipAccount);
				
				
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
	
	
	/**
	 * Set the member cerified state to true
	 * @param memberId
	 * @return
	 */
	public boolean updateVerifiedtate(long memberId) {
		String query = "UPDATE " + this.table + " ";
		query += "SET verified=1 WHERE id=?";
		
		System.out.println("Member " + memberId + " verified state update Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setLong(1, memberId);
			
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
	
	
	/**
	 * Find a member by email and password
	 * @param obj MemberEntity object with phone and password pre-filled
	 * @return
	 */
	public boolean findByEmailPassword(MemberEntity obj) {
		String query = "SELECT ";
		query += "member.id as m_id, ";
		query += "member.lastname as m_lastname, ";
		query += "member.firstname as m_firstname, ";
		query += "member.grade as m_grade, ";
		query += "member.point as m_point, ";
		query += "member.phone as m_phone, ";
		query += "member.email as m_email, ";
		query += "member.reference as m_reference, ";
		query += "member.referenced as m_referenced, ";
		query += "member_account.amount as a_amount, ";
		query += "member.created_at as m_created_at, ";
		query += "member.verified as m_verified, ";
		query += "member_account.transaction_code as a_transaction_code, ";
		query += "member_account.member_id as a_member_id, ";
		query += "ipa.amount as ipa_amount, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "ipa.id as ipa_id, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "spa.amount as spa_amount, "; //Table alias using MemberSsponsorshipPaymentAccountDaoRepository
		query += "spa.id as spa_id "; //Table alias using MemberSponsorshipPaymentAccountDaoRepository
		query += "FROM " + this.table + " ";
		query += "INNER JOIN member_account ";
		query += "ON member.id=member_account.member_id ";
		query += "INNER JOIN " + MemberInterestPaymentAccountDaoRepository.TABLE_NAME + " as ipa ";
		query += "ON ipa.member_account_id=member_account.id ";
		query += "INNER JOIN " + MemberSponsorshipPaymentAccountDaoRepository.TABLE_NAME + " as spa ";
		query += "ON spa.member_account_id=member_account.id ";
		query += "WHERE " ;
		query += " member.email=? AND member.password =? AND member.verified=1";
		
		System.out.println("Member SELECT Query : " + query);
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
				System.out.println("Selected id ---> " + id + " " + res.getDouble("a_amount"));
				
				obj.setId( res.getLong("m_id") );
				obj.setEmail(res.getString( "m_" + TABLE_COLUMN_EMAIL));
				obj.setFirstname(res.getString("m_" + TABLE_COLUMN_FIRSTNAME));
				obj.setPoint(res.getInt("m_" + TABLE_COLUMN_POINT));
				obj.setPhone(res.getString("m_" + TABLE_COLUMN_PHONE));
				obj.setGrade(MemberGrade.valueOf(res.getString("m_" + TABLE_COLUMN_GRADE)));
				obj.setLastname(res.getString("m_" + TABLE_COLUMN_LASTNAME));
				obj.setReference(res.getString("m_" + TABLE_COLUMN_REFERENCE));
				obj.setReferenced(res.getString("m_" + TABLE_COLUMN_REFERENCED));
				obj.setCreatedAt(res.getString("m_" + TABLE_COLUMN_CREATED_AT));
				obj.setVerified(res.getBoolean("m_" + TABLE_COLUMN_VERIFIED));
				
				
				//Create account for the user
				MemberAccountEntity account = new MemberAccountEntity();
				account.setMemberId(obj.getId());
				account.setAmount(res.getDouble("a_amount"));
				account.setTransacrionCode(res.getString("a_transaction_code"));
				
				//Create the interest account
				MemberInterestPaymentAccountEntity interestAccount = new MemberInterestPaymentAccountEntity();
				interestAccount.setAmount(res.getDouble("ipa_amount"));
				interestAccount.setMemberAccountId(account.getId());
				interestAccount.setId(res.getLong("ipa_id"));
				
				//Create the sponsorship account
				MemberSponsorshipPaymentAccountEntity sponsorshipAccount = new MemberSponsorshipPaymentAccountEntity();
				sponsorshipAccount.setAmount(res.getDouble("spa_amount"));
				sponsorshipAccount.setMemberAccountId(account.getId());
				sponsorshipAccount.setId(res.getLong("spa_id"));
				
				obj.setAccount(account);
				obj.setInterestAccount(interestAccount);
				obj.setSponsorshipAccount(sponsorshipAccount);
				
				
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
	
	
	
	

	/**
	 * Find a member by phone and password
	 * @param reference the member reference used for registration
	 * @return
	 */
	public boolean findByReference(String reference) {
		String query = "SELECT ";
		query += "member.id as m_id ";
		/*query += "member.lastname as m_lastname, ";
		query += "member.firstname as m_firstname, ";
		query += "member.email as m_email, ";
		query += "member.grade as m_grade, ";
		query += "member.point as m_point, ";
		query += "member.reference as m_reference, ";
		query += "member.referenced as m_referenced, ";
		query += "member_account.amount as a_amount, ";
		query += "member.created_at as m_created_at, ";
		query += "member_account.transaction_code as a_transaction_code, ";
		query += "member_account.member_id as a_member_id, ";
		query += "ipa.amount as ipa_amount, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "ipa.id as ipa_id, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "FROM " + this.table + " ";
		query += "INNER JOIN member_account ";
		query += "ON member.id=member_account.member_id ";*/
		query += "FROM " + this.table + " ";
		query += "WHERE " ;
		query += " member.reference=? AND member.verified=1";
		//query += "INNER JOIN " + MemberInterestPaymentAccountDaoRepository.TABLE_NAME + " as ipa ";
		//query += "ON ipa.member_account_id=member_account.id ";
		
		System.out.println("Member SELECT by reference" + reference + "  Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, reference);
			
			
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			if(res != null && res.next()){
				//Update the object id here
				int id = res.getInt(1);
				System.out.println("Selected id ---> " + id);
				
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
	
	
	/**
	 * Find a member by email
	 * @param email the member email used for registration
	 * @return boolean true if the member exists with this email
	 */
	public boolean findByEmail(String email) {
		String query = "SELECT ";
		query += "member.id as m_id ";
		/*query += "member.lastname as m_lastname, ";
		query += "member.firstname as m_firstname, ";
		query += "member.email as m_email, ";
		query += "member.grade as m_grade, ";
		query += "member.point as m_point, ";
		query += "member.reference as m_reference, ";
		query += "member.referenced as m_referenced, ";
		query += "member_account.amount as a_amount, ";
		query += "member.created_at as m_created_at, ";
		query += "member_account.transaction_code as a_transaction_code, ";
		query += "member_account.member_id as a_member_id, ";
		query += "ipa.amount as ipa_amount, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "ipa.id as ipa_id, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "FROM " + this.table + " ";
		query += "INNER JOIN member_account ";
		query += "ON member.id=member_account.member_id ";*/
		query += "FROM " + this.table + " ";
		query += "WHERE " ;
		query += " member.email=? AND member.verified=1";
		//query += "INNER JOIN " + MemberInterestPaymentAccountDaoRepository.TABLE_NAME + " as ipa ";
		//query += "ON ipa.member_account_id=member_account.id ";
		
		System.out.println("Member SELECT by email  Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, email);
			
			
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			if(res != null && res.next()){
				//Update the object id here
				int id = res.getInt(1);
				System.out.println("Selected id ---> " + id);
				
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
	
	
	/**
	 * Find a member by firstname or username
	 * @param email the member username used for registration
	 * @return boolean true if the member exists with this email
	 */
	public boolean findByUsername(String firstname) {
		String query = "SELECT ";
		query += "member.id as m_id ";
		/*query += "member.lastname as m_lastname, ";
		query += "member.firstname as m_firstname, ";
		query += "member.email as m_email, ";
		query += "member.grade as m_grade, ";
		query += "member.point as m_point, ";
		query += "member.reference as m_reference, ";
		query += "member.referenced as m_referenced, ";
		query += "member_account.amount as a_amount, ";
		query += "member.created_at as m_created_at, ";
		query += "member_account.transaction_code as a_transaction_code, ";
		query += "member_account.member_id as a_member_id, ";
		query += "ipa.amount as ipa_amount, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "ipa.id as ipa_id, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "FROM " + this.table + " ";
		query += "INNER JOIN member_account ";
		query += "ON member.id=member_account.member_id ";*/
		query += "FROM " + this.table + " ";
		query += "WHERE " ;
		query += " member.firstname=? AND member.verified=1";
		//query += "INNER JOIN " + MemberInterestPaymentAccountDaoRepository.TABLE_NAME + " as ipa ";
		//query += "ON ipa.member_account_id=member_account.id ";
		
		System.out.println("Member SELECT by email  Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, firstname);
			
			
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			if(res != null && res.next()){
				//Update the object id here
				int id = res.getInt(1);
				System.out.println("Selected id ---> " + id);
				
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
	
	/**
	 * Find a member by phone
	 * @param phone the member phone used for registration
	 * @return boolean true if the member exists with this email
	 */
	public boolean findByPhone(String phone) {
		String query = "SELECT ";
		query += "member.id as m_id ";
		/*query += "member.lastname as m_lastname, ";
		query += "member.firstname as m_firstname, ";
		query += "member.email as m_email, ";
		query += "member.grade as m_grade, ";
		query += "member.point as m_point, ";
		query += "member.reference as m_reference, ";
		query += "member.referenced as m_referenced, ";
		query += "member_account.amount as a_amount, ";
		query += "member.created_at as m_created_at, ";
		query += "member_account.transaction_code as a_transaction_code, ";
		query += "member_account.member_id as a_member_id, ";
		query += "ipa.amount as ipa_amount, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "ipa.id as ipa_id, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "FROM " + this.table + " ";
		query += "INNER JOIN member_account ";
		query += "ON member.id=member_account.member_id ";*/
		query += "FROM " + this.table + " ";
		query += "WHERE " ;
		query += " member.phone=? AND member.verified=1";
		//query += "INNER JOIN " + MemberInterestPaymentAccountDaoRepository.TABLE_NAME + " as ipa ";
		//query += "ON ipa.member_account_id=member_account.id ";
		
		System.out.println("Member SELECT by phone  Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, phone);
			
			
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			if(res != null && res.next()){
				//Update the object id here
				int id = res.getInt(1);
				System.out.println("Selected id ---> " + id);
				
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
	
	
	
	
	/**
	 * Find the member by it account id
	 * 
	 * @param accountId
	 * @return
	 */
	public MemberEntity getMemberByAccountId(long accountId) {
		
		String query = "SELECT ";
		query += "member.id as m_id, ";
		query += "member.lastname as m_lastname, ";
		query += "member.firstname as m_firstname, ";
		query += "member.email as m_email, ";
		query += "member.phone as m_phone, ";
		query += "member.grade as m_grade, ";
		query += "member.point as m_point, ";
		query += "member.reference as m_reference, ";
		query += "member.referenced as m_referenced, ";
		query += "member_account.amount as a_amount, ";
		query += "member.created_at as m_created_at, ";
		query += "member.verified as m_verified, ";
		query += "member_account.transaction_code as a_transaction_code, ";
		query += "member_account.member_id as a_member_id, ";
		query += "ipa.amount as ipa_amount, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "ipa.id as ipa_id, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "spa.amount as spa_amount, "; //Table alias using MemberSsponsorshipPaymentAccountDaoRepository
		query += "spa.id as spa_id "; //Table alias using MemberSponsorshipPaymentAccountDaoRepository
		query += "FROM " + this.table + " ";
		query += "INNER JOIN member_account ";
		query += "ON member.id=member_account.member_id ";
		query += "INNER JOIN " + MemberInterestPaymentAccountDaoRepository.TABLE_NAME + " as ipa ";
		query += "ON ipa.member_account_id=member_account.id ";
		query += "INNER JOIN " + MemberSponsorshipPaymentAccountDaoRepository.TABLE_NAME + " as spa ";
		query += "ON spa.member_account_id=member_account.id ";
		query += "WHERE " ;
		query += "member_account.id=? AND member.verified=1";
		
		System.out.println("Member SELECT Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setLong(1, accountId );
			
			
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			if(res != null && res.next()){
				//Update the object id here
				int id = res.getInt(1);
				System.out.println("Selected id Login ---> " + id + " amount" + res.getDouble("a_amount"));
				
				
				MemberEntity member = new MemberEntity();
				
				member.setId( res.getLong("m_id") );
				member.setEmail(res.getString( "m_" + TABLE_COLUMN_EMAIL));
				member.setFirstname(res.getString("m_" + TABLE_COLUMN_FIRSTNAME));
				member.setLastname(res.getString("m_" + TABLE_COLUMN_LASTNAME));
				member.setReference(res.getString("m_" + TABLE_COLUMN_REFERENCE));
				member.setPoint(res.getInt("m_" + TABLE_COLUMN_POINT));
				member.setGrade(MemberGrade.valueOf(res.getString("m_" + TABLE_COLUMN_GRADE)));
				member.setPhone(res.getString("m_" + TABLE_COLUMN_PHONE));
				member.setReferenced(res.getString("m_" + TABLE_COLUMN_REFERENCED));
				member.setCreatedAt(res.getString("m_" + TABLE_COLUMN_CREATED_AT));
				member.setVerified(res.getBoolean("m_" + TABLE_COLUMN_VERIFIED));
				
				
				//Create account for the user
				MemberAccountEntity account = new MemberAccountEntity();
				account.setId(accountId);
				account.setMemberId(member.getId());
				account.setAmount(res.getDouble("a_amount"));
				System.out.println("Account amount " + account.getAmount());
				account.setTransacrionCode(res.getString("a_transaction_code"));
				
				//Create the interest account
				MemberInterestPaymentAccountEntity interestAccount = new MemberInterestPaymentAccountEntity();
				interestAccount.setAmount(res.getDouble("ipa_amount"));
				interestAccount.setMemberAccountId(account.getId());
				interestAccount.setId(res.getLong("ipa_id"));
				
				//Create the sponsorship account
				MemberSponsorshipPaymentAccountEntity sponsorshipAccount = new MemberSponsorshipPaymentAccountEntity();
				sponsorshipAccount.setAmount(res.getDouble("spa_amount"));
				sponsorshipAccount.setMemberAccountId(account.getId());
				sponsorshipAccount.setId(res.getLong("spa_id"));
				
				member.setAccount(account);
				member.setInterestAccount(interestAccount);
				member.setSponsorshipAccount(sponsorshipAccount);
				
				
				return member;
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
	 * Find the member by it reference
	 * 
	 * @param accountId
	 * @return
	 */
	public MemberEntity findMemberByReference(String reference) {
		String query = "SELECT ";
		query += "member.id as m_id, ";
		query += "member.lastname as m_lastname, ";
		query += "member.firstname as m_firstname, ";
		query += "member.email as m_email, ";
		query += "member.phone as m_phone, ";
		query += "member.grade as m_grade, ";
		query += "member.point as m_point, ";
		query += "member.reference as m_reference, ";
		query += "member.referenced as m_referenced, ";
		query += "member_account.amount as a_amount, ";
		query += "member.created_at as m_created_at, ";
		query += "member_account.id as a_id, ";
		query += "member_account.transaction_code as a_transaction_code, ";
		query += "member_account.member_id as a_member_id, ";
		query += "ipa.amount as ipa_amount, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "ipa.id as ipa_id, "; //Table alias using MemberInterestPaymentAccountDaoRepository
		query += "spa.amount as spa_amount, "; //Table alias using MemberSsponsorshipPaymentAccountDaoRepository
		query += "spa.id as spa_id "; //Table alias using MemberSponsorshipPaymentAccountDaoRepository
		query += "FROM " + this.table + " ";
		query += "INNER JOIN member_account ";
		query += "ON member.id=member_account.member_id ";
		query += "INNER JOIN " + MemberInterestPaymentAccountDaoRepository.TABLE_NAME + " as ipa ";
		query += "ON ipa.member_account_id=member_account.id ";
		query += "INNER JOIN " + MemberSponsorshipPaymentAccountDaoRepository.TABLE_NAME + " as spa ";
		query += "ON spa.member_account_id=member_account.id ";
		query += "WHERE " ;
		query += "member.reference=?";
		
		System.out.println("Member SELECT By refrence Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, reference );
			
			
			res =  stm.executeQuery();
			System.out.println( res.getFetchSize() );
			
			if(res != null && res.next()){
				//Update the object id here
				int id = res.getInt(1);
				System.out.println("Selected id Login ---> " + id + " amount" + res.getDouble("a_amount"));
				
				
				MemberEntity member = new MemberEntity();
				
				member.setId( res.getLong("m_id") );
				member.setEmail(res.getString( "m_" + TABLE_COLUMN_EMAIL));
				member.setFirstname(res.getString("m_" + TABLE_COLUMN_FIRSTNAME));
				member.setLastname(res.getString("m_" + TABLE_COLUMN_LASTNAME));
				member.setReference(res.getString("m_" + TABLE_COLUMN_REFERENCE));
				member.setPoint(res.getInt("m_" + TABLE_COLUMN_POINT));
				member.setGrade(MemberGrade.valueOf(res.getString("m_" + TABLE_COLUMN_GRADE)));
				member.setPhone(res.getString("m_" + TABLE_COLUMN_PHONE));
				member.setReferenced(res.getString("m_" + TABLE_COLUMN_REFERENCED));
				member.setCreatedAt(res.getString("m_" + TABLE_COLUMN_CREATED_AT));
				
				
				//Create account for the user
				MemberAccountEntity account = new MemberAccountEntity();
				account.setId(res.getLong("a_id"));
				account.setMemberId(member.getId());
				account.setAmount(res.getDouble("a_amount"));
				System.out.println("Account amount " + account.getAmount());
				account.setTransacrionCode(res.getString("a_transaction_code"));
				
				//Create the interest account
				MemberInterestPaymentAccountEntity interestAccount = new MemberInterestPaymentAccountEntity();
				interestAccount.setAmount(res.getDouble("ipa_amount"));
				interestAccount.setMemberAccountId(account.getId());
				interestAccount.setId(res.getLong("ipa_id"));
				
				//Create the sponsorship account
				MemberSponsorshipPaymentAccountEntity sponsorshipAccount = new MemberSponsorshipPaymentAccountEntity();
				sponsorshipAccount.setAmount(res.getDouble("spa_amount"));
				sponsorshipAccount.setMemberAccountId(account.getId());
				sponsorshipAccount.setId(res.getLong("spa_id"));
				
				member.setAccount(account);
				member.setInterestAccount(interestAccount);
				member.setSponsorshipAccount(sponsorshipAccount);
				
				
				return member;
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

	public boolean updatePassword(MemberEntity member) {
		String query = "UPDATE " + this.table + " ";
		query += "SET password=? ";
		query += "WHERE id=?";
		
		System.out.println("Member UPDATE password Query : " + query);
		PreparedStatement stm = null;
		ResultSet res = null;	
		
		
		
		try {
			
			stm = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, member.getPassword() );
			stm.setLong(2, member.getId());
			
			
			int rows =  stm.executeUpdate();
			res = stm.getGeneratedKeys();
			System.out.println( res.getFetchSize() );
			
			if(res != null && rows > 0){
				//Update the object id here
				System.out.println("Rows updated " + rows);
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
