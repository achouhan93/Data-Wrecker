package com.data.wrecker.validityDimension.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.wrecker.validityDimension.model.DataProfilerInfo;


public interface DataProfilerInfoRepo extends MongoRepository<DataProfilerInfo, String>{

}
