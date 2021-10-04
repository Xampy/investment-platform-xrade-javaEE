package com.xrade.service;

import com.xrade.entity.AnalysisDataEntity;
import com.xrade.orm.dao.factory.MysqlDaoFactory;

public class AnalysisDataService {
	
	public void saveAnalysisData(AnalysisDataEntity analysis){
		MysqlDaoFactory.getAnalysisDataRepository().create(analysis);
	}

	public AnalysisDataEntity[] getAnalysis() {
		return MysqlDaoFactory.getAnalysisDataRepository().findAll(0, 0);
	}

}
