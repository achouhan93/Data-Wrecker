package com.data.booleandatatype.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.booleandatatype.model.DatasetStats;
import com.data.booleandatatype.model.Dimensions;
import com.data.booleandatatype.model.FrequencyOfColumnValues;
import com.data.booleandatatype.model.PatternModel;
import com.data.booleandatatype.service.BooleanDataTypeService;


@Service
@Transactional
public class BooleanDataTypeServiceImpl implements BooleanDataTypeService {

	
	private Dimensions dimensions;
	
	@Override
	public Dimensions NullCheck(DatasetStats datasetStats,int avgWreckingCount) {
		
		dimensions = new Dimensions();
		//int avgWrecking = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount());
		
		if(datasetStats.getProfilingInfo().getColumnStats().getNullCount() > avgWreckingCount) {
			dimensions.setDimensionName("Completeness");
			dimensions.setStatus(false);
			dimensions.setReason("The number of null values exceeds the desired count");
			dimensions.setRemainingWreakingCount(avgWreckingCount - datasetStats.getProfilingInfo().getColumnStats().getNullCount());
			return dimensions;
		} else {
			dimensions.setDimensionName("Completeness");
			dimensions.setStatus(true);
			dimensions.setReason("The number of null values less than the desired count");
			dimensions.setRemainingWreakingCount(avgWreckingCount - datasetStats.getProfilingInfo().getColumnStats().getNullCount());
			return dimensions;
		}
	}
	
	@Override
	public Dimensions ConsistencyCheck(DatasetStats datasetStats,int avgWrecking) {
		dimensions = new Dimensions();
		 //int avgWrecking = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount());
		if(datasetStats.getProfilingInfo().getPatternsIdentified().size() > 2) {
			if(isConsistent(datasetStats, avgWrecking)) {
				dimensions.setDimensionName("Consistency");
				dimensions.setStatus(true);
				dimensions.setReason("The patterns identified are less than the desired count");
				return dimensions;
			}else {
				dimensions.setDimensionName("Consistency");
				dimensions.setStatus(false);
				dimensions.setReason("The patterns identified are greater than the desired count");
				return dimensions;
			}
			
		}else {
			dimensions.setDimensionName("Consistency");
			dimensions.setStatus(true);
			dimensions.setReason("The patterns identified are less than the desired count");
			dimensions.setRemainingWreakingCount(avgWrecking);
			return dimensions;
		}	
	}

	@Override
	public Dimensions ValidityCheck(DatasetStats datasetStats,int avgWrecking) {	
		
		int totalRowsCount = datasetStats.getProfilingInfo().getColumnStats().getRowCount();
		int count = 0;
		dimensions = new Dimensions();
		
		List<PatternModel> patternModelList = datasetStats.getProfilingInfo().getPatternsIdentified();
		for(int i =0; i < patternModelList.size(); i++) {
			if(patternModelList.get(i).getPattern().toLowerCase().contains("x")) {
				count = count + patternModelList.get(i).getOccurance();
			}
		}
		
		//int avgWrecking = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount());
		if(count <  totalRowsCount ) {
			dimensions.setDimensionName("Validity");
			dimensions.setStatus(true);
			dimensions.setReason("The column contains valid values");
			dimensions.setRemainingWreakingCount(avgWrecking - count);
			return dimensions;
		}else {
			dimensions.setDimensionName("Validity");
			dimensions.setStatus(false);
			dimensions.setRemainingWreakingCount(avgWrecking - count);
			dimensions.setReason("The column contains valid values");
			return dimensions;
		}
	}

	@Override
	public Dimensions AccuracyCheck(DatasetStats datasetStats,int avgWrecking) {
		
		List<FrequencyOfColumnValues> distinctValues = datasetStats.getProfilingInfo().getColumnStats().getFrequencyOfColumnValues();
		int inaccurateCount = 0;
		//int avgWrecking = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount());
		dimensions = new Dimensions();
		
		for(int i =0; i < distinctValues.size(); i++) {
			if(!distinctValues.get(i).getColumnDistinctValue().toLowerCase().matches("^([Tt][Rr][Uu][Ee]|[Ff][Aa][Ll][Ss][Ee]|[0-1])$")) {
				
				if(distinctValues.get(i).getColumnDistinctValue().toLowerCase().contains("t") || distinctValues.get(i).getColumnDistinctValue().toLowerCase().contains("f") || distinctValues.get(i).getColumnDistinctValue().toLowerCase().contains("0") || distinctValues.get(i).getColumnDistinctValue().toLowerCase().contains("1")) {
					inaccurateCount = inaccurateCount + distinctValues.get(i).getColumnDistinctValueOccurance();
				}
			}
			
		}
		if(inaccurateCount > avgWrecking) {
		
			dimensions.setDimensionName("Accuracy");
			dimensions.setStatus(false);
			dimensions.setReason("The column contains Inaccurate values");	
			return dimensions;
			
		}else {
			dimensions.setDimensionName("Accuracy");
			dimensions.setStatus(true);
			dimensions.setReason("The column contains Accurate values");
			dimensions.setRemainingWreakingCount(avgWrecking - inaccurateCount);
			return dimensions;
		}
		
	}

	


	
	private boolean isConsistent(DatasetStats datasetStats, int avgWrecking) {
		int totalCount = 0;
	
		List<PatternModel> patternModelList = datasetStats.getProfilingInfo().getPatternsIdentified(); 
				
		if(patternModelList.size() > 2 ) {
			
			for(int i=2; i< patternModelList.size(); i++) {			
				if(!patternModelList.get(i).getPattern().toLowerCase().contains("x")) {
					totalCount = totalCount + patternModelList.get(i).getOccurance();
				}
			}
			
		}
				
		if(totalCount > avgWrecking) {
			return false;
		} else {
			return true;
		}
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
	
	/*private int noOfRowsToBeWrecked(int wreckingPercentage, int rowCount) {
		
		int totalRowsCanBeWrecked = (wreckingPercentage * rowCount)/(100 * 5) ; 
		return totalRowsCanBeWrecked;
	}
	*/
	
}
