package com.data.wrecker.consistencydimension.service;

import java.util.List;

public interface ConsistencyService {

	public String removeConsistencyDimension(String collectionName, String columnName, List<String> wreckingIds);
	
}
