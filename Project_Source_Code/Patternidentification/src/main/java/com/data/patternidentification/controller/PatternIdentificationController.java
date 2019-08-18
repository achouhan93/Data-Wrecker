package com.data.patternidentification.controller;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.patternidentification.exception.PatternIdentificationException;
import com.data.patternidentification.model.DataProfilerInfo;
import com.data.patternidentification.model.DatasetDetails;
import com.data.patternidentification.service.PatternIdentificationService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/patternIdentification")
public class PatternIdentificationController {

	private static final Logger LOGGER = LogManager.getLogger();

	@Autowired
	PatternIdentificationService patternIdentificationService;

	@GetMapping("/getPossiblePatternsForData")
	public DatasetDetails patternIdentification(@RequestParam String fileName) throws PatternIdentificationException, JsonProcessingException {
		LOGGER.info("Inside Patternidentification controller");
		DataProfilerInfo dataProfilerInfo = new DataProfilerInfo();
		DatasetDetails dataSetDetails = new DatasetDetails();
		dataProfilerInfo = patternIdentificationService.getPatternidentificationData(fileName);
		if(dataProfilerInfo.getFileName().isEmpty()) {
			dataSetDetails.setResult("Failure");
		}else {
			dataSetDetails.setResult("Success");
			dataSetDetails.setCollectionName(dataProfilerInfo.getFileName());
		}
		return dataSetDetails;

	}
}
