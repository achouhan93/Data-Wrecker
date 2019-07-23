package com.data.datedatatype.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.datedatatype.model.DataProfilerInfo;
import com.data.datedatatype.repository.DatasetStatsInfoRepository;
import com.data.datedatatype.service.DatasetStatsProcessingService; 


@RestController
@RequestMapping("/dimension")
public class DateDataTypeController {
	
	@Autowired
	private DatasetStatsInfoRepository datasetStatsRepo;
	@Autowired
	private DatasetStatsProcessingService datasetStatsProcessingService;
	private List<DataProfilerInfo> datasetStatsList;



	@RequestMapping(value = "/datasetStats", method = RequestMethod.GET)
	public List<DataProfilerInfo> getStats() {
		datasetStatsList = datasetStatsRepo.findAll();
		return datasetStatsList;
	}
	
	@RequestMapping(value = "/dateDatatypeDimensions", method = RequestMethod.GET)
	public String getDimensionResults(@RequestParam String fileName, @RequestParam int wreckingPercentage){
		return datasetStatsProcessingService.getDimensionResults(fileName,wreckingPercentage);
	}
	
}
