package com.data.lntegerdatatype.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.integerdatatype.service.IntegerDataTypeService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/decimalDataType")
public class lntegerDataTypeController {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Autowired
	IntegerDataTypeService decimalDataTypeService;
	
	@GetMapping("/decimalDataTypeDecision")
	public Map<String, Set<String>> columnDataTypePrediction(String patternIdentificationFilepath,int wreakingPercentage,String columnName) throws JSONException, JsonParseException, JsonMappingException, IOException
	{
		LOGGER.info("Inside decimalDataType controller");
		return decimalDataTypeService.getDecimalDataTypePrediction(patternIdentificationFilepath,wreakingPercentage,columnName);
		
	}
}
