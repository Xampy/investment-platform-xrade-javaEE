package com.xrade.service.member;

import com.xrade.entity.MemberAccountInterestPaymentEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.orm.dao.factory.MysqlDaoFactory;

public class MemberInterestPaymentService {
	
	

	public MemberAccountInterestPaymentEntity[] getLatestInterestPaymentByMember(MemberEntity member) {
		return MysqlDaoFactory.getMemberInterestPaymentDaoRepository().findAllByMember(member);
		
	}
	
}
