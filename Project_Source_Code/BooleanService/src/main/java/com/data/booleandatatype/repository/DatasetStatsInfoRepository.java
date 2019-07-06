package com.data.booleandatatype.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.booleandatatype.model.DatasetStats;



public interface DatasetStatsInfoRepository  extends MongoRepository<DatasetStats, String>{

}
