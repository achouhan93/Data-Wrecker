/**
 * 
 */
package com.data.datedatatype.repositoryimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.datedatatype.model.ColumnStatisticsModel;
import com.data.datedatatype.model.DimensionsResult;
import com.data.datedatatype.model.ProfilerInfo;
import com.data.datedatatype.repository.ProfilerInfoRepository;

/**
 * @author paneesh
 *
 */

@Service
@Transactional 
public class ProfilerInfoRepositoryImpl {

	@Autowired
	private ProfilerInfoRepository profilerInfoRepository;	
	
	
	public List<ColumnStatisticsModel> getAll(){
		return profilerInfoRepository.findAll();
	}
	
	public String givemeString() {
		return "This is the String";
	}
	
	public void create(ColumnStatisticsModel columnStats) {
		profilerInfoRepository.save(columnStats);
	}

	
}
