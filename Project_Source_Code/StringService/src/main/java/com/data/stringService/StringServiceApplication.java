package com.data.stringService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class StringServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StringServiceApplication.class, args);
	}
	 @RequestMapping(value = "/")
	   public String home() {
	      return "String Service";
	   }

}
