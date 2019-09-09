package com.data.integerdatatypeservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.integerdatatypeservice.service.IntegerDataTypeServiceService;


@RestController
@RequestMapping("/integerDataType")
public class IntegerDataTypeServiceController {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Autowired
	IntegerDataTypeServiceService integerDataTypeService;
	
	@GetMapping("/integerDataTypeDecision")
	public String columnDataTypePrediction(int wreakingDataRecordPosition,String collectionName)
	{
		LOGGER.info("Inside integerDataType controller");
		return integerDataTypeService.getIntegerDataTypePrediction(wreakingDataRecordPosition,collectionName);
	}

}
