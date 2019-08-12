package com.data.wrecker.consistencydimension.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.wrecker.consistencydimension.service.ConsistencyService;

@RestController
@RequestMapping("/dimension")
public class ConsistencyDimensionController {

	@Autowired
	private ConsistencyService consistencyService;
	
	
	@RequestMapping(value = "/consistencyDimension", method = RequestMethod.GET)
	public String applyCompletenessDimension(@RequestParam String collectionName, @RequestParam String columnName) {	
		return consistencyService.removeConsistencyDimension(collectionName, columnName);
	}
}
