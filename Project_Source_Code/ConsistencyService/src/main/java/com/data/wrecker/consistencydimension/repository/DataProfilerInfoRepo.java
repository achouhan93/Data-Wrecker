package com.data.wrecker.consistencydimension.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.wrecker.consistencydimension.model.DataProfilerInfo;


public interface DataProfilerInfoRepo extends MongoRepository<DataProfilerInfo, String>{

}
