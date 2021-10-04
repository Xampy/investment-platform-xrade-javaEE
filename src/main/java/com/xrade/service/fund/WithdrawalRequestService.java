package com.xrade.service.fund;

import com.xrade.entity.MemberWithdrawRequestEntity;
import com.xrade.orm.dao.factory.MysqlDaoFactory;

public class WithdrawalRequestService {
	
	public MemberWithdrawRequestEntity[] getUnfilledRequests(){
		return MysqlDaoFactory.getMemberWithdrawRequestDaoRepository().getUnfilled(0, 0);
	}
	
	public MemberWithdrawRequestEntity getWithdrawRequestById(long withdrawRequestId) {
		return MysqlDaoFactory.getMemberWithdrawRequestDaoRepository().find((int) withdrawRequestId);
	}

}
