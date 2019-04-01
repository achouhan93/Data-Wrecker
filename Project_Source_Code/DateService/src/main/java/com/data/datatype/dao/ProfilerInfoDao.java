/**
 * 
 */
package com.data.datatype.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.datedatatype.model.ProfilerInfo;
import com.data.datedatatype.repository.ProfilerInfoRepository;

/**
 * @author paneesh
 *
 */

@Service
public class ProfilerInfoDao {


	@Autowired
	private ProfilerInfoRepository profilerInfoRepository;	
	
	public List<ProfilerInfo> getAll(){
		return profilerInfoRepository.findAll();
	}
}
