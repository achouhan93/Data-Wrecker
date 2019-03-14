package com.data.columnStatistics.dao;

import java.util.List;

public interface ColumnStatisticsDao {
	public List<?> getColumnValues(String columnName);
}
