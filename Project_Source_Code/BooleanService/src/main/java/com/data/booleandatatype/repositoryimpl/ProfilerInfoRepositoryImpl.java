package com.data.booleandatatype.repositoryimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.booleandatatype.model.ProfilerInfo;
import com.data.booleandatatype.repository.ProfilerInfoRepository;


@Service
//@Transactional
public class ProfilerInfoRepositoryImpl {

	@Autowired
	private ProfilerInfoRepository profilerInfoRepository;	
	
	public List<ProfilerInfo> getAll(){
		return profilerInfoRepository.findAll();
	}
	
	public String givemeString() {
		return "This is the String";
	}
	
	public void create(ProfilerInfo profilerInfo) {
		profilerInfoRepository.save(profilerInfo);
	}
	
}
