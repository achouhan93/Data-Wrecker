package com.data.columndatatypeprediction.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.columndatatypeprediction.model.DataProfilerInfo;

public interface ColumnDataTypeRepository extends MongoRepository<DataProfilerInfo,String> {

}
