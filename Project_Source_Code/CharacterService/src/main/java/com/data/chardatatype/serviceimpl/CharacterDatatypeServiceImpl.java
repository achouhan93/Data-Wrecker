package com.data.chardatatype.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.aggregation.AccumulatorOperators.Avg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.chardatatype.model.ProfilerInfoChar;
import com.data.chardatatype.model.RegexInfoChar;
import com.data.chardatatype.service.CharacterDataTypeService;


@Service
@Transactional
public class CharacterDatatypeServiceImpl  implements CharacterDataTypeService{

	@Override
	public boolean NullCheck(ProfilerInfoChar profilerInfo) {
		if(profilerInfo.getNullCount() > 20) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean ConsistencyCheck(ProfilerInfoChar profilerInfo) {
		return true;
	}

	@Override
	public boolean ValidityCheck(ProfilerInfoChar profilerInfo) {
		ArrayList<RegexInfoChar> regexInfoArrayList = profilerInfo.getRegexInfo();
		int count = 0;
		if(regexInfoArrayList.size() > 1) {
			for(int i =0; i< regexInfoArrayList.size(); i++) {
				if(!(regexInfoArrayList.get(i).getRegexPattern().contains("X") || regexInfoArrayList.get(i).getRegexPattern().contains("x"))) {
					count = count + regexInfoArrayList.get(i).getRegexPatternCount();
				}				
			}
			if(count > 20) {
				return false;
			}else {
				return true;
			}
		}else if(regexInfoArrayList.get(0).getRegexPattern().contains("X") || regexInfoArrayList.get(0).getRegexPattern().contains("x")) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public boolean AccuracyCheck(ProfilerInfoChar profilerInfo) {
		return true;
	}
	
}
