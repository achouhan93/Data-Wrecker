package com.data.wrecker.consistencydimension.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.wrecker.consistencydimension.model.ChangesLog;

public interface ChangesLogsRepository extends MongoRepository<ChangesLog, String>{

}
