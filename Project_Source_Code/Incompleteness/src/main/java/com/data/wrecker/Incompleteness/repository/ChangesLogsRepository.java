package com.data.wrecker.Incompleteness.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.wrecker.Incompleteness.model.ChangesLog;



public interface ChangesLogsRepository extends MongoRepository<ChangesLog, String>{

}
