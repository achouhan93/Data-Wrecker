package com.data.wrecker.booleandatatype.service;

import com.data.wrecker.booleandatatype.bean.ProfilerInfo;

public interface BooleanDataTypeInterface {
	
	public boolean NullCheck(ProfilerInfo profilerInfo);
	
	public boolean ConsistencyCheck(ProfilerInfo profilerInfo);
	
	public boolean ValidityCheck(ProfilerInfo profilerInfo);
	
	public boolean AccuracyCheck(ProfilerInfo profilerInfo);
	
	
}
