package com.data.datedatatype.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.datedatatype.serviceimpl.DateDataTypeImpl;

@RestController
@RequestMapping("/dateController")
public class DateDataTypeController {
	
	@Autowired 
	DateDataTypeImpl dateService;
	
	@GetMapping("/dateDimension")
	public JSONObject getDimensions()
	{
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.append("Accuracy", dateService.AccuracyCheck());
		jsonObject.append("Completeness", dateService.NullCheck());
		jsonObject.append("Validity", dateService.ValidityCheck());
		jsonObject.append("Consostency", dateService.ConsistencyCheck());
		
		return jsonObject;
		
	}	
	
}
