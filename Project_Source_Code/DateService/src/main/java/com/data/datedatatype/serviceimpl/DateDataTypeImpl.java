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


	private Dimensions dimensions;
	private int invalidValues = 0;

	/*@Override
	public Dimensions NullCheck(DatasetStats datasetStats, int wreckingPercentage) {
		
		dimensions = new Dimensions();
		//int totalRowsCanBeWrecked = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount());
		
		if(datasetStats.getProfilingInfo().getColumnStats().getNullCount() > wreckingPercentage) {
			dimensions.setDimensionName("Completeness");
			dimensions.setStatus(false);
			dimensions.setRemainingWreakingCount(wreckingPercentage - datasetStats.getProfilingInfo().getColumnStats().getNullCount());
			dimensions.setReason("The number of null values exceeds threshold");
			return dimensions;
		} else {
			dimensions.setDimensionName("Completeness");
			dimensions.setStatus(true);
			dimensions.setRemainingWreakingCount(wreckingPercentage - datasetStats.getProfilingInfo().getColumnStats().getNullCount());
			dimensions.setReason("The number of null values less than threshold");
			return dimensions;
		}
	}*/

	@Override
	public Dimensions ConsistencyCheck(DatasetStats datasetStats, int totalRowsCanBeWrecked) {
		dimensions = new Dimensions();
		//int totalRowsCanBeWrecked = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount());
		
		if(datasetStats.getProfilingInfo().getPatternsIdentified().size() > 1) {
			if(isConsistent(datasetStats,totalRowsCanBeWrecked)) {
				dimensions.setDimensionName("Consistency");
				dimensions.setStatus(true);
				dimensions.setRemainingWreakingCount(totalRowsCanBeWrecked- numberOfinConsistentValues(datasetStats, totalRowsCanBeWrecked));
				dimensions.setReason("The patterns identified are less than the desired percentage");
				return dimensions;
			}else {
				dimensions.setDimensionName("Consistency");
				dimensions.setStatus(false);
				dimensions.setRemainingWreakingCount(totalRowsCanBeWrecked- numberOfinConsistentValues(datasetStats, totalRowsCanBeWrecked));
				dimensions.setReason("The patterns identified are greater than the desired percentage");
				return dimensions;
			}
			
		}else {
			dimensions.setDimensionName("Consistency");
			dimensions.setStatus(true);
			dimensions.setRemainingWreakingCount(totalRowsCanBeWrecked- numberOfinConsistentValues(datasetStats, totalRowsCanBeWrecked));
			dimensions.setReason("The patterns identified are less than the desired percentage");
			return dimensions;
		}	
	}

	@Override
	public Dimensions ValidityCheck(DatasetStats datasetStats, int totalRowsCanBeWrecked) {
		dimensions = new Dimensions();
		//int totalRowsCanBeWrecked = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount());
		int totalCount = 0;
		List<PatternModel> patternModelList = datasetStats.getProfilingInfo().getPatternsIdentified();
		if(patternModelList.size() > 1) {
			
			for(int i=0; i< patternModelList.size(); i++) {			
			
				if((patternModelList.get(i).getPattern().toLowerCase().contains("x") ||patternModelList.get(i).getPattern().toLowerCase().contains("0") )) {
					totalCount = totalCount + patternModelList.get(i).getOccurance();
				}
			}
			
		}
		
		if(totalCount > totalRowsCanBeWrecked) {
			dimensions.setDimensionName("Validity");
			dimensions.setStatus(true);
			dimensions.setReason("There are valid values which is less than 20");
			dimensions.setRemainingWreakingCount(totalRowsCanBeWrecked - invalidValues);
			return dimensions;
		}else {
			dimensions.setDimensionName("Validity");
			dimensions.setStatus(false);
			dimensions.setReason("There are Invalid values which is greater than 20");
			dimensions.setRemainingWreakingCount(totalRowsCanBeWrecked - invalidValues);
			return dimensions;
		}
	}

	@Override
	public Dimensions AccuracyCheck(DatasetStats datasetStats, int totalRowsCanBeWrecked) {
		// int totalRowsCanBeWrecked = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount());
		dimensions = new Dimensions();
		dimensions.setDimensionName("Accuracy");
		dimensions.setStatus(true);
		dimensions.setReason("There are Accurate values in the datasets");
		dimensions.setRemainingWreakingCount(totalRowsCanBeWrecked);
		return dimensions;
		
	}
	
	private boolean isConsistent(DatasetStats datasetStats,int totalRowsCanBeWrecked) {
		int totalCount = 0;
		List<PatternModel> patternModelList = datasetStats.getProfilingInfo().getPatternsIdentified(); 
		ArrayList<Integer> regexCounts = new ArrayList<Integer>();
		if(patternModelList.size() > 1) {
			
			for(int i=0; i< patternModelList.size(); i++) {			
			
				if(!(patternModelList.get(i).getPattern().toLowerCase().contains("x") ||patternModelList.get(i).getPattern().toLowerCase().contains("0") )) {
					regexCounts.add(patternModelList.get(i).getOccurance());
				}
			}
			
		}
		
		int maxValue = Collections.max(regexCounts);
		if((totalCount - maxValue) > totalRowsCanBeWrecked) {
			return false;
		} else {
			return true;
		}
	}
	
	private int numberOfinConsistentValues(DatasetStats datasetStats,int totalRowsCanBeWrecked) {
		int totalCount = 0;
		
		
		List<PatternModel> patternModelList = datasetStats.getProfilingInfo().getPatternsIdentified(); 
		ArrayList<Integer> regexCounts = new ArrayList<Integer>();
		
		if(patternModelList.size() > 1) {
			
			for(int i=0; i< patternModelList.size(); i++) {			
			
				if(!(patternModelList.get(i).getPattern().toLowerCase().contains("x") ||patternModelList.get(i).getPattern().toLowerCase().contains("0") )) {
					regexCounts.add(patternModelList.get(i).getOccurance());
					totalCount = totalCount + patternModelList.get(i).getOccurance();
				}
			}
			
		}
		
		//int maxValue = Collections.max(regexCounts);
		return totalRowsCanBeWrecked;
		
	}

	@Override
	public Dimensions UniquenessCheck(DatasetStats datasetStats, int avgWreckingCount) {
		dimensions = new Dimensions();
		dimensions.setDimensionName("Uniqueness");
		dimensions.setStatus(true);
		dimensions.setReason("Uniqueness is performed at the record level");	
		dimensions.setRemainingWreakingCount(avgWreckingCount);
		return dimensions;
	}
	
	
	/*private boolean isValid(DatasetStats datasetStats,int totalRowsCanBeWrecked) {
			
		
		if(!(datasetStats.getProfilingInfo().getColumnStats().getMinLength() == datasetStats.getProfilingInfo().getColumnStats().getMaxLength() && 
				datasetStats.getProfilingInfo().getColumnStats().getMaxLength() == datasetStats.getProfilingInfo().getColumnStats().getAverageLength())) {
			int minLength = datasetStats.getProfilingInfo().getColumnStats().getMinLength();
			int maxLength = datasetStats.getProfilingInfo().getColumnStats().getMaxLength();
			int avgLength = datasetStats.getProfilingInfo().getColumnStats().getAverageLength();
			int maxValue = getMaxValue(minLength, maxLength, avgLength);
			
			if(maxValue == avgLength) {
				return isNotWrecked(minLength, maxLength,totalRowsCanBeWrecked);
			}else if(maxValue == maxLength){
				return isNotWrecked(minLength, avgLength,totalRowsCanBeWrecked);
			}else {
				return isNotWrecked(avgLength, maxLength,totalRowsCanBeWrecked);
			}
		}else {
			return true;
		}		
	}
	*/

	/*private int getMaxValue(int number1, int number2, int number3) {
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
	
	private boolean isNotWrecked(int number1, int number2,int totalRowsCanBeWrecked) {
		if((number1 + number2) > totalRowsCanBeWrecked) {
			invalidValues = number1 + number2 - totalRowsCanBeWrecked;
			return false;
		}else {
			invalidValues = number1 + number2 - totalRowsCanBeWrecked;
			return true;
		}
	}
	
	private int noOfRowsToBeWrecked(int wreckingPercentage, int rowCount) {	
		int totalRowsCanBeWrecked = (wreckingPercentage * rowCount)/(100 * 4) ; 
		return totalRowsCanBeWrecked;
	}*/
	
	
}
