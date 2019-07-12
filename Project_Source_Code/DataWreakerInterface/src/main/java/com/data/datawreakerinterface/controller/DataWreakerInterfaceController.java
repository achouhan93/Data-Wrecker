package com.data.datawreakerinterface.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.datawreakerinterface.exception.DataWreakernterfaceException;
import com.data.datawreakerinterface.service.DataWreakerIntefaceService;

@RestController
@RequestMapping("/dataWreakerInterface")
public class DataWreakerInterfaceController {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Autowired
	DataWreakerIntefaceService dataWreakerInterfaceService;

	
	@GetMapping("/dataPopulation")
	public String DataPopulationIntoMongo() throws FileNotFoundException, IOException, DataWreakernterfaceException
	{
		LOGGER.info("DataPopulationIntoMongo controller");
		return dataWreakerInterfaceService.putCsvDataIntoMongo();
		
	}
	
}
