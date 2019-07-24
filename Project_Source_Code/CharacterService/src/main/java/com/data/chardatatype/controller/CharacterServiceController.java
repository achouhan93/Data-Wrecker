 package com.data.chardatatype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.chardatatype.service.DatasetStatsProcessingService;

@RestController
@RequestMapping("/dimension")
public class CharacterServiceController {
	
	@Autowired
	private DatasetStatsProcessingService datasetStatsProcessingService;


	@RequestMapping(value = "/characterDatatypeDimensions", method = RequestMethod.GET)
	public String getDimensionResults(@RequestParam String fileName,@RequestParam String wreckingPercentage){
		return datasetStatsProcessingService.getDimensionResults(fileName,25);
	}
	
}
