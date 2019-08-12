package com.data.wrecker.accuracyDimension.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.wrecker.accuracyDimension.model.DataProfilerInfo;


public interface DataProfilerInfoRepo extends MongoRepository<DataProfilerInfo, String>{

}
