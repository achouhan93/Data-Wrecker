package com.data.booleandatatype.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import com.data.booleandatatype.service.BooleanDataTypeService;

public class BooleanDataTypeServiceImpl implements BooleanDataTypeService {

	String jsonData = "";
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
