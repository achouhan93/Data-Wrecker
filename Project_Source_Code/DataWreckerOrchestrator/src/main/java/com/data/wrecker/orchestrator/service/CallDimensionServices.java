package com.data.wrecker.orchestrator.service;

import java.util.List;

public interface CallDimensionServices {

	public String callCompletenessService(List<Integer> objectIds, String colName, String collectionName);
	
	public String callUniquenessService(List<Integer> objectIds, String colName, String collectionName);
	
	public String callConsistencyService(List<Integer> objectIds, String colName, String collectionName);
	
	public String callAccuracyServcie(List<Integer> objectIds, String colName, String collectionName);
	
	public String callValidityServcie(List<Integer> objectIds, String colName, String collectionName);
	
}

