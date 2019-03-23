package com.data.wrecker.booleandatatype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@ComponentScan("com.data.wrecker")
public class BooleanDataTypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooleanDataTypeApplication.class, args);
	}

}
