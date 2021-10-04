package com.xrade.service;

import com.xrade.entity.AnalysisDataEntity;
import com.xrade.entity.AnalysisEntity;
import com.xrade.entity.MarketAnalysisEntity;
import com.xrade.orm.dao.factory.MysqlDaoFactory;

public class AnalysisService {
	
	
	
	public AnalysisEntity[] getUncompletedAnalysis(){
		return MysqlDaoFactory.getAnalysisDaoRepository().findAllWithoutMarketAnalysis(0, 0);
	}
	
	public AnalysisEntity[] getCompletedAnalysis() {
		return MysqlDaoFactory.getAnalysisDaoRepository().findAllWithMarketAnalysis(0, 0);
	}

	public AnalysisDataEntity getAnalysisById(long analysisId) {		
		return MysqlDaoFactory.getAnalysisDataRepository().find(analysisId);
	}

	public void saveMarketAnalysis(MarketAnalysisEntity marketData) {
		//Need to be surround with try and catch
		MysqlDaoFactory.getMarketAnalysisRepository().create(marketData);
		
	}

	public AnalysisEntity getFullAnalysisById(long analysisId) {
		return MysqlDaoFactory.getAnalysisDaoRepository().find((int) analysisId);
	}

	
	
}
