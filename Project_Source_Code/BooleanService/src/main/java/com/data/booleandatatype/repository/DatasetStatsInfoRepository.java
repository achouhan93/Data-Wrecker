package com.data.booleandatatype.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.booleandatatype.model.DataProfilerInfo;



public interface DatasetStatsInfoRepository  extends MongoRepository<DataProfilerInfo, String>{

}
