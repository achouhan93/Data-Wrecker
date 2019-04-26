package com.data.stringdatatype.service;

import com.data.stringdatatype.model.ProfilerInfoString;

public interface StringDataTypeService {

	public boolean NullCheck(ProfilerInfoString profilerInfo);
	
	public boolean ConsistencyCheck(ProfilerInfoString profilerInfo);
	
	public boolean ValidityCheck(ProfilerInfoString profilerInfo);
	
	public boolean AccuracyCheck(ProfilerInfoString profilerInfo);
	
}
