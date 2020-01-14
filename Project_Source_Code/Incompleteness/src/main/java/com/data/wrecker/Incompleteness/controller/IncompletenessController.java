package com.data.wrecker.Incompleteness.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.wrecker.Incompleteness.model.DataProfilerInfo;
import com.data.wrecker.Incompleteness.model.DatasetStats;
import com.data.wrecker.Incompleteness.repository.DataProfilerInfoRepository;
import com.data.wrecker.Incompleteness.service.IncompletenessService;


@RestController
@RequestMapping("/dimension")
public class IncompletenessController {

	
	@Autowired
	private IncompletenessService completenessDimensionService;
	@Autowired
	private DataProfilerInfoRepository dataProfilerInfoRepository;
	private DataProfilerInfo dataprofilerInfo;
	private DatasetStats datasetStats;
	@RequestMapping(value = "/completenessDimension", method = RequestMethod.GET)
	public String applyCompletenessDimension(@RequestParam String collectionName, @RequestParam String columnName, @RequestParam int wreckingIdcount) {	

		dataprofilerInfo = getDataprofileInfo(collectionName);
		datasetStats = getDatasetStats(dataprofilerInfo, columnName);
		String result = "";
		
		switch(datasetStats.getProfilingInfo().getColumnDataType()) {
		
		case "Boolean":
			result = completenessDimensionService.removeValuesBoolean(collectionName, columnName, wreckingIdcount, datasetStats.getProfilingInfo().getColumnStats());
			break;
		case "Decimal":
			result = completenessDimensionService.removeValuesForDecimalAndInteger(collectionName, columnName, wreckingIdcount, datasetStats);
			break;
		case "Integer":
			result = completenessDimensionService.removeValuesForDecimalAndInteger(collectionName, columnName, wreckingIdcount, datasetStats);
			break;
		default:
			break;
		
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
