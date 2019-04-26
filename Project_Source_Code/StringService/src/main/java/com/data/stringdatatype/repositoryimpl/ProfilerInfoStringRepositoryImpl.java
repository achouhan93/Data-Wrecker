package com.data.stringdatatype.repositoryimpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.data.stringdatatype.model.ProfilerInfoString;
import com.data.stringdatatype.repository.ProfilerInfoStringRepository;

public class ProfilerInfoStringRepositoryImpl {

	@Autowired
	private ProfilerInfoStringRepository profilerInfoRepository;

	
	public void create(ProfilerInfoString profilerInfo) {
		profilerInfoRepository.save(profilerInfo);
	}
}
