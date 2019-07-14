package com.data.patternidentification.service;

import java.util.List;

import com.data.patternidentification.exception.PatternIdentificationException;
import com.data.patternidentification.model.PatternIdentificationModel;

public interface PatternIdentificationService {



	PatternIdentificationModel getPatternidentificationData(String collectionName, List<String> columnHeaders)
			throws PatternIdentificationException;

}
