package com.data.wrecker.consistencydimension.service;

import com.data.wrecker.consistencydimension.model.DataProfilerInfo;

public interface WaysofConsistencyToBeApplied {

	public String interchangeColumnValues(String colValue,DataProfilerInfo datasetProfiler,String columnName,String fileName);
	
	public String changeItIntoUpperCase(String colValue);
	
	public String reverseCase(String colValue);
	
	public String changeItIntoLowerCase(String colValue);
	
	public String changeItIntoUpperLower(String colValue);
	
	public String changeDateFormat(String colValue);
	
	public String affectBooleanValues(boolean colValue);
	
	public int affectNumbers(int colValue);
	
	public String affectCurrencyValues(String colValue);
		
	public String convertIntegerIntoBinary(int colValue);
	
	public String consistencyForCharacters(String colValue);
	
}
