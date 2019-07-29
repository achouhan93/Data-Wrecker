package com.data.decimaldatatypeservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.decimaldatatypeservice.model.DataProfilerInfo;

public interface DecimalDataTypeServiceRepository extends MongoRepository<DataProfilerInfo, String> {

}
