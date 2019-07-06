package com.data.datedatatype.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.datedatatype.model.DatasetStats;
import com.data.datedatatype.model.Dimensions;
import com.data.datedatatype.model.PatternModel;
import com.data.datedatatype.service.DateDataTypeService;


@Service
@Transactional
public class DateDataTypeImpl implements DateDataTypeService{

	// private ProfilerInfoRepository profilerInfoRepo;
	// private DatasetStats datasetStats;
	
	private Dimensions dimensions;

	@Override
	public Dimensions NullCheck(DatasetStats datasetStats) {
		
		dimensions = new Dimensions();
		
		if(datasetStats.getColumnStats().getNullCount() > 20) {
			dimensions.setDimensionName("NullCheck");
			dimensions.setStatus(false);
			dimensions.setReason("The number of null values exceeds 20");
			return dimensions;
		} else {
			dimensions.setDimensionName("NullCheck");
			dimensions.setStatus(true);
			dimensions.setReason("The number of null values less than 20");
			return dimensions;
		}
	}

	@Override
	public Dimensions ConsistencyCheck(DatasetStats datasetStats) {
		dimensions = new Dimensions();
		
		if(datasetStats.getPropertyModel().getPatternModel().size() > 1) {
			if(isConsistent(datasetStats)) {
				dimensions.setDimensionName("ConsistencyCheck");
				dimensions.setStatus(true);
				dimensions.setReason("The patterns identified are less than 20 in their occurances");
				return dimensions;
			}else {
				dimensions.setDimensionName("ConsistencyCheck");
				dimensions.setStatus(false);
				dimensions.setReason("The patterns identified are greater than 20 in their occurances");
				return dimensions;
			}
			
		}else {
			dimensions.setDimensionName("ConsistencyCheck");
			dimensions.setStatus(true);
			dimensions.setReason("The patterns identified are less than 20 in their occurances");
			return dimensions;
		}	
	}

	@Override
	public Dimensions ValidityCheck(DatasetStats datasetStats) {
		dimensions = new Dimensions();
		if(isValid(datasetStats)) {
			dimensions.setDimensionName("ValidityCheck");
			dimensions.setStatus(true);
			dimensions.setReason("There are valid values which is less than 20");
			return dimensions;
		}else {
			dimensions.setDimensionName("ValidityCheck");
			dimensions.setStatus(false);
			dimensions.setReason("There are Invalid values which is greater than 20");
			return dimensions;
		}
	}

	@Override
	public Dimensions AccuracyCheck(DatasetStats datasetStats) {
		dimensions = new Dimensions();
		dimensions.setDimensionName("AccuracyCheck");
		dimensions.setStatus(true);
		dimensions.setReason("There are Accurate values in the datasets");
		return dimensions;
		
	}
	
	
	
	/*private int getAvgWrecking(int wreckingPercentage, int totalRows) {
		return (wreckingPercentage * totalRows)/4;
	}*/
	

	private boolean isConsistent(DatasetStats datasetStats) {
		int totalCount = 0;
		List<PatternModel> patternModelList = datasetStats.getPropertyModel().getPatternModel(); 
		ArrayList<Integer> regexCounts = new ArrayList<Integer>();
		for(int i=0; i< patternModelList.size(); i++) {			
			regexCounts.add(patternModelList.get(i).getOccurance());
			totalCount = totalCount + patternModelList.get(i).getOccurance();			
		}
		int maxValue = Collections.max(regexCounts);
		if((totalCount - maxValue) > 20) {
			return false;
		} else {
			return true;
		}
	}
	
	
	private boolean isValid(DatasetStats datasetStats) {
			
		
		if(!(datasetStats.getColumnStats().getMinLength() == datasetStats.getColumnStats().getMaxLength() && 
				datasetStats.getColumnStats().getMaxLength() == datasetStats.getColumnStats().getAverageLength())) {
			int minLength = datasetStats.getColumnStats().getMinLength();
			int maxLength = datasetStats.getColumnStats().getMaxLength();
			int avgLength = datasetStats.getColumnStats().getAverageLength();
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
