package com.data.decimaldatatype;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DecimalDataType {
	
	private static final Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) {
		SpringApplication.run(DecimalDataType.class, args);
		LOGGER.info("******************DecimalService : APPLICATION STARTED********************");
	}

}
