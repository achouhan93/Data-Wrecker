package com.data.stringdatatype.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.data.stringdatatype.model.ColumnStatisticsModel;
import com.data.stringdatatype.model.DimensionsResult;
import com.data.stringdatatype.repository.ProfilerInfoStringRepository;
import com.data.stringdatatype.service.StringDataTypeService;


@RestController
@RequestMapping("/dimension")
public class StringServiceController {

	 
	@Autowired
	StringDataTypeService stringService;
	@Autowired
	private ProfilerInfoStringRepository profilerInfoRepo;
	private List<ColumnStatisticsModel> columnStatisticsModel;  
	
	
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
		
		dimensionResult.setNullCheck(stringService.NullCheck(columnStatisticsModel.get(0)));
		dimensionResult.setAccuracyCheck(stringService.AccuracyCheck(columnStatisticsModel.get(0)));
		dimensionResult.setConsistencyCheck(stringService.AccuracyCheck(columnStatisticsModel.get(0)));
		dimensionResult.setValidityCheck(stringService.ValidityCheck(columnStatisticsModel.get(0)));
		dimensionResultList.add(dimensionResult);
		return dimensionResultList;
	}
	
	 
	/* @RequestMapping("/abcd")
		public ProfilerInfoString loadProfileInfoObj() {
			
			ArrayList<DistinctValueListString> distinctValueList = new ArrayList();
			ArrayList<RegexInfoString> regexInfoList =   new ArrayList();
			
			distinctValueList.add(new DistinctValueListString("value1",5));
			distinctValueList.add(new DistinctValueListString("value2",3));
			distinctValueList.add(new DistinctValueListString("value3",2));
			regexInfoList.add(new RegexInfoString("regex1", 50));
			regexInfoList.add(new RegexInfoString("regex2", 5));
			regexInfoList.add(new RegexInfoString("regex3", 2));
			
			ProfilerInfoString profilerInfo = new ProfilerInfoString(1,"colname","date",20,100,10,3,distinctValueList,2,9,2,5,3,10,20,15,10,20,regexInfoList);
			//profilerInfoImpl.create(profilerInfo);
			return profilerInfo;
		}*/
}
