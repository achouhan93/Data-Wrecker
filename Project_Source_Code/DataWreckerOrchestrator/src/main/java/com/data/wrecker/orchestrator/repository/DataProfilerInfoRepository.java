package com.data.wrecker.orchestrator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.wrecker.orchestrator.entity.DataProfilerInfo;


public interface DataProfilerInfoRepository extends MongoRepository<DataProfilerInfo, String> {

}
