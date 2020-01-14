package com.data.wrecker.validityDimension.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.wrecker.validityDimension.model.DataProfilerInfo;
import com.data.wrecker.validityDimension.model.DatasetStats;
import com.data.wrecker.validityDimension.repository.DataProfilerInfoRepo;
import com.data.wrecker.validityDimension.service.ValidityDimensionService;

@RestController
@RequestMapping("/dimension")
public class ValidityDimensionController {

	@Autowired
	ValidityDimensionService validityDimensionService;
	@Autowired
	private DataProfilerInfoRepo dataProfilerInfoRepository;
	private DataProfilerInfo dataprofilerInfo;
	private DatasetStats datasetStats;
	
	
	@RequestMapping(value = "/validityDimension", method = RequestMethod.GET)
	public String applyCompletenessDimension(@RequestParam String collectionName, @RequestParam String columnName, @RequestParam String[] wreckingIdsForDimension) throws JSONException {	
		List<String> wreckingIds = new ArrayList<String>();
		dataprofilerInfo = getDataprofileInfo(collectionName);
		datasetStats = getDatasetStats(dataprofilerInfo, columnName);
		String result = "";
		
		if(datasetStats.getProfilingInfo().getColumnDataType().equals("Integer") || datasetStats.getProfilingInfo().getColumnDataType().equals("Decimal")) {
			result = validityDimensionService.removeValidityDimension(collectionName, columnName,wreckingIdsForDimension.length);
		}else {
			result = collectionName;
		}
		
		return result;
	}
	

	
	private DataProfilerInfo getDataprofileInfo(String collectionName) {
		List<DataProfilerInfo> dataprofileInfoList = new ArrayList<DataProfilerInfo>();
		dataprofileInfoList = dataProfilerInfoRepository.findAll();
		DataProfilerInfo dataProfilerInfo = null;
		for(int i =0; i < dataprofileInfoList.size(); i++) {
			dataProfilerInfo = new DataProfilerInfo();
			if(dataprofileInfoList.get(i).getFileName().equals(collectionName)) {
				dataProfilerInfo = new DataProfilerInfo();
				dataProfilerInfo = dataprofileInfoList.get(i);
				break;
			}
	}
		return dataProfilerInfo;
}
	
	private DatasetStats getDatasetStats(DataProfilerInfo dataProfileInfo, String columnName) {
		List<DatasetStats> datasetStatsList = dataprofilerInfo.getDatasetStats();
		DatasetStats datasetStats = new DatasetStats();
		for(int ds=0; ds < datasetStatsList.size(); ds++) {
			
			if(datasetStatsList.get(ds).getColumnName().equals(columnName)) {
				datasetStats = datasetStatsList.get(ds);
				break;
			}
		}
		
		
		return datasetStats;
	}
	
}
