package com.data.booleandatatype.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import com.data.booleandatatype.service.BooleanDataTypeService;

public class BooleanDataTypeServiceImpl implements BooleanDataTypeService {

	String jsonData = "[\r\n" + 
			"{\r\n" + 
			"	\"TOTAL ROW COUNT\":100,\r\n" + 
			"	\"NULL COUNT\":20,\r\n" + 
			"	\"DISTINCT OUNT\": 4,\r\n" + 
			"	\"UNIQUE COUNT\": 0,\r\n" + 
			"	\"DUPLICATE\": 50,\r\n" + 
			"	\"DISTINCT VALUE LIST \":[\r\n" + 
			"	{\r\n" + 
			"	\"VALUE\": \"1\",\r\n" + 
			"	\"VALUE COUNT \": 45\r\n" + 
			"	},\r\n" + 
			"	{\r\n" + 
			"	\"VALUE\": \"0\",\r\n" + 
			"	\"VALUE COUNT \": 40  \r\n" + 
			"	},\r\n" + 
			"	{\r\n" + 
			"	\"VALUE\": \"TRR\",\r\n" + 
			"	\"VALUE COUNT \": 10  \r\n" + 
			"	},\r\n" + 
			"	{\r\n" + 
			"	\"VALUE\": \"FLL\",\r\n" + 
			"	\"VALUE COUNT \": 5  \r\n" + 
			"	}\r\n" + 
			"	],\r\n" + 
			"	\r\n" + 
			"	\"UNIQUE VALUE LIST\":[],\r\n" + 
			"	\"MIN_LENGTH\":1,\r\n" + 
			"	\"MAX_LENGTH\":3,\r\n" + 
			"	\"AVG_LENGTH\":1,\r\n" + 
			"	\"MINVALUE\":0,\r\n" + 
			"	\"MAX VALUE\":1,\r\n" + 
			"	\"AVG VALUE\":1,\r\n" + 
			"	\"TRUE COUNT\":1,\r\n" + 
			"	\"FALSE COUNT\":0,\r\n" + 
			"	\"REGEX\":[\r\n" + 
			"	\"REGEX_PATTREN_1\":{\r\n" + 
			"	\"REGEX_PATTREN\":\"XXYYDD\",\r\n" + 
			"	\"REGEX_COUNT\":3\r\n" + 
			"	},\r\n" + 
			"	\"REGEX_PATTREN_2\":{\r\n" + 
			"	\"REGEX_PATTREN\":\"FFBBHH\",\r\n" + 
			"	\"REGEX_COUNT\":6\r\n" + 
			"	},\r\n" + 
			"	\"REGEX_PATTREN_3\":{\r\n" + 
			"	\"REGEX_PATTREN\":\"HOJJ\",\r\n" + 
			"	\"REGEX_COUNT\":6\r\n" + 
			"	}\r\n" + 
			"	]\r\n" + 
			"	\r\n" + 
			"	}\r\n" + 
			"]";
	JSONObject jsonObject = new JSONObject(jsonData);
	int avgWrecking = getAvgWrecking(20);
	
	@Override
	public boolean NullCheck() {
		if(jsonObject.getInt("NULL_COUNT") > avgWrecking) {
			return false;
		} else {
			return true;
		}		
	}

	@Override
	public boolean ConsistencyCheck() {
		if(jsonObject.getInt("DISTINCTOUNT") > 2) {
			return isConsistent(jsonObject.getJSONArray("DISTINCT_VALUE_LIST"));
		}else {
			return true;
		}
	}

	@Override
	public boolean ValidityCheck() {	
		int trueCount = jsonObject.getInt("TRUE_COUNT");
		int falseCount = jsonObject.getInt("FALSE_COUNT");
		int nullCount = jsonObject.getInt("NULL_COUNT");
		int totalRowsCount = jsonObject.getInt("TOTAL_ROW_COUNT");
		if((trueCount + falseCount + nullCount ) == totalRowsCount ) {
			return true;
		}else if((totalRowsCount - (trueCount + falseCount + nullCount)) > avgWrecking ) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public boolean AccuracyCheck() {
		return true;
	}

	

	private int getAvgWrecking(int wreckingPercentage) {
		return wreckingPercentage/4;
	}
	
	private boolean isConsistent(JSONArray jsonArray) {
		int totalCount = 0;
		ArrayList<Integer> regexCountArrayList = new ArrayList(); 
		JSONObject isConsistentJsonObject = new JSONObject();
		for(int i=0; i< jsonArray.length(); i++) {
			isConsistentJsonObject = jsonArray.getJSONObject(i);
			regexCountArrayList.add(isConsistentJsonObject.getInt("REGEX_COUNT"));
			totalCount = totalCount + isConsistentJsonObject.getInt("REGEX_COUNT");
		}
		int maxValue = Collections.max(regexCountArrayList);
		regexCountArrayList.remove(maxValue);
		int secondMax = Collections.max(regexCountArrayList);
		if((totalCount - (maxValue + secondMax)) > avgWrecking) {
			return false;
		} else {
			return true;
		}
	}
	
	
}
