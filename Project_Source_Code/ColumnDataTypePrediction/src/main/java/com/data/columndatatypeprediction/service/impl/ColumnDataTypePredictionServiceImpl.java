package com.data.columndatatypeprediction.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.columndatatypeprediction.model.DataProfilerInfo;
import com.data.columndatatypeprediction.model.DataSetStats;
import com.data.columndatatypeprediction.model.ProfilingInfoModel;
import com.data.columndatatypeprediction.repositories.ColumnDataTypeRepository;
import com.data.columndatatypeprediction.service.ColumnDataTypePredictionService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
@Transactional
public class ColumnDataTypePredictionServiceImpl implements ColumnDataTypePredictionService {

	private static final Logger LOGGER = LogManager.getLogger();

	@Autowired
	ColumnDataTypeRepository columnPatternRepository;

	@Override
	public String getColumnDataTypePrediction(String collectionName, List<String> columnHeader)
			throws JSONException, JsonParseException, JsonMappingException, IOException {

		// get header of the dataset
		List<String> columnHeader1 = new ArrayList<String>();
		columnHeader1.add("eq_site_limit");
		columnHeader1.add("county");
		columnHeader1.add("statecode");
		columnHeader1.add("Date");
		List<DataSetStats> columnPatternModel = null;
		ProfilingInfoModel profilingInfoModel = new ProfilingInfoModel();
		List<DataProfilerInfo> datasetStatsList = columnPatternRepository.findAll();
		DataProfilerInfo dataProfilerInfo = new DataProfilerInfo();
		for (int datasetHeadersIterator = 0; datasetHeadersIterator < columnHeader1.size(); datasetHeadersIterator++) {
			dataProfilerInfo = getDatasetThroughColumnName(collectionName, columnHeader1.get(datasetHeadersIterator),
					datasetStatsList);
			columnPatternModel = dataProfilerInfo.getDatasetStats();
			for (int j = 0; j < columnPatternModel.size(); j++) {
				if (columnPatternModel.get(j).getColumnName().equals(columnHeader1.get(datasetHeadersIterator))) {
					profilingInfoModel = columnPatternModel.get(j).getProfilingInfo();
				}
			}
			Map<String, Integer> dataTypes = new TreeMap<String, Integer>();
			dataTypes.put("Boolean", 0);
			dataTypes.put("Character", 0);
			dataTypes.put("Double", 0);
			dataTypes.put("Integer", 0);
			dataTypes.put("String", 0);
			dataTypes.put("Date", 0);
			int doubleCnt = 0;
			int charCnt = 0;
			int boolCnt = 0;
			int intCnt = 0;
			int strCnt = 0;
			int dateCnt = 0;
			for (int patternIterator = 0; patternIterator < profilingInfoModel.getPatternsIdentified()
					.size(); patternIterator++) {

				LOGGER.info("Pattern = " + profilingInfoModel.getPatternsIdentified().get(patternIterator).getPattern()
						+ ", Occurance = "
						+ profilingInfoModel.getPatternsIdentified().get(patternIterator).getOccurance());
				String pattern = profilingInfoModel.getPatternsIdentified().get(patternIterator).getPattern();
				int patternOccurance = profilingInfoModel.getPatternsIdentified().get(patternIterator).getOccurance();
				int checkforDateWithdoubleSlashOrDot = 0;
				checkforDateWithdoubleSlashOrDot = StringUtils.countMatches(pattern, ".");
				checkforDateWithdoubleSlashOrDot = StringUtils.countMatches(pattern, "-");
				checkforDateWithdoubleSlashOrDot = StringUtils.countMatches(pattern, "/");
				// Can either be a character or a boolean.
				if (pattern.length() == 1) {
					// ----incomplete logic
					// 1st get the name of that column with pattern with size of 1
					// then get the complete data of that column
					// go through data and check if it contains 0/1. if yes it a boolean else
					// character.
					if (pattern.equals("1") || pattern.equals("0")) {
						boolCnt = boolCnt + patternOccurance;
						dataTypes.put("Boolean", boolCnt);
					} else {
						charCnt = charCnt + patternOccurance;
						dataTypes.put("Character", charCnt);
					}

				} else if (pattern.matches("(\\+|-)?([0-9]+(\\.[0-9]+))")
						|| pattern.matches("(\\+|-)?([0-9]+(\\,[0-9]+))")) {
					doubleCnt = doubleCnt + patternOccurance;
					dataTypes.put("Double", doubleCnt);
				} else if (pattern.matches("[0-9]+")) {
					intCnt = intCnt + patternOccurance;
					dataTypes.put("Integer", intCnt);
				} else if (checkforDateWithdoubleSlashOrDot == 2) {
					dateCnt = dateCnt + patternOccurance;
					dataTypes.put("Date", dateCnt);
				} else {
					strCnt = strCnt + patternOccurance;
					dataTypes.put("String", strCnt);
				}

			}
			System.out.println("dataTypes" + dataTypes);
			Map.Entry<String, Integer> maxEntry = null;
			// finding out max key value pair out of map
			for (Map.Entry<String, Integer> entry : dataTypes.entrySet()) {
				if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
					maxEntry = entry;
				}
			}
			System.out.println("data type for column: " + columnHeader1.get(datasetHeadersIterator) + " is : "
					+ maxEntry.getKey());
			profilingInfoModel.setColumnDataType(maxEntry.getKey());
			columnPatternModel.get(datasetHeadersIterator).setProfilingInfo(profilingInfoModel);
			dataProfilerInfo.setDatasetStats(columnPatternModel);
		}
		
		columnPatternRepository.save(dataProfilerInfo);
		return "Success";
	}

	private DataProfilerInfo getDatasetThroughColumnName(String collectionName, String columnName,
			List<DataProfilerInfo> datasetStatsList) {
		DataProfilerInfo dataProfilerInfo = new DataProfilerInfo();
		List<DataSetStats> columnPatternModel = null;
		ProfilingInfoModel profilingInfoModel = null;
		for (int i = 0; i < datasetStatsList.size(); i++) {
			if (datasetStatsList.get(i).getFileName().equals(collectionName)) {
				dataProfilerInfo = datasetStatsList.get(i);
				columnPatternModel = dataProfilerInfo.getDatasetStats();
				for (int j = 0; j < columnPatternModel.size(); j++) {
					if (columnPatternModel.get(j).getColumnName().equals(columnName)) {
						profilingInfoModel = columnPatternModel.get(j).getProfilingInfo();
						break;
					}
				}

			}
		}

		return dataProfilerInfo;
	}

}
