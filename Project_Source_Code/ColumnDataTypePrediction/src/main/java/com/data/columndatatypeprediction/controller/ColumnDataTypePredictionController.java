package com.data.columndatatypeprediction.controller;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.columndatatypeprediction.service.ColumnDataTypePredictionService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/columnDataTypePrediction")
public class ColumnDataTypePredictionController {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Autowired
	ColumnDataTypePredictionService columnDataTypePredictionService;
	
	@GetMapping("/getDataTypeOfAColumns")
	public String columnDataTypePrediction(String collectionName) throws JSONException, JsonParseException, JsonMappingException, IOException
	{
		LOGGER.info("Inside columnDataTypePrediction controller");
		List<String> columnHeader = null;
		return columnDataTypePredictionService.getColumnDataTypePrediction(collectionName,columnHeader);
		
	}
}
