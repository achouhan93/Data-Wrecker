package com.data.chardatatype.service;

import com.data.chardatatype.model.DatasetStats;
import com.data.chardatatype.model.Dimensions;

public interface CharacterDataTypeService {

public Dimensions NullCheck(DatasetStats datasetStats);
	
	public Dimensions ConsistencyCheck(DatasetStats datasetStats);
	
	public Dimensions ValidityCheck(DatasetStats datasetStats);
	
	public Dimensions AccuracyCheck(DatasetStats datasetStats);
	
	
}
