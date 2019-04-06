package com.data.datedatatype.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.datedatatype.model.ProfilerInfo;
import com.data.datedatatype.repository.ProfilerInfoRepository;
import com.data.datedatatype.service.DateDataTypeService;


@Transactional 
public class DateDataTypeImpl implements DateDataTypeService{

	String jsonData = "";
	JSONObject jsonObject = new JSONObject(jsonData);
	JSONArray profilerJsonArray = jsonObject.getJSONArray("profilerinfo");
	JSONArray regexJsonArray = jsonObject.getJSONArray("regexinfo");
	JSONObject profilerJsonObject = new JSONObject(profilerJsonArray);
	JSONObject regexJsonObject = new JSONObject(regexJsonArray);
	
	int avgWrecking = getAvgWrecking(20);
	
		@Override
	public boolean NullCheck() {
			
		if(profilerJsonObject.getInt("nullcount")> avgWrecking) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean ConsistencyCheck() {
	
		if(regexJsonArray.length()>1) {
			return isConsistent(regexJsonArray);
			
		}else {
			return true;	
		}
		
	}

	@Override
	public boolean ValidityCheck() {
		return isValid(profilerJsonObject);
	}

	@Override
	public boolean AccuracyCheck() {
		return true;
	}
	
	
	
	private int getAvgWrecking(int wreckingPercentage) {
		int totalNumberOfRows = jsonObject.getInt("");
		return wreckingPercentage/4;
	}
	

	private boolean isConsistent(JSONArray jsonArray) {
		int totalCount = 0;
		ArrayList<Integer> regexCountArrayList = new ArrayList(); 
		JSONObject isConsistentJsonObject = new JSONObject();
		for(int i=0; i< jsonArray.length(); i++) {
			isConsistentJsonObject = jsonArray.getJSONObject(i);
			regexCountArrayList.add(isConsistentJsonObject.getInt("regexcount"));
			totalCount = totalCount + isConsistentJsonObject.getInt("regexcount");
		}
		int maxValue = Collections.max(regexCountArrayList);
		if((totalCount - maxValue) > avgWrecking) {
			return false;
		} else {
			return true;
		}
	}
	
	
	private boolean isValid(JSONObject jsonObject) {
		
		if(!(jsonObject.getInt("minlength") == jsonObject.getInt("maxlength") && 
				jsonObject.getInt("maxlength") == jsonObject.getInt("avglength"))) {
			int minLength = jsonObject.getInt("minlength");
			int maxLength = jsonObject.getInt("maxlength");
			int avgLength = jsonObject.getInt("avglength");
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
