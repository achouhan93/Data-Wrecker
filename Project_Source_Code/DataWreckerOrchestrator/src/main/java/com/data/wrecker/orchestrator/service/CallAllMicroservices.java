package com.data.wrecker.orchestrator.service;

public interface CallAllMicroservices {

	public String callDataprofilingServices(String fileName, int wreckPercentage);
	public String callAllDimensionServices(String collectionName, int wreckingPercentage);

	
}
