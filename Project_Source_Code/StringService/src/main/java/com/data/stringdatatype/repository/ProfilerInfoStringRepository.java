package com.data.stringdatatype.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.stringdatatype.model.ColumnStatisticsModel;

public interface ProfilerInfoStringRepository extends MongoRepository<ColumnStatisticsModel, String>{

}
