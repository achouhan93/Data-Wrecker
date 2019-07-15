package com.data.patternidentification.controller;



import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.patternidentification.exception.PatternIdentificationException;
import com.data.patternidentification.model.PatternIdentificationModel;
import com.data.patternidentification.service.PatternIdentificationService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/patternIdentification")
public class PatternIdentificationController {

	private static final Logger LOGGER = LogManager.getLogger();

	@Autowired
	PatternIdentificationService patternIdentificationService;

	@GetMapping("/getPossiblePatternsForData")
	public PatternIdentificationModel patternIdentification(@RequestParam String fileName) throws PatternIdentificationException, JsonProcessingException {
		LOGGER.info("Inside Patternidentification controller");
		List<String> columnHeaders = new ArrayList<String>();
		columnHeaders.add("Date");
		columnHeaders.add("statecode");
		columnHeaders.add("county");
		columnHeaders.add("eq_site_limit");
		return patternIdentificationService.getPatternidentificationData(fileName,columnHeaders);

	}
}
