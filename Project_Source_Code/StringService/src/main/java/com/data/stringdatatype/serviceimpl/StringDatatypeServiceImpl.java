  package com.data.stringdatatype.serviceimpl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.stringdatatype.model.ColumnStatisticsModel;
import com.data.stringdatatype.service.StringDataTypeService;


@Service
@Transactional
public class StringDatatypeServiceImpl implements StringDataTypeService {

	@Override
	public boolean NullCheck(ColumnStatisticsModel profilerInfo) {
		if(profilerInfo.getNullCount() > 20) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean ConsistencyCheck(ColumnStatisticsModel profilerInfo) {
		
		return false;
	}

	@Override
	public boolean ValidityCheck(ColumnStatisticsModel profilerInfo) {
		String emailRegex = "^[a-z0-9+_.-]+@(.+)$";
		boolean result = false;
		
		/*Pattern pattern = Pattern.compile(emailRegex);
		for(int i = 0; i < profilerInfo.getRegexInfo().size(); i++) {
			String regexPattern = profilerInfo.getRegexInfo().get(i).getRegexPattern();
			Matcher matcher = pattern.matcher(regexPattern);
			if(matcher.matches()) {
				if(profilerInfo.getRegexInfo().get(i).getRegexPatternCount() >= 80) {
					result = false;
				}else {
					result = true;
				}
			}
		}*/
		return result;
	}

	@Override
	public boolean AccuracyCheck(ColumnStatisticsModel profilerInfo) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
