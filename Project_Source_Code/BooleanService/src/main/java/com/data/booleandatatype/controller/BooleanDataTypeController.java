package com.data.booleandatatype.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.booleandatatype.service.BooleanDataTypeService;


@RestController
@RequestMapping("/demo")
public class BooleanDataTypeController {

	@Autowired
	BooleanDataTypeService booleanservice;
	
	@GetMapping("/booleanDimension")
	public JSONObject getDimensions()
	{
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.append("Accuracy", booleanservice.AccuracyCheck());
		jsonObject.append("Completeness", booleanservice.NullCheck());
		jsonObject.append("Validity", booleanservice.ValidityCheck());
		jsonObject.append("Consostency", booleanservice.ConsistencyCheck());
		
		return jsonObject;
	
	}	
	
}
