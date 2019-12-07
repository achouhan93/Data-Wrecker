package com.data.datawreakerinterface.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.datawreakerinterface.exception.DataWreakernterfaceException;
import com.data.datawreakerinterface.model.DatasetDetails;
import com.data.datawreakerinterface.service.DataWreakerIntefaceService;

@RestController
@RequestMapping("/dataWreakerInterface")
public class DataWreakerInterfaceController {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Autowired
	DataWreakerIntefaceService dataWreakerInterfaceService;

	
	@GetMapping("/dataPopulation")
	public DatasetDetails DataPopulationIntoMongo() throws FileNotFoundException, IOException, DataWreakernterfaceException
	{
		LOGGER.info("DataPopulationIntoMongo controller");
		return dataWreakerInterfaceService.putCsvDataIntoMongo();
		
	}
	
	@GetMapping("/referenceDataApi")
	public String referenceDataApiToMongo(@RequestParam String referenceApi,@RequestParam String columnName) throws FileNotFoundException, IOException, DataWreakernterfaceException, JSONException
	{
		LOGGER.info("DataPopulationIntoMongo controller");
		return dataWreakerInterfaceService.referenceDataApiToMongo(referenceApi,columnName);
		
	}
	
	@GetMapping("/exportDatainCsv")
	public DatasetDetails exportDataFromMongo(@RequestParam String collectionName) throws FileNotFoundException, IOException, DataWreakernterfaceException
	{		
		return dataWreakerInterfaceService.exportDataAsCSV(collectionName);
	}
	
	@GetMapping("/referenceData")
	public DatasetDetails exportDataFromMongo() throws FileNotFoundException, IOException, DataWreakernterfaceException
	{		
		return dataWreakerInterfaceService.putRefenceColumnDataToMongo();
	}
	
}
