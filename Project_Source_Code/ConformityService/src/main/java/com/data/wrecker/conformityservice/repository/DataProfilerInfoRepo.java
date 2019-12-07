package com.data.wrecker.conformityservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.wrecker.conformitydimension.model.DataProfilerInfo;


public interface DataProfilerInfoRepo extends MongoRepository<DataProfilerInfo, String>{

}
