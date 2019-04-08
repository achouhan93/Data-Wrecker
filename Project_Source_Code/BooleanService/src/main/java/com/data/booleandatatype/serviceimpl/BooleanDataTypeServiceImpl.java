package com.data.booleandatatype.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.booleandatatype.service.BooleanDataTypeService;
import com.data.booleandatatype.model.ProfilerInfo;
import com.data.booleandatatype.model.RegexInfo;


@Service
@Transactional
public class BooleanDataTypeServiceImpl implements BooleanDataTypeService {

	int avgWrecking = 20;
	
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
		if(profilerInfo.getDistinctvaluelist().size() > 2) {
			return isConsistent(profilerInfo);
		}else {
			return true;
		}
	}

	@Override
	public boolean ValidityCheck(ProfilerInfo profilerInfo) {	
		int trueCount = profilerInfo.getTrueCount();
		int falseCount = profilerInfo.getFalseCount();
		int nullCount = profilerInfo.getNullCount();
		int totalRowsCount = profilerInfo.getTotalRowCount();
		if((trueCount + falseCount + nullCount ) == totalRowsCount ) {
			return true;
		}else if((totalRowsCount - (trueCount + falseCount + nullCount)) > avgWrecking ) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public boolean AccuracyCheck(ProfilerInfo profilerInfo) {
		return true;
	}

	

	private int getAvgWrecking(int wreckingPercentage) {
		return wreckingPercentage/4;
	}
	
	private boolean isConsistent(ProfilerInfo profilerInfo) {
		int totalCount = 0;
		ArrayList<RegexInfo> regexCountArrayList = profilerInfo.getRegexInfo(); 
		ArrayList<Integer> regexCounts = new ArrayList();
		for(int i=0; i< regexCountArrayList.size(); i++) {			
			regexCounts.add(regexCountArrayList.get(i).getRegexPatternCount());
			totalCount = totalCount + regexCountArrayList.get(i).getRegexPatternCount();			
		}
		int maxValue = Collections.max(regexCounts);
		regexCountArrayList.remove(maxValue);
		int secondMax = Collections.max(regexCounts);
		if((totalCount - (maxValue + secondMax)) > avgWrecking) {
			return false;
		} else {
			return true;
		}
	}
	
	
}
