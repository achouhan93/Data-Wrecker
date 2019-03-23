package com.data.wrecker.booleandatatype.service;


import com.data.wrecker.booleandatatype.bean.ProfilerInfo;

import org.json.JSONObject;

public class BooleanDataTypeImpl implements BooleanDataTypeInterface{

	
	String jsonData = "[{}]";
	JSONObject jsonObject = new JSONObject(jsonData);
	
	
	@Override
	public boolean NullCheck(ProfilerInfo profilerInfo) {
		int wreckingPercentage  = profilerInfo.getWreckingPercentage();
		int avgWrecking = wreckingPercentage / 4;
		if(jsonObject.getInt("NULL COUNT")> avgWrecking) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean ConsistencyCheck(ProfilerInfo profilerInfo) {
		int wreckingPercentage  = profilerInfo.getWreckingPercentage();
		int avgWrecking = wreckingPercentage / 4;
		if(profilerInfo.getDistinctCount() > 2) {
			
			
			return false;
		} else {
			return true;
		}
		
	}

	@Override
	public boolean ValidityCheck(ProfilerInfo profilerInfo) {
		int wreckingPercentage  = profilerInfo.getWreckingPercentage();
		int avgWrecking = wreckingPercentage / 4;
		int minLength = profilerInfo.getMinLength();
		int maxLength = profilerInfo.getMaxLength();
		int avgLength = profilerInfo.getAvgLength();	
		return true;		
	}

	@Override
	public boolean AccuracyCheck(ProfilerInfo profilerInfo) {
		
		return true;
	}

	
	private int maxOfThree(int num1, int num2, int num3) {
		
		int max;
		if(num1 > num2) {
			if(num1 > num3) {
				max = num1;
			}else {
				max = num3;
			}
		} else if(num2 > num3){
			max = num2;
		}else {
			max = num3;
		}
		
		return max;
	}
	

	
}
