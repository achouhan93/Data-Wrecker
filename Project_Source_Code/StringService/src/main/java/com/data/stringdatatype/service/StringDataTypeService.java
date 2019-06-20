package com.data.stringdatatype.service;

import com.data.stringdatatype.model.ColumnStatisticsModel;

public interface StringDataTypeService {

	public boolean NullCheck(ColumnStatisticsModel profilerInfo);
	
	public boolean ConsistencyCheck(ColumnStatisticsModel profilerInfo);
	
	public boolean ValidityCheck(ColumnStatisticsModel profilerInfo);
	
	public boolean AccuracyCheck(ColumnStatisticsModel profilerInfo);
	
}
