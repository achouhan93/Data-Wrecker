package com.data.columnStatistics.dao;

import java.util.List;

public interface ColumnStatisticsDao {
	@SuppressWarnings("rawtypes")
	public List<?> getColumnValues(String columnName);
}
