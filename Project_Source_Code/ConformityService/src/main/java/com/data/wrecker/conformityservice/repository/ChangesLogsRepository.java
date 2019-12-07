package com.data.wrecker.conformityservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.wrecker.conformitydimension.model.ChangesLog;



public interface ChangesLogsRepository extends MongoRepository<ChangesLog, String>{

}
