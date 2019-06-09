package com.data.columnStatistics.dao;

import java.util.List;

import com.data.columnStatistics.model.ColumnStatisticsModel;

public interface ColumnStatisticsDaoMongo {
	@SuppressWarnings("rawtypes")
	public List<String> getColumnValues(String dbName, String collectionName,String columnName);

	public void saveColumnStatistics(ColumnStatisticsModel columnStatisticsModel, String dbName, String columnName, String columnName2);
}
