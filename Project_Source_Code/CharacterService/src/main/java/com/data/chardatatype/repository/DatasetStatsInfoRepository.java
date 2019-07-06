package com.data.chardatatype.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.chardatatype.model.DatasetStats;

public interface DatasetStatsInfoRepository  extends MongoRepository<DatasetStats, String>{

}
