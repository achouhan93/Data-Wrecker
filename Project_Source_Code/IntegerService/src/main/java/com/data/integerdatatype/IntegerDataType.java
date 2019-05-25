package com.data.integerdatatype;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntegerDataType {
	
	private static final Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) {
		SpringApplication.run(IntegerDataType.class, args);
		LOGGER.info("******************DecimalService : APPLICATION STARTED********************");
	}

}
