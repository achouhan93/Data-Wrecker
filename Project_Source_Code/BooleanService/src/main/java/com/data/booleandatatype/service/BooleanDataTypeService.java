package com.data.booleandatatype.service;

import com.data.booleandatatype.model.ProfilerInfo;

public interface BooleanDataTypeService {
	
	public boolean NullCheck(ProfilerInfo profilerInfo);
	
	public boolean ConsistencyCheck(ProfilerInfo profilerInfo);
	
	public boolean ValidityCheck(ProfilerInfo profilerInfo);
	
	public boolean AccuracyCheck(ProfilerInfo profilerInfo);
	
}
