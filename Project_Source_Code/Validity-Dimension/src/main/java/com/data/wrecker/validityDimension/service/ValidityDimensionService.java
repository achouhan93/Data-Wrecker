package com.data.wrecker.validityDimension.service;

import java.util.List;

public interface ValidityDimensionService {

	public String removeValidityDimension(String collectionName, String columnName,List<String> wreckingIds);
}
