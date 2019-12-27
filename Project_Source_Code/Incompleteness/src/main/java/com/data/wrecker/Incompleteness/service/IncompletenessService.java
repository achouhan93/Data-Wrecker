package com.data.wrecker.Incompleteness.service;

import java.util.List;

import com.data.wrecker.Incompleteness.model.ColumnStats;
import com.data.wrecker.Incompleteness.model.DatasetStats;

public interface IncompletenessService {

	public String removeValues(String collectionName, String columnName, List<String> wreckingIdsForDimension);
	
	public String removeValuesForDecimalAndInteger(String collectionName, String columnName, int wreckingCount, DatasetStats datasetStats);
	
	//public String removeValuesInteger(String collectionName, String columnName, int wreckingCount, ColumnStats colStats);
	
	public String removeValuesBoolean(String collectionName, String columnName, int wreckingCount, ColumnStats colStats);
	
	
}
