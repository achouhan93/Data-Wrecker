package com.data.integerService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class IntegerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegerServiceApplication.class, args);
	}
	 @RequestMapping(value = "/")
	   public String home() {
	      return "Integer Service";
	   }


}
