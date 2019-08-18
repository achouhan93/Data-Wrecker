package com.example.mainorchestrator.service;

import com.example.mainorchestrator.entity.DatasetDetails;

public interface LoadFileIntoMongo {

	public DatasetDetails loadFileIntoMongo();
	
	public String callAllDataTypeDimensionServices(String fileName, int wreckingPercentage);
}
