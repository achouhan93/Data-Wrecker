package com.data.wrecker.orchestrator.service;

import com.data.wrecker.orchestrator.entity.DataProfilerInfo;

public interface GetProfilerInfoFromServices {

	public DataProfilerInfo callPatternIdentificationService(String filename);
	
	public DataProfilerInfo callColumnDatatypePredictionService();
	
	public DataProfilerInfo callColumnStatisticsService();
	
	
	
}
