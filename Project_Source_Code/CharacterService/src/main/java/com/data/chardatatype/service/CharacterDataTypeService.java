package com.data.chardatatype.service;

import com.data.chardatatype.model.ProfilerInfoChar;

public interface CharacterDataTypeService {

public boolean NullCheck(ProfilerInfoChar profilerInfo);
	
	public boolean ConsistencyCheck(ProfilerInfoChar profilerInfo);
	
	public boolean ValidityCheck(ProfilerInfoChar profilerInfo);
	
	public boolean AccuracyCheck(ProfilerInfoChar profilerInfo);
	
	
}
