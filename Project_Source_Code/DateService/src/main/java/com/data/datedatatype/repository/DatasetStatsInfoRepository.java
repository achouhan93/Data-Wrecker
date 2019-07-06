package com.data.datedatatype.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.datedatatype.model.DatasetStats;

public interface DatasetStatsInfoRepository extends MongoRepository<DatasetStats, String>{

}
