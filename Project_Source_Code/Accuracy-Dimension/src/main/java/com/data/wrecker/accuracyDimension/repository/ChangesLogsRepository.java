package com.data.wrecker.accuracyDimension.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.wrecker.accuracyDimension.model.ChangesLog;

public interface ChangesLogsRepository extends MongoRepository<ChangesLog, String>{

}
