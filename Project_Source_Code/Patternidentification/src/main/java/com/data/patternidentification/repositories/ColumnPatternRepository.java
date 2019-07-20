package com.data.patternidentification.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.patternidentification.model.DataProfilerInfo;

public interface ColumnPatternRepository extends MongoRepository<DataProfilerInfo,String> {

}
