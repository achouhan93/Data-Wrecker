package com.data.columndatatypeprediction.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.columndatatypeprediction.service.ColumnDataTypePredictionService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class ColumnDataTypePredictionServiceImpl implements ColumnDataTypePredictionService {

	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Map<String, String> getColumnDataTypePrediction(String patternIdentificationFilepath)
			throws JSONException, JsonParseException, JsonMappingException, IOException {
		String patternIdentificationDataStr = "{" + "\"Date\": {" + "\"PatternsIdentifiedForDate\": {" + "\"0\": 4,"
				+ "\"A,A\": 2," + "\"$0000,00\": 13," + "\"000000.0\": 1," + "\"000A0000a.0tdg\": 1" + "}" + "},"
				+ "\"statecode\": {" + "\"PatternsIdentifiedForstatecode\": {" + "\"XXXX XXXXXX$\": 8,"
				+ "\"xxxx XXXXXX\": 2," + "}" + "}" + "}";
		List<String> datasetHeaders = new ArrayList<String>();
		//get header of the dataset
		datasetHeaders.add("Date");
		datasetHeaders.add("statecode");
		/*
		 * datasetHeaders.add("county"); datasetHeaders.add("eq_site_limit");
		 */
		JSONObject jObj = new JSONObject(patternIdentificationDataStr);

				int doubleCnt = 0;
		int charCnt = 0;
		int boolCnt = 0;
		int intCnt = 0;
		int strCnt = 0;
		Map<String, String> columnDataTypes = new LinkedHashMap<String, String>();

		for (int datasetHeadersIterator = 0; datasetHeadersIterator < datasetHeaders.size(); datasetHeadersIterator++) {
			Map<String, Integer> dataTypes = new HashMap<String, Integer>();
			dataTypes.put("Boolean", 0);
			dataTypes.put("Character", 0);
			dataTypes.put("Double", 0);
			dataTypes.put("Integer", 0);
			dataTypes.put("String", 0);
					
			String columnPatterns = jObj.getJSONObject(datasetHeaders.get(datasetHeadersIterator))
					.getString("PatternsIdentifiedFor" + datasetHeaders.get(datasetHeadersIterator));
			Map<String, Object> response = new ObjectMapper().readValue(columnPatterns, HashMap.class);
			
			for (Entry<String, Object> patternIdentificationIterator : response.entrySet()) {

				LOGGER.info("Key = " + patternIdentificationIterator.getKey() + ", Value = "
						+ patternIdentificationIterator.getValue());
				String pattern = patternIdentificationIterator.getKey();
				int patternOccurance = (int) patternIdentificationIterator.getValue();
				// Can either be a character or a boolean.
				if (pattern.length() == 1) {
					// ----incomplete logic
					// 1st get the name of that column with pattern with size of 1
					// then get the complete data of that column
					// go through data and check if it contains 0/1. if yes it a boolean else
					// character.
					if (pattern.equals("1") || pattern.equals("0")) {
						boolCnt = boolCnt + patternOccurance;
						dataTypes.put("Boolean", boolCnt);
					} else {
						charCnt = charCnt + patternOccurance;
						dataTypes.put("Character", charCnt);
					}

				} else if (pattern.matches("(\\+|-)?([0-9]+(\\.[0-9]+))") || pattern.matches("(\\+|-)?([0-9]+(\\,[0-9]+))")) {
					doubleCnt = doubleCnt + patternOccurance;
					dataTypes.put("Double", doubleCnt);
				} else if (pattern.matches("[0-9]+")) {
					intCnt = intCnt + patternOccurance;
					dataTypes.put("Integer", intCnt);
				} else {
					strCnt = strCnt + patternOccurance;
					dataTypes.put("String", strCnt);
				}

			}
			columnDataTypes.put(datasetHeaders.get(datasetHeadersIterator), dataTypes.entrySet().stream()
					.max((entry1, entry2) -> Integer.compare(entry1.getValue(), entry2.getValue())).get().getKey());
		}
		LOGGER.info("finaljsonobj" + jObj);

		return columnDataTypes;
	}

}
