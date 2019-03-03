package com.data.helperServices.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelperServicesController {

	@RequestMapping(value = "/")
	public String home() {
		return "Helper Service";
	}
}
