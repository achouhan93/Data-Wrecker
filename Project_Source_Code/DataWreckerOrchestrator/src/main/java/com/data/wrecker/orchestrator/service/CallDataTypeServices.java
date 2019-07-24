package com.data.wrecker.orchestrator.service;

public interface CallDataTypeServices {

	public String callBooleanService(String fileName, int wreckingPercentage);
	
	public String callCharacterService(String fileName,int wreckingPercentage);
	
	public String callDateService(String fileName,int wreckingPercentage);
	
	public String callStringService(String fileName);
	
	
}
