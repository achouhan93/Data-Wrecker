package com.data.decimalService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class DecimalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DecimalServiceApplication.class, args);
	}
	
	 @RequestMapping(value = "/")
	   public String home() {
	      return "Decimal Service";
	   }


}
