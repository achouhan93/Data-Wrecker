package com.data.wrecker.orchestrator.service;

import com.data.wrecker.orchestrator.entity.DatasetDetails;

public interface GetProfilerInfoFromServices {

	public DatasetDetails callPatternIdentificationService(String filename);
	
	public String callColumnDatatypePredictionService(String fileName);
	
	public String callColumnStatisticsService(String filename);
	
	public String callWreckedDataEvaluatorService(String fileName);
	
	public String callMultiColumnStatisticsService(String fileName);
	
	
}
