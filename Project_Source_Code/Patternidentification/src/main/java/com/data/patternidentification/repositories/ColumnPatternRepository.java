package com.data.patternidentification.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.patternidentification.model.PatternIdentificationModel;

public interface ColumnPatternRepository extends MongoRepository<PatternIdentificationModel,String> {

}
