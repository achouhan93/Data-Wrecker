package com.data.chardatatype.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.chardatatype.model.DatasetStats;
import com.data.chardatatype.model.Dimensions;
import com.data.chardatatype.model.PatternModel;
import com.data.chardatatype.service.CharacterDataTypeService;


@Service
@Transactional
public class CharacterDatatypeServiceImpl  implements CharacterDataTypeService{

	private Dimensions dimensions;
	
	@Override
	public Dimensions NullCheck(DatasetStats datasetStats,int wreckingPercentage) {
		
		dimensions = new Dimensions();
		int avgWrecking = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount());
		if(datasetStats.getProfilingInfo().getColumnStats().getNullCount() > avgWrecking) {
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
	public Dimensions ConsistencyCheck(DatasetStats datasetStats,int wreckingPercentage) {
		
		dimensions = new Dimensions();
		dimensions.setDimensionName("ConsistencyCheck");
		dimensions.setStatus(true);
		dimensions.setReason("The patterns identified are less than 20 in their occurances");
		return dimensions;
	}

	@Override
	public Dimensions ValidityCheck(DatasetStats datasetStats,int wreckingPercentage) {
		dimensions = new Dimensions();
		int avgWrecking = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount());
		List<PatternModel> patternModelList = datasetStats.getProfilingInfo().getPatternsIdentified();
		int count = 0;
		for(int i=0; i < patternModelList.size(); i++ ) {
			if(patternModelList.get(i).getPattern().length() > 1) {
				count = count + patternModelList.get(i).getOccurance();
			}
		}
		
		if(count > avgWrecking) {
			dimensions.setDimensionName("ValidityCheck");
			dimensions.setStatus(false);
			dimensions.setReason("There are Invalid values which is greater than 20");
			return dimensions;
		}else {
			dimensions.setDimensionName("ValidityCheck");
			dimensions.setStatus(true);
			dimensions.setReason("There are valid values which is lesser than 20");
			return dimensions;
		}
	}

	@Override
	public Dimensions AccuracyCheck(DatasetStats datasetStats,int wreckingPercentage) {
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
	
}
