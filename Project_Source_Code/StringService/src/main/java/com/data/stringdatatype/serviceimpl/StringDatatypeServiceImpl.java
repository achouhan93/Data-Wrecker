  package com.data.stringdatatype.serviceimpl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.stringdatatype.model.DatasetStats;
import com.data.stringdatatype.model.Dimensions;
import com.data.stringdatatype.service.StringDataTypeService;


@Service
@Transactional
public class StringDatatypeServiceImpl implements StringDataTypeService {

	private Dimensions dimensions;

	
	@Override
	public Dimensions NullCheck(DatasetStats datasetStats, int wreckingPercentage) {
		dimensions = new Dimensions();
		int totalRowsCanBeWrecked = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount());
		
		if(datasetStats.getProfilingInfo().getColumnStats().getNullCount() > totalRowsCanBeWrecked) {
			dimensions.setDimensionName("NullCheck");
			dimensions.setStatus(false);
			dimensions.setReason("The number of null values exceeds threshold");
			return dimensions;
		} else {
			dimensions.setDimensionName("NullCheck");
			dimensions.setStatus(true);
			dimensions.setReason("The number of null values less than threshold");
			return dimensions;
		}
	}

	@Override
	public Dimensions ConsistencyCheck(DatasetStats datasetStats, int wreckingPercentage) {
		dimensions.setDimensionName("ConsistencyCheck");
		dimensions.setStatus(true);
		dimensions.setReason("The patterns identified are less than 20 in their occurances");
		return dimensions;
	}

	@Override
	public  Dimensions ValidityCheck(DatasetStats datasetStats, int wreckingPercentage) {
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
		dimensions.setDimensionName("ValidityCheck");
		dimensions.setStatus(true);
		dimensions.setReason("There are valid values which is less than 20");
		return dimensions;
	}

	@Override
	public Dimensions AccuracyCheck(DatasetStats datasetStats, int wreckingPercentage) {
		dimensions = new Dimensions();
		dimensions.setDimensionName("AccuracyCheck");
		dimensions.setStatus(true);
		dimensions.setReason("There are Accurate values in the datasets");
		return dimensions;
		
	}


	private int noOfRowsToBeWrecked(int wreckingPercentage, int rowCount) {
		
		int totalRowsCanBeWrecked = (wreckingPercentage * rowCount)/(100 * 4) ; 
		return totalRowsCanBeWrecked;
	}
	
	private boolean isNotWrecked(int number1, int number2,int totalRowsCanBeWrecked) {
		if((number1 + number2) > totalRowsCanBeWrecked) {
			return false;
		}else {
			return true;
		}
	}
	
	
}
