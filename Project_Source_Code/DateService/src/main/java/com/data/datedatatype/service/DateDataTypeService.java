package com.data.datedatatype.service;

import com.data.datedatatype.model.DatasetStats;
import com.data.datedatatype.model.Dimensions;

public interface DateDataTypeService {
	
	public Dimensions NullCheck(DatasetStats datasetStats);
	
	public Dimensions ConsistencyCheck(DatasetStats datasetStats);
	
	public Dimensions ValidityCheck(DatasetStats datasetStats);
	
	public Dimensions AccuracyCheck(DatasetStats datasetStats);
	
	
}
