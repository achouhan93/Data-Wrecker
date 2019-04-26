package com.data.chardatatype.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.aggregation.AccumulatorOperators.Avg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.chardatatype.model.DistinctValueListChar;
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
		ArrayList<DistinctValueListChar> distinctValueArrayList = profilerInfo.getDistinctvaluelist();
		int count = 0;
		if(profilerInfo.getDistinctCount() > 1) {
			for(int i =0; i< distinctValueArrayList.size(); i++) {
				if(!(distinctValueArrayList.get(i).getDistinctValueName().length() > 1)) {
					count = count + distinctValueArrayList.get(i).getDistinctValueCount();
				}				
			}		
			
			if(count > 20) {
				return false;
			}else {
				return true;
			}
		}else {
			return true;
		}
		
	}

	@Override
	public boolean AccuracyCheck(ProfilerInfoChar profilerInfo) {
		return true;
	}
	
}
