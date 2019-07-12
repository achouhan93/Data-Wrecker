package com.data.patternidentification.service;

import com.data.patternidentification.exception.PatternIdentificationException;
import com.data.patternidentification.model.PatternIdentificationModel;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PatternIdentificationService {


	PatternIdentificationModel getPatternidentificationData(String fileName) throws PatternIdentificationException, JsonProcessingException;

}
