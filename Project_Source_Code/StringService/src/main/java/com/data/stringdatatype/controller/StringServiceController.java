package com.data.stringdatatype.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.stringdatatype.model.DataProfilerInfo;
import com.data.stringdatatype.repository.DatasetStatsInfoRepository;
import com.data.stringdatatype.service.DatasetStatsProcessingService;
import com.data.stringdatatype.service.StringDataTypeService;


@RestController
@RequestMapping("/dimension")
public class StringServiceController {

	 
	@Autowired
	StringDataTypeService stringService;
	@Autowired
	private DatasetStatsInfoRepository profilerInfoRepo;
	@Autowired
	private DatasetStatsProcessingService datasetStatsProcessingService;
	private List<DataProfilerInfo> columnStatisticsModel;  
	
	
	/*@RequestMapping(value = "/result", method = RequestMethod.GET)
	public String getDimensions()
	{
		ProfilerInfoString profilerInfo = loadProfileInfoObj();
		JSONObject jsonObject = new JSONObject();
		jsonObject.append("NullCHeck", stringService.NullCheck(profilerInfo));
		jsonObject.append("Accuracy", stringService.AccuracyCheck(profilerInfo));
		jsonObject.append("Consistency",stringService.ConsistencyCheck(profilerInfo));
		jsonObject.append("Validity", stringService.ValidityCheck(profilerInfo));
		return jsonObject.toString();
	}*/
	
	@RequestMapping(value = "/stringDatatypeDimensions", method = RequestMethod.GET)
	public String getDimensionResults(@RequestParam String fileName, @RequestParam int wreckingPercentage){
		return datasetStatsProcessingService.getDimensionResults(fileName,wreckingPercentage);
	}
	
	/*@RequestMapping(value = "/stringDatatypeDimensions", method = RequestMethod.GET)
	public String getDimensionResults(@RequestParam String fileName, @RequestParam int wreckingPercentage){
		List<DimensionsResult> dimensionResultList = new ArrayList<DimensionsResult>();
		DimensionsResult dimensionResult = new DimensionsResult();
		columnStatisticsModel = profilerInfoRepo.findAll();
		
		dimensionResult.setNullCheck(stringService.NullCheck(columnStatisticsModel.get(0)));
		dimensionResult.setAccuracyCheck(stringService.AccuracyCheck(columnStatisticsModel.get(0)));
		dimensionResult.setConsistencyCheck(stringService.AccuracyCheck(columnStatisticsModel.get(0)));
		dimensionResult.setValidityCheck(stringService.ValidityCheck(columnStatisticsModel.get(0)));
		dimensionResultList.add(dimensionResult);
		return "";
	}*/
}
