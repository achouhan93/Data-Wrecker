package com.data.datedatatype.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.data.datedatatype.model.ProfilerInfo;
import com.data.datedatatype.repository.ProfilerInfoRepository;
import com.data.datedatatype.service.DateDataTypeService;

public class DateDataTypeImpl implements DateDataTypeService{

	String jsonData = "";
	JSONObject jsonObject = new JSONObject(jsonData);
	int avgWrecking = getAvgWrecking(20);
	
		@Override
	public boolean NullCheck() {
		
		if(jsonObject.getInt("NULL_COUNT")> avgWrecking) {
			return false;
		} else {
		
			return true;
		}
	}

	@Override
	public boolean ConsistencyCheck() {
		JSONArray jsonArray = jsonObject.getJSONArray("REGEX");
		if(jsonArray.length()>1) {
			
			return isConsistent(jsonArray);
			
		}else {
			return true;	
		}
		
	}

	@Override
	public boolean ValidityCheck() {
		return isValid(jsonObject);
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
		if((totalCount - maxValue) > avgWrecking) {
			return false;
		} else {
			return true;
		}
	}
	
	
	private boolean isValid(JSONObject jsonObject) {
		
		if(!(jsonObject.getInt("MIN_LENGTH") == jsonObject.getInt("MAX_LENGTH") && 
				jsonObject.getInt("MAX_LENGTH") == jsonObject.getInt("AVG_LENGTH"))) {
			int minLength = jsonObject.getInt("MIN_LENGTH");
			int maxLength = jsonObject.getInt("MAX_LENGTH");
			int avgLength = jsonObject.getInt("AVG_LENGTH");
			int maxValue = getMaxValue(minLength, maxLength, avgLength);
			
			if(maxValue == avgLength) {
				return isNotWrecked(minLength, maxLength);
			}else if(maxValue == maxLength){
				return isNotWrecked(minLength, avgLength);
			}else {
				return isNotWrecked(avgLength, maxLength);
			}
		}else {
			return true;
		}		
	}
	
	private int getMaxValue(int number1, int number2, int number3) {
		if(number1 > number2) {
			if(number1 > number3) {
				return number3;
			}else {
				return number1;
			}
		}else if(number2 > number3) {
			return number2;
		}else {
			return number3;
		}		
	}
	
	private boolean isNotWrecked(int number1, int number2) {
		if((number1 + number2) > avgWrecking) {
			return false;
		}else {
			return true;
		}
	}
	
	
}
