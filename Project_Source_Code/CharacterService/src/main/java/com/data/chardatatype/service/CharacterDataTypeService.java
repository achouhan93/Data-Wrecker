package com.data.chardatatype.service;

import com.data.chardatatype.model.DatasetStats;
import com.data.chardatatype.model.Dimensions;

public interface CharacterDataTypeService {

	public Dimensions NullCheck(DatasetStats datasetStats, int wreckingPercentage);
	
	public Dimensions ConsistencyCheck(DatasetStats datasetStats, int wreckingPercentage);
	
	public Dimensions ValidityCheck(DatasetStats datasetStats, int wreckingPercentage);
	
	public Dimensions AccuracyCheck(DatasetStats datasetStats, int wreckingPercentage);
	
}
