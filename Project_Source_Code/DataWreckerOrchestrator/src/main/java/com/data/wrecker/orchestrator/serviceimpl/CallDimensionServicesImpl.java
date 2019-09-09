package com.data.wrecker.orchestrator.serviceimpl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.wrecker.orchestrator.service.CallDimensionServices;

@Service
@Transactional
public class CallDimensionServicesImpl implements CallDimensionServices{

	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public String callCompletenessService(List<String> objectIds, String colName, String collectionName) {
		// TODO Auto-generated method stub
		LOGGER.info("Completeness Service Called for column "+colName);
		return null;
	}

	@Override
	public String callUniquenessService(List<String> objectIds, String colName, String collectionName) {
		// TODO Auto-generated method stub
		LOGGER.info("Uniqueness Service Called for column "+colName);
		return null;
	}

	@Override
	public String callConsistencyService(List<String> objectIds, String colName, String collectionName) {
		// TODO Auto-generated method stub
		LOGGER.info("Consistency Service Called for column "+colName);
		return null;
	}

	@Override
	public String callAccuracyServcie(List<String> objectIds, String colName, String collectionName) {
		// TODO Auto-generated method stub
		LOGGER.info("Accuracy Service Called for column "+colName);
		return null;
	}

	@Override
	public String callValidityServcie(List<String> objectIds, String colName, String collectionName) {
		// TODO Auto-generated method stub
		LOGGER.info("Validity Service Called for column "+colName);
		return null;
	}
	
	
}
