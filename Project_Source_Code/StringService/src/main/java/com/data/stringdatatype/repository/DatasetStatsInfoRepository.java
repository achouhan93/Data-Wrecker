package com.data.stringdatatype.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.stringdatatype.model.DataProfilerInfo;

public interface DatasetStatsInfoRepository extends MongoRepository<DataProfilerInfo, String>{

}
