package com.data.conformityService.service;

import java.util.List;

public interface ConformityServiceInterface {

	String removeConformityDimension(String collectionName, String columnName, List<String> wreckingIds);

}
