package com.data.booleandatatype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan("com.data.booleandatatype")
public class BooleanDataTypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooleanDataTypeApplication.class, args);
	}

}
