package com.xrade.orm.dao.factory;

import java.sql.Connection;

import com.xrade.orm.DatabaseConnection;
import com.xrade.orm.dao.mysqlRepository.AnalysisDaoRepository;
import com.xrade.orm.dao.mysqlRepository.AnalysisDataDaoRepository;
import com.xrade.orm.dao.mysqlRepository.BackOfficerDaoRepository;
import com.xrade.orm.dao.mysqlRepository.MarketAnalysisDaoRepository;
import com.xrade.orm.dao.mysqlRepository.MarketOrderDaoRepository;
import com.xrade.orm.dao.mysqlRepository.MarketOrderProcessDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.MemberAccountDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.MemberAccountManagementRepository;
import com.xrade.orm.dao.mysqlRepository.member.MemberDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.MemberDepositRequestDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.MemberInterestPaymentAccountDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.MemberInterestPaymentDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.MemberInvestmentProfitMergeRequestDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.MemberInvestmentProfitWithdrawRequestDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.MemberRegisterVerificatorDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.MemberSponsorhipPaymentDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.MemberSponsorshipPaymentAccountDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.MemberSponsorshipProfitMergeRequestDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.MemberSponsorshipProfitWithdrawRequestDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.MemberWithdrawRequestDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.transaction.MemberCardTransactionDaoRepository;
import com.xrade.orm.dao.mysqlRepository.member.transaction.MemberPerfectMoneyTransactionDaoRepository;

public class MysqlDaoFactory {
	protected static final Connection connection = DatabaseConnection.getInstance();

    public static AnalysisDataDaoRepository getAnalysisDataRepository() {
        return new AnalysisDataDaoRepository(connection);
    }
    
    public static MarketAnalysisDaoRepository getMarketAnalysisRepository() {
        return new MarketAnalysisDaoRepository(connection);
    }
    
    public static MarketOrderDaoRepository getMarketOrderDaoRepository(){
    	return new MarketOrderDaoRepository(connection);
    }
    
    public static MarketOrderProcessDaoRepository getMarketOrderProcessDaoRepository(){
    	return new MarketOrderProcessDaoRepository(connection);
    }
    
    public static AnalysisDaoRepository getAnalysisDaoRepository(){
    	return new AnalysisDaoRepository(connection);
    }
    
    
    
    
    
    
    public static MemberDaoRepository getMemberDaoRepository(){
    	return new MemberDaoRepository(connection);
    }
    
    public static MemberRegisterVerificatorDaoRepository getMemberRegisterVerificatorDaoRepository(){
    	return new MemberRegisterVerificatorDaoRepository(connection);
    }
    
    public static MemberAccountDaoRepository getMemberAccountDaoRepository(){
    	return new MemberAccountDaoRepository(connection);
    }
    
    public static  MemberDepositRequestDaoRepository getMemberDepositRequestDaoRepository(){
    	return new  MemberDepositRequestDaoRepository(connection);
    }
    
    public static MemberWithdrawRequestDaoRepository getMemberWithdrawRequestDaoRepository(){
    	return new MemberWithdrawRequestDaoRepository(connection);
    }
    
    
    
    
    
    
    //Back Officer repository
    public static BackOfficerDaoRepository getBackOfficerDaoRepository(){
    	return new BackOfficerDaoRepository(connection);
    }
    
    
    
    
    
    //Member account management
    public static MemberAccountManagementRepository getMemberAccountManagementRepository() {
    	return new MemberAccountManagementRepository(connection);
    }
    
    public static MemberInterestPaymentDaoRepository getMemberInterestPaymentDaoRepository(){
    	return new MemberInterestPaymentDaoRepository(connection);
    }
    
    public static MemberSponsorhipPaymentDaoRepository getMemberSponsorhipPaymentDaoRepository(){
    	return new MemberSponsorhipPaymentDaoRepository(connection);
    }
    
    
    //Investment account
    public static MemberInterestPaymentAccountDaoRepository getMemberInterestPaymentAccountDaoRepository(){
    	return new MemberInterestPaymentAccountDaoRepository(connection);
    }
    
    public static MemberInvestmentProfitWithdrawRequestDaoRepository getMemberInvestmentProfitWithdrawRequestDaoRepository(){
    	return new MemberInvestmentProfitWithdrawRequestDaoRepository(connection);
    }
    
    public static  MemberInvestmentProfitMergeRequestDaoRepository getMemberInvestmentProfitMergeRequestDaoRepository(){
    	return new  MemberInvestmentProfitMergeRequestDaoRepository(connection);
    }
    
    //Sponsorship account
    public static  MemberSponsorshipPaymentAccountDaoRepository getMemberSponsorshipPaymentAccountDaoRepository(){
    	return new  MemberSponsorshipPaymentAccountDaoRepository(connection);
    }
    
    public static MemberSponsorshipProfitWithdrawRequestDaoRepository getMemberSponsorshipProfitWithdrawRequestDaoRepository(){
    	return new MemberSponsorshipProfitWithdrawRequestDaoRepository(connection);
    }
    
    public static MemberSponsorshipProfitMergeRequestDaoRepository getMemberSponsorshipProfitMergeRequestDaoRepository(){
    	return new MemberSponsorshipProfitMergeRequestDaoRepository(connection);
    }
    
    
    
    //Member Money transaction
    public static MemberCardTransactionDaoRepository getMemberCardTransactionDaoRepository(){
    	return new MemberCardTransactionDaoRepository(connection);
    }
    
    public static MemberPerfectMoneyTransactionDaoRepository getMemberPerfectMoneyTransactionDaoRepository(){
    	return new MemberPerfectMoneyTransactionDaoRepository(connection);
    }
}
