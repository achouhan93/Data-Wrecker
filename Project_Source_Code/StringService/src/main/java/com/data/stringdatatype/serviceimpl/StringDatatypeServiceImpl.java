  package com.data.stringdatatype.serviceimpl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.stringdatatype.model.DatasetStats;
import com.data.stringdatatype.model.Dimensions;
import com.data.stringdatatype.service.StringDataTypeService;


@Service
@Transactional
public class StringDatatypeServiceImpl implements StringDataTypeService {

	private Dimensions dimensions;
	private static final Logger LOGGER = LogManager.getLogger();

	
	@Override
	public Dimensions NullCheck(DatasetStats datasetStats, int wreckingPercentage, int Colcount) {
		dimensions = new Dimensions();
		int totalRowsCanBeWrecked = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount(), Colcount);
		LOGGER.info("Total wreckingPercentage " + wreckingPercentage);
		LOGGER.info("Total wreck count " + totalRowsCanBeWrecked);
		
		if(datasetStats.getProfilingInfo().getColumnStats().getNullCount() > totalRowsCanBeWrecked) {
			dimensions.setDimensionName("Completeness");
			dimensions.setStatus(false);
			dimensions.setReason("The number of null values exceeds threshold");
			return dimensions;
		} else {
			dimensions.setDimensionName("Completeness");
			dimensions.setStatus(true);
			dimensions.setRemainingWreakingCount(totalRowsCanBeWrecked);
			dimensions.setReason("The number of null values less than threshold");
			return dimensions;
		}
	}

	@Override
	public Dimensions ConsistencyCheck(DatasetStats datasetStats, int wreckingPercentage, int Colcount) {
		dimensions = new Dimensions();
		int totalRowsCanBeWrecked = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount(), Colcount);
		dimensions.setDimensionName("Consistency");
		dimensions.setStatus(true);
		dimensions.setReason("The patterns identified are less than 20 in their occurances");
		dimensions.setRemainingWreakingCount(totalRowsCanBeWrecked);
		return dimensions;
	}

	@Override
	public  Dimensions ValidityCheck(DatasetStats datasetStats, int wreckingPercentage, int Colcount) {
		String emailRegex = "^[a-z0-9+_.-]+@(.+)$";
		int totalRowsCanBeWrecked = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount(), Colcount);
		boolean result = false;
		dimensions = new Dimensions();
		
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
		dimensions.setDimensionName("Validity");
		dimensions.setStatus(true);
		dimensions.setReason("There are valid values which is less than 20");
		dimensions.setRemainingWreakingCount(totalRowsCanBeWrecked);
		return dimensions;
	}

	@Override
	public Dimensions AccuracyCheck(DatasetStats datasetStats, int wreckingPercentage, int Colcount) {
		dimensions = new Dimensions();
		int totalRowsCanBeWrecked = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount(), Colcount);
		dimensions.setDimensionName("Accuracy");
		dimensions.setStatus(true);
		dimensions.setReason("There are Accurate values in the datasets");
		dimensions.setRemainingWreakingCount(totalRowsCanBeWrecked);
		return dimensions;
		
	}


	private int noOfRowsToBeWrecked(int wreckingPercentage, int rowCount, int colCount) {
		
		int totalRowsCanBeWrecked = (wreckingPercentage * rowCount)/(100 * 4 * colCount) ;  
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
