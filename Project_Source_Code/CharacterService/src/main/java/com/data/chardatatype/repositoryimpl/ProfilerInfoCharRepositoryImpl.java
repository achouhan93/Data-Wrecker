package com.data.chardatatype.repositoryimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import com.data.chardatatype.model.ProfilerInfoChar;
import com.data.chardatatype.repository.ProfilerInfoCharRepository;



public class ProfilerInfoCharRepositoryImpl {

	@Autowired
	private ProfilerInfoCharRepository profilerInfoRepository;
	
	
	public List<ProfilerInfoChar> getAll(){
		return profilerInfoRepository.findAll();
	}
	
	public String givemeString() {
		return "This is the String";
	}
	
	public void create(ProfilerInfoChar profilerInfo) {
		profilerInfoRepository.save(profilerInfo);
	}
	
	
}
