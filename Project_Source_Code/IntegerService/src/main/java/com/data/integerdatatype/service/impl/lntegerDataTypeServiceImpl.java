package com.data.integerdatatype.service.impl;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.integerdatatype.service.IntegerDataTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class lntegerDataTypeServiceImpl implements IntegerDataTypeService {

	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Map<String, Set<String>> getDecimalDataTypePrediction(String patternIdentificationFilepath,
			int wreakingPercentage, String columnName) {
		LOGGER.info("Inside DecimalDataTypeServiceImpl");
		wreakingPercentage = 20; // Hardcoded value for wreaking %
		columnName = "Date";
		int numberOfRecords = 20000;
		int indivisualWreakingCountForDimentions = (((20 / 5) * numberOfRecords) / 100);
		LinkedHashSet<String> datadimention = new LinkedHashSet<String>();
		Map<String, Set<String>> dataDimentionList = new HashMap<String, Set<String>>();
		LOGGER.info("indivisualWreakingCountForDimentions" + indivisualWreakingCountForDimentions);
		try {
			String patternIdentificationDataStr = "{" + "\"Date\": {" + "\"PatternsIdentifiedForDate\": {"
					+ "\"\": 800," + "\"xxx\": 2," + "\"xx\": 13," + "\"000XXxx.0\": 1," + "\"000#@.0\": 1,"
					+ "\"#$%\": 1," + "\"-0000.00\": 1," + "\"000X0000$%&0xxx\": 1" + "}" + "}," + "\"statecode\": {"
					+ "\"PatternsIdentifiedForstatecode\": {" + "\"XXXX XXXXXX$\": 8," + "\"xxxx XXXXXX\": 2," + "}"
					+ "}" + "}";
			JSONObject jObj = new JSONObject(patternIdentificationDataStr);

			String columnPatterns = jObj.getJSONObject(columnName).getString("PatternsIdentifiedFor" + columnName);
			Map<String, Object> response = new ObjectMapper().readValue(columnPatterns, HashMap.class);
			int consistancyCnt = 0;
			int completenessCnt = 0;
			int accuracyCnt = 0;
			int validaityCnt = 0;
			for (Entry<String, Object> patternIdentificationIterator : response.entrySet()) {

				int patternValue = (int) patternIdentificationIterator.getValue();
				String patternString = patternIdentificationIterator.getKey();

				if (patternString.equals("")) {
					completenessCnt = completenessCnt + patternValue;
					LOGGER.info("Completeness may be called");
				} else if (patternString.matches("(\\\\+|-)?([0-9]+(\\\\,[0-9]+))") || patternString.matches("(\\\\+|-)?([0-9]+(\\\\.[0-9]+))") ) {
					consistancyCnt = consistancyCnt + patternValue;
					LOGGER.info("Consistancy for ',' may be called");
				} else if (!patternString.matches("^-[0-9]*")) {
					validaityCnt = validaityCnt + patternValue;
					LOGGER.info("validaity may be called(-ve values)");
				} else {
					accuracyCnt = accuracyCnt + patternValue;
					LOGGER.info("Accuracy may be called");
				}
			}

			if (indivisualWreakingCountForDimentions > completenessCnt) {
				datadimention.add("Completeness");
			}

			if (indivisualWreakingCountForDimentions > consistancyCnt) {
				datadimention.add("Consistancy");
			}

			if (indivisualWreakingCountForDimentions > validaityCnt) {
				datadimention.add("validaity");
			}

			if (indivisualWreakingCountForDimentions > accuracyCnt) {
				datadimention.add("Accuracy");
			}

		} catch (Exception e) {
			LOGGER.info("Exception " + e);
		}
		dataDimentionList.put(columnName, datadimention);
		return dataDimentionList;
	}

}
