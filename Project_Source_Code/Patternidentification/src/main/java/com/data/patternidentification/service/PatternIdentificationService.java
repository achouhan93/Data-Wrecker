package com.data.patternidentification.service;

import com.data.patternidentification.exception.PatternIdentificationException;
import com.data.patternidentification.model.DataProfilerInfo;

public interface PatternIdentificationService {



	DataProfilerInfo getPatternidentificationData(String collectionName)
			throws PatternIdentificationException;

}
