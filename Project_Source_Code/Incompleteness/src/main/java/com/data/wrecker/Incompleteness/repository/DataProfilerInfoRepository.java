package com.data.wrecker.Incompleteness.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.wrecker.Incompleteness.model.DataProfilerInfo;



public interface DataProfilerInfoRepository extends MongoRepository<DataProfilerInfo, String> {

}
