package com.data.decimaldatatypeservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.decimaldatatypeservice.service.DecimalDataTypeServiceService;

@RestController
@RequestMapping("/decimalDataType")
public class DecimalDataTypeServiceController {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Autowired
	DecimalDataTypeServiceService decimalDataTypeService;
	
	@GetMapping("/decimalDataTypeDecision")
	public String columnDataTypePrediction(int wreakingPercentage,String collectionName)
	{
		LOGGER.info("Inside decimalDataTypeService controller");
		return decimalDataTypeService.getDecimalDataTypePrediction(wreakingPercentage,collectionName);
	}
}
