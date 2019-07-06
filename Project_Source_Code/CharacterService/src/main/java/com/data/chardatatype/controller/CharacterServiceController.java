 package com.data.chardatatype.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.chardatatype.model.DatasetStats;
import com.data.chardatatype.model.Dimensions;
import com.data.chardatatype.repository.DatasetStatsInfoRepository;
import com.data.chardatatype.service.DatasetStatsProcessingService;

@RestController
@RequestMapping("/dimension")
public class CharacterServiceController {
	
	@Autowired
	private DatasetStatsInfoRepository datasetStatsRepo;
	@Autowired
	private DatasetStatsProcessingService datasetStatsProcessingService;
	private List<DatasetStats> datasetStatsList;



	@RequestMapping(value = "/datasetStats", method = RequestMethod.GET)
	public List<DatasetStats> getStats() {
		datasetStatsList = datasetStatsRepo.findAll();
		return datasetStatsList;
	}
	
	@RequestMapping(value = "/characterDatatypeDimensions", method = RequestMethod.GET)
	public List<Dimensions> getDimensionResults(@RequestParam String columnName){
		return datasetStatsProcessingService.getDimensionResults(columnName);
	}
	
}
