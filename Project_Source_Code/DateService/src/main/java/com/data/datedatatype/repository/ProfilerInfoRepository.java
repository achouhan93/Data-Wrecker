package com.data.datedatatype.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.data.datedatatype.model.ProfilerInfo;

@Repository
public interface ProfilerInfoRepository extends MongoRepository<ProfilerInfo, String>{

	// List<ProfilerInfo> findByprofilerId(final int profilerId);	
	
}
