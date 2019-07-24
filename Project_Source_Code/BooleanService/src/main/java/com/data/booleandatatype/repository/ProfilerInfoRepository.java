package com.data.booleandatatype.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.data.booleandatatype.model.DataProfilerInfo;


@Repository
public interface ProfilerInfoRepository extends MongoRepository<DataProfilerInfo, String>{

}
