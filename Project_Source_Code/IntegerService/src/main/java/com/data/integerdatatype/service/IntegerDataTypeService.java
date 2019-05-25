package com.data.integerdatatype.service;

import java.util.Map;
import java.util.Set;

public interface IntegerDataTypeService {

	Map<String, Set<String>> getDecimalDataTypePrediction(String patternIdentificationFilepath,int wreakingPercentage,String columnName);

	

}
