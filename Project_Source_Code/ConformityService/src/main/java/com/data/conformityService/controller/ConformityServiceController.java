package com.data.conformityService.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.conformityService.service.ConformityServiceInterface;



@RestController
@RequestMapping("/dimension")
public class ConformityServiceController {
	
	@Autowired
	private ConformityServiceInterface conformityServiceInterface;
	
	@RequestMapping(value = "/conformityDimension", method = RequestMethod.GET)
	public String applyConformityDimension(@RequestParam String collectionName, @RequestParam String columnName, @RequestParam String[] wreckingIdsForDimension) {	
		List<String> wreckingIds = new ArrayList<String>();
		
		for(int i=0; i<wreckingIdsForDimension.length;i++ ) {
			wreckingIds.add(wreckingIdsForDimension[i]);			
		}
		return conformityServiceInterface.removeConformityDimension(collectionName, columnName,wreckingIds);
	}


}
