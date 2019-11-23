package com.data.datedatatype.service;

import com.data.datedatatype.model.DatasetStats;
import com.data.datedatatype.model.Dimensions;

public interface DateDataTypeService {
	
	public Dimensions NullCheck(DatasetStats datasetStats, int wreckingPercentage);
	
	public Dimensions ConsistencyCheck(DatasetStats datasetStats, int wreckingPercentage);
	
	public Dimensions ValidityCheck(DatasetStats datasetStats, int wreckingPercentage);
	
	public Dimensions AccuracyCheck(DatasetStats datasetStats, int wreckingPercentage);
	
	public Dimensions UniquenessCheck(DatasetStats datasetStats, int avgWreckingCount);
	
	
}
