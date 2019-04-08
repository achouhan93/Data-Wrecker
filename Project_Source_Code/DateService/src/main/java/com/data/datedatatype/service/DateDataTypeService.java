package com.data.datedatatype.service;

import com.data.datedatatype.model.ProfilerInfo;

public interface DateDataTypeService {
	
	public boolean NullCheck(ProfilerInfo profilerInfo);
	
	public boolean ConsistencyCheck(ProfilerInfo profilerInfo);
	
	public boolean ValidityCheck(ProfilerInfo profilerInfo);
	
	public boolean AccuracyCheck(ProfilerInfo profilerInfo);
	
	
}
