package com.data.completenessDimension.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.completenessDimension.model.ChangesLog;

public interface ChangesLogsRepository extends MongoRepository<ChangesLog, String>{

}
