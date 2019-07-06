package com.data.booleandatatype.service;

import com.data.booleandatatype.model.DatasetStats;
import com.data.booleandatatype.model.Dimensions;

public interface BooleanDataTypeService {
	
public Dimensions NullCheck(DatasetStats datasetStats);
	
	public Dimensions ConsistencyCheck(DatasetStats datasetStats);
	
	public Dimensions ValidityCheck(DatasetStats datasetStats);
	
	public Dimensions AccuracyCheck(DatasetStats datasetStats);
	
	
}
