package com.data.wrecker.orchestrator.service;

public interface CallAllMicroservices {

	public String callDataprofilingServices(String fileName);
	public String callDimensionServices(String collectionName, int wreckingPercentage);
	
}
