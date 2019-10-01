package com.data.datawreakerinterface.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.datawreakerinterface.model.DataProfilerInfo;



public interface DataProfilerInfoRepository extends MongoRepository<DataProfilerInfo, String> {

}
