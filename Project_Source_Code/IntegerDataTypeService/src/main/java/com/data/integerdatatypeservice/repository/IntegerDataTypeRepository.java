package com.data.integerdatatypeservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.integerdatatypeservice.model.DataProfilerInfo;


public interface IntegerDataTypeRepository extends MongoRepository<DataProfilerInfo, String>{

}
