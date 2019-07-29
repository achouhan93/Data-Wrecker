package com.data.wrecker.orchestrator.service;

import com.data.wrecker.orchestrator.entity.DataProfilerInfo;

public interface GetProfilerInfoFromServices {

	public DataProfilerInfo callPatternIdentificationService(String filename);
	
	public String callColumnDatatypePredictionService(String fileName);
	
	public String callColumnStatisticsService(String filename);
	
	
	
}
