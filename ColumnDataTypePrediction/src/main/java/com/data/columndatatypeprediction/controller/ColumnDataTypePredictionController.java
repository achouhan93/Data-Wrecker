package com.data.columndatatypeprediction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.columndatatypeprediction.service.ColumnDataTypePredictionService;

@RestController
@RequestMapping("/columnDataTypePrediction")
public class ColumnDataTypePredictionController {
	
	@Autowired
	ColumnDataTypePredictionService columnDataTypePredictionService;
	
	@GetMapping("/hello")
	public String sayHello()
	{
		return "Hello";
		
	}
}
