package com.data.stringdatatype.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.stringdatatype.model.ProfilerInfoString;

public interface ProfilerInfoStringRepository extends MongoRepository<ProfilerInfoString, String>{

}
