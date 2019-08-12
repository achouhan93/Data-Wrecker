package com.data.uniquenessDimension.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.uniquenessDimension.service.UniquenessDimensionService;

@RestController
@RequestMapping("/dimension")
public class UniquenessDimensionController {

	@Autowired
	private UniquenessDimensionService uniquenessService;
	
	@RequestMapping(value = "/uniquenessDimension", method = RequestMethod.GET)
	public String applyCompletenessDimension(@RequestParam String collectionName, @RequestParam String columnName) {	
		return uniquenessService.applyUniquenessDimension(collectionName, columnName);
	}
}
