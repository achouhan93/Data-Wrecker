package com.data.booleandatatype.service;

import com.data.booleandatatype.model.DatasetStats;
import com.data.booleandatatype.model.Dimensions;

public interface BooleanDataTypeService {
	

	public Dimensions NullCheck(DatasetStats datasetStats, int wreckingPercentage);
	
	public Dimensions ConsistencyCheck(DatasetStats datasetStats, int wreckingPercentage);
	
	public Dimensions ValidityCheck(DatasetStats datasetStats, int wreckingPercentage);
	
	public Dimensions AccuracyCheck(DatasetStats datasetStats, int wreckingPercentage);
	
	
	
}
