package com.data.columnStatistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.columnStatistics.model.DataProfilerInfo;

public interface ColumnStatsRepo extends MongoRepository<DataProfilerInfo, String>{

}
