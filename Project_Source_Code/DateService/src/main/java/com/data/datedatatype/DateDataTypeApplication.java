package com.data.datedatatype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class DateDataTypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DateDataTypeApplication.class, args);
	}

}
