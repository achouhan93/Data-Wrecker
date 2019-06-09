package com.data.columnStatistics.service;

public interface ColumnStatisticsService {
	
	public String getColumnStatistics(String dbName, String collectionName, String columnName, String columnDataType, String dateFormat, String booleanTrueValue, String booleanFalseValue);
}
