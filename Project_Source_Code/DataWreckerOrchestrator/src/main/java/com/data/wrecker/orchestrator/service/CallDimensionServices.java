package com.data.wrecker.orchestrator.service;

import java.util.List;

public interface CallDimensionServices {

	public String callCompletenessService(List<String> objectIds, String colName, String collectionName);
	
	public String callUniquenessService(List<String> objectIds, String colName, String collectionName);
	
	public String callConsistencyService(List<String> objectIds, String colName, String collectionName);
	
	public String callAccuracyServcie(List<String> objectIds, String colName, String collectionName);
	
	public String callValidityServcie(List<String> objectIds, String colName, String collectionName);
	
}

