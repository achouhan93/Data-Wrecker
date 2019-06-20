package com.data.datedatatype.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.data.datedatatype.model.ColumnStatisticsModel;
import com.data.datedatatype.model.DimensionsResult;
import com.data.datedatatype.model.DistinctValueList;
import com.data.datedatatype.model.ProfilerInfo;
import com.data.datedatatype.model.RegexInfo;
import com.data.datedatatype.repository.ProfilerInfoRepository;
import com.data.datedatatype.repositoryimpl.ProfilerInfoRepositoryImpl;
import com.data.datedatatype.service.DateDataTypeService;
import com.data.datedatatype.serviceimpl.DateDataTypeImpl; 


@RestController
@RequestMapping("/dimension")
public class DateDataTypeController {
	
	@Autowired
	private ProfilerInfoRepository profilerInfoRepo;
	@Autowired
	private DateDataTypeService dateService;
	private List<ColumnStatisticsModel> columnStatisticsModel;  
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<ColumnStatisticsModel> getAllDocuments() {
		columnStatisticsModel = profilerInfoRepo.findAll();
	  return columnStatisticsModel;
	}
	
	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public List<DimensionsResult> getDimensionResults(){
		List<DimensionsResult> dimensionResultList = new ArrayList<DimensionsResult>();
		DimensionsResult dimensionResult = new DimensionsResult();
		columnStatisticsModel = profilerInfoRepo.findAll();
		
		dimensionResult.setNullCheck(dateService.NullCheck(columnStatisticsModel.get(0)));
		dimensionResult.setAccuracyCheck(dateService.AccuracyCheck(columnStatisticsModel.get(0)));
		dimensionResult.setConsistencyCheck(dateService.AccuracyCheck(columnStatisticsModel.get(0)));
		dimensionResult.setValidityCheck(dateService.ValidityCheck(columnStatisticsModel.get(0)));
		dimensionResultList.add(dimensionResult);
		return dimensionResultList;
	}
	
}
