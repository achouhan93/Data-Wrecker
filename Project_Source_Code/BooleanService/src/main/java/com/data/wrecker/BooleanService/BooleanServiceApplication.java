package com.data.wrecker.BooleanService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class BooleanServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooleanServiceApplication.class, args);
	}
	
	 @RequestMapping(value = "/")
	   public String home() {
	      return "Boolean Service";
	   }

}
