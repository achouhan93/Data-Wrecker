package com.data.decimalService.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class DecimalServiceController {

	@RequestMapping(value = "/")
	public String home() {
		return "Decimal Service";
	}


}
