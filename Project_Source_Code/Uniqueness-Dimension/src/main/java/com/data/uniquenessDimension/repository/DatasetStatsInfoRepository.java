package com.data.uniquenessDimension.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.uniquenessDimension.model.DataProfilerInfo;


public interface DatasetStatsInfoRepository extends MongoRepository<DataProfilerInfo, String>{

}
