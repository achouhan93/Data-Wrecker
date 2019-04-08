/**
 * 
 */
package com.data.datedatatype.repositoryimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.datedatatype.model.ProfilerInfo;
import com.data.datedatatype.repository.ProfilerInfoRepository;

/**
 * @author paneesh
 *
 */

@Service
//@Transactional 
public class ProfilerInfoRepositoryImpl{

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
