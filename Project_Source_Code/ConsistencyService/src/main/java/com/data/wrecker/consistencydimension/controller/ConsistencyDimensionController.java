package com.data.wrecker.consistencydimension.controller;

import java.util.ArrayList;
import java.util.List;

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
	public String applyCompletenessDimension(@RequestParam String collectionName, @RequestParam String columnName, @RequestParam String[] wreckingIdsForDimension) {	
		List<String> wreckingIds = new ArrayList<String>();
		
		for(int i=0; i<wreckingIdsForDimension.length;i++ ) {
			wreckingIds.add(wreckingIdsForDimension[i]);			
		}
		return consistencyService.removeConsistencyDimension(collectionName, columnName,wreckingIds);
	}
}
