package com.data.stringdatatype.service;

import com.data.stringdatatype.model.DatasetStats;
import com.data.stringdatatype.model.Dimensions;

public interface StringDataTypeService {

	public Dimensions NullCheck(DatasetStats datasetStats, int wreckingPercentage, int Colcount);
	
	public Dimensions ConsistencyCheck(DatasetStats datasetStats, int wreckingPercentage, int Colcount);
	
	public Dimensions ValidityCheck(DatasetStats datasetStats, int wreckingPercentage, int Colcount);
	
	public Dimensions AccuracyCheck(DatasetStats datasetStats, int wreckingPercentage, int Colcount);
	
	public Dimensions UniquenessCheck(DatasetStats datasetStats, int avgWreckingCount, int Colcount);
	
}
