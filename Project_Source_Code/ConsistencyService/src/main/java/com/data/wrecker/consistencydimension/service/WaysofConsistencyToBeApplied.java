package com.data.wrecker.consistencydimension.service;

import com.data.wrecker.consistencydimension.model.DataProfilerInfo;
import com.data.wrecker.consistencydimension.model.DatasetStats;

public interface WaysofConsistencyToBeApplied {

	public String interchangeColumnValues(String colValue,DataProfilerInfo datasetProfiler,String columnName,String fileName);
	
	public String changeItIntoUpperCase(String colValue);
	
	public String reverseCase(String colValue);
	
	public String changeItIntoLowerCase(String colValue);
	
	public String changeItIntoUpperLower(String colValue);
	
	public String changeDateFormat(String colValue, DatasetStats datasetStats);
	
	public String affectBooleanValues(boolean colValue);
	
	public String convertToFloat(int colValue);
	
	public String affectCurrencyValues(String colValue);
		
	public String convertIntegerIntoBinary(int colValue);
	
	public String consistencyForCharacters(String colValue);
	
}
