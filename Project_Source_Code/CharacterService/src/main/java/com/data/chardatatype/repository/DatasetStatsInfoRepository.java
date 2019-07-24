package com.data.chardatatype.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.chardatatype.model.DataProfilerInfo;

public interface DatasetStatsInfoRepository  extends MongoRepository<DataProfilerInfo, String>{

}
