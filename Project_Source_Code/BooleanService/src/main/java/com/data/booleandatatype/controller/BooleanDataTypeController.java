package com.data.booleandatatype.controller;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.booleandatatype.model.DistinctValueList;
import com.data.booleandatatype.model.ProfilerInfo;
import com.data.booleandatatype.model.RegexInfo;
import com.data.booleandatatype.service.BooleanDataTypeService;




@RestController
@RequestMapping("/dimension")
public class BooleanDataTypeController {

	@Autowired
	BooleanDataTypeService booleanservice;
	
	@GetMapping("/booleanDimension")
	public String getDimensions()
	{
		ProfilerInfo profilerInfo = loadProfileInfoObj();
		JSONObject jsonObject = new JSONObject();
		jsonObject.append("NullCHeck", booleanservice.NullCheck(profilerInfo));
		jsonObject.append("Accuracy", booleanservice.AccuracyCheck(profilerInfo));
		jsonObject.append("Consistency",booleanservice.ConsistencyCheck(profilerInfo));
		jsonObject.append("Validity", booleanservice.ValidityCheck(profilerInfo));
		return jsonObject.toString();
	
	}	
	
	@RequestMapping("/abcd")
	public ProfilerInfo loadProfileInfoObj() {
		
		ArrayList<DistinctValueList> distinctValueList = new ArrayList();
		ArrayList<RegexInfo> regexInfoList =   new ArrayList();
		
		distinctValueList.add(new DistinctValueList("value1",5));
		distinctValueList.add(new DistinctValueList("value2",3));
		distinctValueList.add(new DistinctValueList("value3",2));
		regexInfoList.add(new RegexInfo("regex1", 50));
		regexInfoList.add(new RegexInfo("regex2", 5));
		regexInfoList.add(new RegexInfo("regex3", 2));
		
		ProfilerInfo profilerInfo = new ProfilerInfo(1,"colname","date",20,100,10,3,distinctValueList,2,9,2,5,3,10,20,15,10,20,regexInfoList);
		//profilerInfoImpl.create(profilerInfo);
		return profilerInfo;
	}
	
}
