package com.data.wrecker.validityDimension.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.wrecker.validityDimension.model.ChangesLog;

public interface ChangesLogsRepository extends MongoRepository<ChangesLog, String>{

}
