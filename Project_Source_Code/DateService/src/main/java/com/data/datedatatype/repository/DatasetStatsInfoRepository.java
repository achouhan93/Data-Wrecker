package com.data.datedatatype.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.datedatatype.model.DataProfilerInfo;

public interface DatasetStatsInfoRepository extends MongoRepository<DataProfilerInfo, String>{
}
