package com.data.wrecker.Incompleteness.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.wrecker.Incompleteness.service.IncompletenessService;


@RestController
@RequestMapping("/dimension")
public class IncompletenessController {

	
	@Autowired
	private IncompletenessService completenessDimensionService;
	
	
	@RequestMapping(value = "/completenessDimension", method = RequestMethod.GET)
	public String applyCompletenessDimension(@RequestParam String collectionName, @RequestParam String columnName, @RequestParam String[] wreckingIdsForDimension) {	
		List<String> wreckingIds = new ArrayList<String>();
		
		for(int i=0; i<wreckingIdsForDimension.length;i++ ) {
			wreckingIds.add(wreckingIdsForDimension[i]);			
		}
		
		return completenessDimensionService.removeValues(collectionName,columnName, wreckingIds);
	}
	
}
