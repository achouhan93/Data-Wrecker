package com.data.wrecker.Incompleteness.service;

import java.util.List;

public interface IncompletenessService {

	public String removeValues(String collectionName, String columnName, List<String> wreckingIdsForDimension);
	
}
