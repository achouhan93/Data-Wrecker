package com.data.stringService.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StringServiceController {

	 @RequestMapping(value = "/")
	   public String home() {
	      return "String Service";
	   }
}
