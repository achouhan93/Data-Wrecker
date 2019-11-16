package com.data.decimaldatatypeservice.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.decimaldatatypeservice.model.DataProfilerInfo;
import com.data.decimaldatatypeservice.model.DataSetStats;
import com.data.decimaldatatypeservice.model.DimensionInfoModel;
import com.data.decimaldatatypeservice.model.Dimensions;
import com.data.decimaldatatypeservice.model.ProfilingInfoModel;
import com.data.decimaldatatypeservice.repository.DecimalDataTypeServiceRepository;
import com.data.decimaldatatypeservice.service.DecimalDataTypeServiceService;

@Service
@Transactional
public class DecimalDataTypeServiceServiceImpl implements DecimalDataTypeServiceService {
	
	@Autowired
	DecimalDataTypeServiceRepository decimalDataTypeServiceRepository;

	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public String getDecimalDataTypePrediction(int wreakingPercentage, String collectionName) {
		LOGGER.info("Inside getDecimalDataTypePrediction");
		/*wreakingPercentage = 20; // Hardcoded value for wreaking %

		int numberOfRecords = 100;*/

		// get header of the dataset
		List<String> columnHeader1 = new ArrayList<String>();
		
		int indivisualWreakingCountForDimentions = 0;
		LinkedHashSet<String> datadimention = new LinkedHashSet<String>();

		//LOGGER.info("indivisualWreakingCountForDimentions" + indivisualWreakingCountForDimentions);
		try {

			List<DataSetStats> dataSetStatsList = null;
			ProfilingInfoModel profilingInfoModel = new ProfilingInfoModel();
			List<DataProfilerInfo> datasetStatsList = decimalDataTypeServiceRepository.findAll();
			DataProfilerInfo dataProfilerInfo = new DataProfilerInfo();
			
			/*
			 * int consistancyCnt = 0; int completenessCnt = 0; int accuracyCnt = 0; int
			 * validaityCnt = 0;
			 */

			for (int datasetHeadersIterator = 0; datasetHeadersIterator < columnHeader1
					.size(); datasetHeadersIterator++) {
				
				DimensionInfoModel dimensionInfoModel = new DimensionInfoModel();
				List<Dimensions> DimensionsList = new ArrayList<Dimensions>();

				int consistancyCnt = 0;
				int positiveValidityCnt = 0;
				int negativeValidityCnt = 0;
				int completenessCnt = 0;
				int accuracyCnt = 0;

				dataProfilerInfo = getDatasetThroughColumnName(collectionName,
						columnHeader1.get(datasetHeadersIterator), datasetStatsList);
				dataSetStatsList = dataProfilerInfo.getDatasetStats();
				for (int j = 0; j < dataSetStatsList.size(); j++) {
					if (dataSetStatsList.get(j).getColumnName().equals(columnHeader1.get(datasetHeadersIterator))) {
						profilingInfoModel = dataSetStatsList.get(j).getProfilingInfo();
					}
					int numberOfRecords = profilingInfoModel.getColumnStats().getRowCount();
					indivisualWreakingCountForDimentions = ( ( (wreakingPercentage / 5) * numberOfRecords) / ( 100* columnHeader1.size() ) );
				}
				if (profilingInfoModel.getColumnDataType().equalsIgnoreCase("Decimal")) {
					for (int patternIterator = 0; patternIterator < profilingInfoModel.getPatternsIdentified()
							.size(); patternIterator++) {
						/*LOGGER.info("Pattern = "
								+ profilingInfoModel.getPatternsIdentified().get(patternIterator).getPattern()
								+ ", Occurance = "
								+ profilingInfoModel.getPatternsIdentified().get(patternIterator).getOccurance());*/
						String patternString = profilingInfoModel.getPatternsIdentified().get(patternIterator)
								.getPattern();
						int patternValue = profilingInfoModel.getPatternsIdentified().get(patternIterator)
								.getOccurance();
						// null value present?
						if (patternString.equals("")) {
							completenessCnt = completenessCnt + patternValue;
							//LOGGER.info("Completeness may be called");
						}
						// signed Decimal check
						else if (patternString.matches("(?<=\\s|^)[-+]?\\d+(?=\\s|$)")) {
							if (patternString.matches("^\\d+$")) {
								positiveValidityCnt = positiveValidityCnt + patternValue;
							} else {
								negativeValidityCnt = negativeValidityCnt + patternValue;
							}
						}
						// int value
						else if ((patternString.matches("^[-+]?\\d+(\\.\\d+)?$")
								|| patternString.matches("^[-+]?\\d+(\\,\\d+)?$"))
								&& (patternString.contains(".") || patternString.contains(","))) {
							consistancyCnt = consistancyCnt + patternValue;
							//LOGGER.info("Consistancy for integer may be called");
						} else {
							accuracyCnt = accuracyCnt + patternValue;
							//LOGGER.info("Accuracy may be called");
						}
					}
					if (indivisualWreakingCountForDimentions > completenessCnt) {
						datadimention.add("Completeness");
						Dimensions dimensions = new Dimensions();
						dimensions.setDimensionName("Completeness");
						dimensions.setReason("insufficient null values");
						dimensions.setStatus(true);
						dimensions.setRemainingWreakingCount(indivisualWreakingCountForDimentions - completenessCnt);
						DimensionsList.add(dimensions);
					}
					
					if (indivisualWreakingCountForDimentions > consistancyCnt) {
						datadimention.add("Consistancy");
						Dimensions dimensions = new Dimensions();
						dimensions.setDimensionName("Consistancy");
						dimensions.setReason("insufficient integer values");
						dimensions.setStatus(true);
						dimensions.setRemainingWreakingCount(indivisualWreakingCountForDimentions - consistancyCnt);
						DimensionsList.add(dimensions);
					}

					if (indivisualWreakingCountForDimentions > positiveValidityCnt) {
						datadimention.add("validaity");
						Dimensions dimensions = new Dimensions();
						dimensions.setDimensionName("validaity");
						dimensions.setReason("insufficient +ve Decimal values");
						dimensions.setRemainingWreakingCount(indivisualWreakingCountForDimentions - positiveValidityCnt);
						DimensionsList.add(dimensions);
					}
					if (indivisualWreakingCountForDimentions > negativeValidityCnt) {
						datadimention.add("validaity");
						Dimensions dimensions = new Dimensions();
						dimensions.setDimensionName("validaity");
						dimensions.setReason("insufficient -ve Decimal values");
						dimensions.setStatus(true);
						dimensions.setRemainingWreakingCount(indivisualWreakingCountForDimentions - negativeValidityCnt);
						DimensionsList.add(dimensions);
					}
					if (indivisualWreakingCountForDimentions > accuracyCnt) {
						datadimention.add("Accuracy");
						Dimensions dimensions = new Dimensions();
						dimensions.setDimensionName("Accuracy");
						dimensions.setReason("insufficient accurate values");
						dimensions.setStatus(true);
						dimensions.setRemainingWreakingCount(indivisualWreakingCountForDimentions - accuracyCnt);
						DimensionsList.add(dimensions);
					}
					if(wreakingPercentage > profilingInfoModel.getColumnStats().getDuplicateCount())
					{
						datadimention.add("Uniqueness");
						Dimensions dimensions = new Dimensions();
						dimensions.setDimensionName("Uniqueness");
						dimensions.setReason("insufficient uniqueness values");
						dimensions.setStatus(true);
						dimensions.setRemainingWreakingCount(wreakingPercentage - profilingInfoModel.getColumnStats().getDuplicateCount());
						DimensionsList.add(dimensions);
						
					}

					dimensionInfoModel.setDimensionsList(DimensionsList);
					dataSetStatsList.get(datasetHeadersIterator).setDimensionList(dimensionInfoModel);
				}

				
			}
			dataProfilerInfo.setDatasetStats(dataSetStatsList);
			decimalDataTypeServiceRepository.save(dataProfilerInfo);

		} catch (Exception e) {
			LOGGER.info("Exception " + e);
		}
		return "Success";
	}

	private DataProfilerInfo getDatasetThroughColumnName(String collectionName, String columnName,
			List<DataProfilerInfo> datasetStatsList) {
		DataProfilerInfo dataProfilerInfo = new DataProfilerInfo();
		for (int i = 0; i < datasetStatsList.size(); i++) {
			if (datasetStatsList.get(i).getFileName().equals(collectionName)) {
				dataProfilerInfo = datasetStatsList.get(i);
			}
		}

		return dataProfilerInfo;

	}

}
