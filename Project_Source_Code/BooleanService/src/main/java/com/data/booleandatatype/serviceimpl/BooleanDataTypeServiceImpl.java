package com.data.booleandatatype.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.booleandatatype.model.DatasetStats;
import com.data.booleandatatype.model.Dimensions;
import com.data.booleandatatype.model.PatternModel;
import com.data.booleandatatype.service.BooleanDataTypeService;


@Service
@Transactional
public class BooleanDataTypeServiceImpl implements BooleanDataTypeService {

	int avgWrecking = 20;
	private Dimensions dimensions;
	
	@Override
	public Dimensions NullCheck(DatasetStats datasetStats) {
		
		dimensions = new Dimensions();
		
		if(datasetStats.getColumnStats().getNullCount() > avgWrecking) {
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
		int trueCount = datasetStats.getColumnStats().getTrueCount();
		int falseCount = datasetStats.getColumnStats().getFalseCount();
		int nullCount = datasetStats.getColumnStats().getNullCount();
		int totalRowsCount = datasetStats.getColumnStats().getRowCount();
		if((trueCount + falseCount + nullCount ) == totalRowsCount ) {
			dimensions.setDimensionName("ValidityCheck");
			dimensions.setStatus(true);
			dimensions.setReason("The column contains valid values");
			return dimensions;
		}else if((totalRowsCount - (trueCount + falseCount + nullCount)) > avgWrecking ) {
			dimensions.setDimensionName("ValidityCheck");
			dimensions.setStatus(false);
			dimensions.setReason("The column contains Invalid values");
			return dimensions;
		}else {
			dimensions.setDimensionName("ValidityCheck");
			dimensions.setStatus(true);
			dimensions.setReason("The column contains valid values");
			return dimensions;
		}
	}

	@Override
	public Dimensions AccuracyCheck(DatasetStats datasetStats) {
		dimensions.setDimensionName("AccuracyCheck");
		dimensions.setStatus(true);
		dimensions.setReason("The column contains Accurate values");
		return dimensions;
	}

	


	
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
	
	
}
