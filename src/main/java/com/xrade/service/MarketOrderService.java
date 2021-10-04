package com.xrade.service;

import com.xrade.orm.dao.factory.MysqlDaoFactory;

public class MarketOrderService {
	
	public long countOrderBy(long marketAnalysisId){
		return MysqlDaoFactory.getMarketOrderDaoRepository().countOrderBy(marketAnalysisId);
	}
	
	public int getMaxAmountOrderBy(long marketAnalysisId){
		return MysqlDaoFactory.getMarketOrderDaoRepository().getMaxAmountOrderBy(marketAnalysisId);
	}
}
