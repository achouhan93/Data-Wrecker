package com.data.datedatatype.service;

import com.data.datedatatype.model.ColumnStatisticsModel;
import com.data.datedatatype.model.ProfilerInfo;

public interface DateDataTypeService {
	
	public boolean NullCheck(ColumnStatisticsModel profilerInfo);
	
	public boolean ConsistencyCheck(ColumnStatisticsModel profilerInfo);
	
	public boolean ValidityCheck(ColumnStatisticsModel profilerInfo);
	
	public boolean AccuracyCheck(ColumnStatisticsModel profilerInfo);
	
	
}
