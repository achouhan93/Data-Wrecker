package com.data.patternidentification.service;

import java.util.Map;

import org.json.JSONObject;

public interface PatternIdentificationService {


	Map<String, Map<String, Map<String, Integer>>> getPatternidentificationData(String fileName);

}
