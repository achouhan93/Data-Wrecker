package com.data.booleandatatype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.booleandatatype.service.DatasetStatsProcessingService;




@RestController
@RequestMapping("/dimension")
public class BooleanDataTypeController {

	@Autowired
	private DatasetStatsProcessingService datasetStatsProcessingService;

	@RequestMapping(value = "/booleanDatatypeDimensions", method = RequestMethod.GET)
	public String getDimensionResults(@RequestParam String columnName, @RequestParam int wreckingPercentage){
		return datasetStatsProcessingService.getDimensionResults(columnName,wreckingPercentage);
	}
}
