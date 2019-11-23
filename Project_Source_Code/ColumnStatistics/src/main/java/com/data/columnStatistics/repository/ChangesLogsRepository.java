package com.data.columnStatistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.columnStatistics.model.ChangesLog;


public interface ChangesLogsRepository extends MongoRepository<ChangesLog, String>{

}
