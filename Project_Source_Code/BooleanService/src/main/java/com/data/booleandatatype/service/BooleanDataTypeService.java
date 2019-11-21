package com.data.booleandatatype.service;

import com.data.booleandatatype.model.DatasetStats;
import com.data.booleandatatype.model.Dimensions;

public interface BooleanDataTypeService {
	

	public Dimensions NullCheck(DatasetStats datasetStats, int avgWreckingCount);
	
	public Dimensions ConsistencyCheck(DatasetStats datasetStats, int avgWreckingCount);
	
	public Dimensions ValidityCheck(DatasetStats datasetStats, int avgWreckingCount);
	
	public Dimensions AccuracyCheck(DatasetStats datasetStats, int avgWreckingCount);
	
	public Dimensions UniquenessCheck(DatasetStats datasetStats, int avgWreckingCount);
	
}
