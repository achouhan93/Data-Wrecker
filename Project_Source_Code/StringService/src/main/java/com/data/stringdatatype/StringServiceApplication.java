package com.data.stringdatatype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.data.stringdatatype")
public class StringServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StringServiceApplication.class, args);
	}
	

}
