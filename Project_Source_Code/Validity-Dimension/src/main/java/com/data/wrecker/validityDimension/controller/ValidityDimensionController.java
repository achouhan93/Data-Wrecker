package com.data.wrecker.validityDimension.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.wrecker.validityDimension.service.ValidityDimensionService;

@RestController
@RequestMapping("/dimension")
public class ValidityDimensionController {

	@Autowired
	ValidityDimensionService validityDimensionService;
	
	@RequestMapping(value = "/validityDimension", method = RequestMethod.GET)
	public String applyCompletenessDimension(@RequestParam String collectionName, @RequestParam String columnName, @RequestParam String[] wreckingIdsForDimension) throws JSONException {	
		List<String> wreckingIds = new ArrayList<String>();
		
		for(int i=0; i<wreckingIdsForDimension.length;i++ ) {
			wreckingIds.add(wreckingIdsForDimension[i]);			
		}
		return validityDimensionService.removeValidityDimension(collectionName, columnName,wreckingIds);
	}
	
}
