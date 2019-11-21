  package com.data.stringdatatype.serviceimpl;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.stringdatatype.model.DatasetStats;
import com.data.stringdatatype.model.Dimensions;
import com.data.stringdatatype.model.PatternModel;
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
		/*LOGGER.info("Total wreckingPercentage " + wreckingPercentage);
		LOGGER.info("Total wreck count " + totalRowsCanBeWrecked);*/
		
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
		List<PatternModel> patternModel= new ArrayList<PatternModel>();
		patternModel = datasetStats.getProfilingInfo().getPatternsIdentified();
		int count = 0;
		for(int i=0; i< patternModel.size();i++) {
			if(!isConsistentValue(patternModel.get(i))) {
				count = patternModel.get(i).getOccurance();
			}
		}
		if(count > totalRowsCanBeWrecked) {
			dimensions.setDimensionName("Consistency");
			dimensions.setStatus(false);
			dimensions.setReason("The patterns identified are greater than the wrecking count ");
			dimensions.setRemainingWreakingCount(0);
			
		}else {
			
			dimensions.setDimensionName("Consistency");
			dimensions.setStatus(true);
			dimensions.setReason("The patterns identified are less than the wrecking count ");
			dimensions.setRemainingWreakingCount(totalRowsCanBeWrecked - count);
				
		}
		return dimensions;
	}

	@Override
	public  Dimensions ValidityCheck(DatasetStats datasetStats, int wreckingPercentage, int Colcount) {
		String emailRegex = "^[a-z0-9+_.-]+@(.+)$";
		int totalRowsCanBeWrecked = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount(), Colcount);
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
		
		List<PatternModel> patternModel= new ArrayList<PatternModel>();
		patternModel = datasetStats.getProfilingInfo().getPatternsIdentified();
		int count = 0;
		for(int i=0; i< patternModel.size();i++) {
			if(!isValidString(patternModel.get(i))) {
				count = patternModel.get(i).getOccurance();
			}
		}
		
		if(count > totalRowsCanBeWrecked) {
			dimensions.setDimensionName("Validity");
			dimensions.setStatus(false);
			dimensions.setReason("The patterns identified are greater than the wrecking count ");
			dimensions.setRemainingWreakingCount(0);
			
		}else {
			
			dimensions.setDimensionName("Validity");
			dimensions.setStatus(true);
			dimensions.setReason("The patterns identified are less than the wrecking count ");
			dimensions.setRemainingWreakingCount(totalRowsCanBeWrecked - count);
				
		}
		return dimensions;
	}

	@Override
	public Dimensions AccuracyCheck(DatasetStats datasetStats, int wreckingPercentage, int Colcount) {
		dimensions = new Dimensions();
		int totalRowsCanBeWrecked = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount(), Colcount);
		
		List<PatternModel> patternModel= new ArrayList<PatternModel>();
		patternModel = datasetStats.getProfilingInfo().getPatternsIdentified();
		int count = 0;
		for(int i=0; i< patternModel.size();i++) {
			if(!isAccurate(patternModel.get(i))) {
				count = patternModel.get(i).getOccurance();
			}
		}
		
		if(count > totalRowsCanBeWrecked) {
			dimensions.setDimensionName("Accuracy");
			dimensions.setStatus(false);
			dimensions.setReason("The patterns identified are greater than the wrecking count ");
			dimensions.setRemainingWreakingCount(0);
			
		}else {
			
			dimensions.setDimensionName("Accuracy");
			dimensions.setStatus(true);
			dimensions.setReason("The patterns identified are less than the wrecking count ");
			dimensions.setRemainingWreakingCount(totalRowsCanBeWrecked - count);
				
		}
		return dimensions;
		
	}


	private int noOfRowsToBeWrecked(int wreckingPercentage, int rowCount, int colCount) {
		
		int totalRowsCanBeWrecked = (wreckingPercentage * rowCount)/(100 * 4 * colCount) ;  
		return totalRowsCanBeWrecked;
		
	}
	
	private boolean isConsistentValue(PatternModel patternModel) {
		String strValue =patternModel.getPattern().trim();
		int spaceCount = strValue.length() - strValue.replaceAll(" ", "").length();
		char[] stringArray = strValue.toCharArray();
		int uppercaseCharcount = 0;
		int lowercaseCount = 0;
		for(int i=0; i< stringArray.length; i++) {
			if(Character.isUpperCase(stringArray[i])) {
				uppercaseCharcount++;
			}else {
				lowercaseCount++;
			}
		}
		
		if((uppercaseCharcount + spaceCount + lowercaseCount) == strValue.length()) {
			return true;
		}else if(uppercaseCharcount > ( spaceCount + 1 ) ) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean isValidString(PatternModel patternModel) {
		String specialCharsPattern = "-/@#$%^&_+=()";
		String patternValue = patternModel.getPattern();
		if(patternValue.matches("["+specialCharsPattern+"]")) {
			return false;
		}else {
			return true;
		}
	}
	
	private boolean  isAccurate(PatternModel patternModel) {
		String emailRegex = "^[a-z0-9+_.-]+@(.+)$";
		String patternValue = patternModel.getPattern();
		if(patternValue.matches(emailRegex)) {
			return true;
		}else if(patternValue == null || patternValue.trim().isEmpty()){
			return false;
		}else {
			int count = 0;
			char[] patternArray = patternValue.toCharArray();
			for(int i=0; i < patternArray.length; i++) {
				 if (Character.toString(patternArray[i]).matches("[^A-Za-z0-9 ]")) {
		             count++;
		         }
			}
			if(count > 3) {
				return false;
			}else {
				return true;
			}
		}
	}

	@Override
	public Dimensions UniquenessCheck(DatasetStats datasetStats, int avgWreckingCount, int Colcount) {
		
		int totalRowsCanBeWrecked = noOfRowsToBeWrecked(avgWreckingCount, datasetStats.getProfilingInfo().getColumnStats().getRowCount(), Colcount);
		dimensions = new Dimensions();
		dimensions.setDimensionName("Uniqueness");
		dimensions.setStatus(true);
		dimensions.setReason("Uniqueness is performed at the record level");	
		dimensions.setRemainingWreakingCount(totalRowsCanBeWrecked);
		return dimensions;
	}
	
}
