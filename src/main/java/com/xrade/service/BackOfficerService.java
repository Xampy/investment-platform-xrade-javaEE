package com.xrade.service;

import java.util.Optional;

import com.xrade.entity.BackOfficerEntity;
import com.xrade.orm.dao.factory.MysqlDaoFactory;

public class BackOfficerService {
	
	public Optional<BackOfficerEntity> getUser(BackOfficerEntity user){
		boolean got = MysqlDaoFactory.getBackOfficerDaoRepository().findByEmailPassword(user);
		System.out.println(got);
		if( got ){
			return Optional.of(user);
		}
		
		return Optional.empty();
	}
}
