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
	public Dimensions NullCheck(DatasetStats datasetStats,int avgWrecking) {

		dimensions = new Dimensions();
		//int avgWrecking = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount());
		if(datasetStats.getProfilingInfo().getColumnStats().getNullCount() > avgWrecking) {
			dimensions.setDimensionName("Completeness");
			dimensions.setStatus(false);
			dimensions.setReason("The number of null values exceeds the desired count");
			dimensions.setRemainingWreakingCount(avgWrecking - datasetStats.getProfilingInfo().getColumnStats().getNullCount());
			return dimensions;
		} else {
			dimensions.setDimensionName("Completeness");
			dimensions.setStatus(true);
			dimensions.setRemainingWreakingCount(avgWrecking - datasetStats.getProfilingInfo().getColumnStats().getNullCount());
			dimensions.setReason("The number of null values less than the desired count");
			return dimensions;
		}
	}

	@Override
	public Dimensions ConsistencyCheck(DatasetStats datasetStats,int avgWrecking) {

		List<PatternModel> patternsIdentified = datasetStats.getProfilingInfo().getPatternsIdentified();
		//int avgWrecking = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount());
		int patternXcount = 0;
		int patternxCount = 0;
		int dirtyPatternCount = 0;
		if(patternsIdentified.size() > 1) {

			for(int i =0; i < patternsIdentified.size(); i++ ) {
				if(patternsIdentified.get(i).getPattern().equals("X")) {
					patternXcount = patternXcount + patternsIdentified.get(i).getOccurance();
				}else if(patternsIdentified.get(i).getPattern().equals("x")) {
					patternxCount = patternxCount + patternsIdentified.get(i).getOccurance();
				}
			}
		}

		if(patternXcount > patternxCount) {
			dirtyPatternCount = patternXcount;
		}else {
			dirtyPatternCount = patternxCount;
		}

		if(avgWrecking > dirtyPatternCount) {

			dimensions = new Dimensions();
			dimensions.setDimensionName("Consistency");

			dimensions.setReason("The patterns identified are less than the desired count");
			if((avgWrecking - dirtyPatternCount) > 0) {
				dimensions.setStatus(true);
				dimensions.setRemainingWreakingCount(avgWrecking - dirtyPatternCount);

			}else {
				dimensions.setStatus(false);
			}

			return dimensions;

		}else {

			dimensions = new Dimensions();
			dimensions.setDimensionName("Consistency");
			dimensions.setStatus(false);
			dimensions.setReason("The patterns identified are greater than the desired count");
			// dimensions.setRemainingWreakingCount(avgWrecking - dirtyPatternCount);
			return dimensions;

		}

	}

	@Override
	public Dimensions ValidityCheck(DatasetStats datasetStats,int avgWrecking) {
		dimensions = new Dimensions();
		//int avgWrecking = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount());
		List<PatternModel> patternModelList = datasetStats.getProfilingInfo().getPatternsIdentified();
		int count = 0;
		for(int i=0; i < patternModelList.size(); i++ ) {
			if(patternModelList.get(i).getPattern().length() > 1) {
				count = count + patternModelList.get(i).getOccurance();
			}
		}

		if(count > avgWrecking) {
			dimensions.setDimensionName("Validity");
			dimensions.setStatus(false);
			dimensions.setReason("There are Invalid values are greater than the desired count");
			dimensions.setRemainingWreakingCount(avgWrecking);
			return dimensions;
		}else {
			dimensions.setDimensionName("Validity");
			dimensions.setStatus(true);
			dimensions.setReason("There are valid values are lesser than the desired count");
			dimensions.setRemainingWreakingCount(avgWrecking);
			return dimensions;
		}
	}

	@Override
	public Dimensions AccuracyCheck(DatasetStats datasetStats,int avgWrecking) {

		//int avgWrecking = noOfRowsToBeWrecked(wreckingPercentage, datasetStats.getProfilingInfo().getColumnStats().getRowCount());

		dimensions = new Dimensions();
		dimensions.setDimensionName("Accuracy");
		dimensions.setStatus(true);
		dimensions.setRemainingWreakingCount(avgWrecking);
		dimensions.setReason("There are Accurate values in the datasets");

		return dimensions;
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
}
