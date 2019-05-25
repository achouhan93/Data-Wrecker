package com.data.decimaldatatype.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DecimalDataTypeService {

	Map<String, Set<String>> getDecimalDataTypePrediction(String patternIdentificationFilepath,int wreakingPercentage,String columnName);

	

}
