package com.data.uniquenessDimension.service;

import java.util.List;

public interface UniquenessDimensionService {
	
	public String applyUniquenessDimension(String collectionName, String columnName, List<String> wreckingIdsForDimension);

	
}
