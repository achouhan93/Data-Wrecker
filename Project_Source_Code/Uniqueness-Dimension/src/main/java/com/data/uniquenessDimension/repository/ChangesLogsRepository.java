package com.data.uniquenessDimension.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.uniquenessDimension.model.ChangesLog;



public interface ChangesLogsRepository extends MongoRepository<ChangesLog, String>{

}
