package com.data.integerService.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntegerServiceController {
	 @RequestMapping(value = "/")
	   public String home() {
	      return "Integer Service";
	   }

}
