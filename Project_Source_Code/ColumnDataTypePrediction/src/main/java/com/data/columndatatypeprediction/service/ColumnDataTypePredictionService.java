package com.data.columndatatypeprediction.service;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ColumnDataTypePredictionService {

	Map<String, String> getColumnDataTypePrediction(String patternIdentificationFilepath) throws JSONException, JsonParseException, JsonMappingException, IOException;

}
