package com.data.columndatatypeprediction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ColumnDataTypePrediction {
	
	private static final Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) {
		SpringApplication.run(ColumnDataTypePrediction.class, args);
		LOGGER.info("******************ColumnDataTypePrediction : APPLICATION STARTED********************");
	}

}
