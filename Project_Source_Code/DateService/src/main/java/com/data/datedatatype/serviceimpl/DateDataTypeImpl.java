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
import com.data.datedatatype.model.RegexInfo;
import com.data.datedatatype.repository.ProfilerInfoRepository;
import com.data.datedatatype.service.DateDataTypeService;


@Service
@Transactional
public class DateDataTypeImpl implements DateDataTypeService{

	//private ProfilerInfo profilerInfo;

		@Override
	public boolean NullCheck(ProfilerInfo profilerInfo) {
			
		if(profilerInfo.getNullCount() > 20) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean ConsistencyCheck(ProfilerInfo profilerInfo) {
		if(profilerInfo.getRegexInfo().size() > 1) {
			return isConsistent(profilerInfo);
			
		}else {
			return true;	
		}	
		
	}

	@Override
	public boolean ValidityCheck(ProfilerInfo profilerInfo) {
		return isValid(profilerInfo);	
	}

	@Override
	public boolean AccuracyCheck(ProfilerInfo profilerInfo) {
		return true;
	}
	
	
	/*
	private int getAvgWrecking(int wreckingPercentage) {
		int totalNumberOfRows = jsonObject.getInt("");
		return wreckingPercentage/4;
	}*/
	

	private boolean isConsistent(ProfilerInfo profilerInfo) {
		int totalCount = 0;
		ArrayList<RegexInfo> regexCountArrayList = profilerInfo.getRegexInfo(); 
		ArrayList<Integer> regexCounts = new ArrayList();
		for(int i=0; i< regexCountArrayList.size(); i++) {			
			regexCounts.add(regexCountArrayList.get(i).getRegexPatternCount());
			totalCount = totalCount + regexCountArrayList.get(i).getRegexPatternCount();			
		}
		int maxValue = Collections.max(regexCounts);
		if((totalCount - maxValue) > 20) {
			return false;
		} else {
			return true;
		}
	}
	
	
	private boolean isValid(ProfilerInfo profilerInfo) {
		
		if(!(profilerInfo.getMinLength() == profilerInfo.getMaxLength() && 
				profilerInfo.getMaxLength() == profilerInfo.getAvgLength())) {
			int minLength = profilerInfo.getMinLength();
			int maxLength = profilerInfo.getMaxLength();
			int avgLength = profilerInfo.getAvgLength();
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
		if((number1 + number2) > 20) {
			return false;
		}else {
			return true;
		}
	}
	
	
}
