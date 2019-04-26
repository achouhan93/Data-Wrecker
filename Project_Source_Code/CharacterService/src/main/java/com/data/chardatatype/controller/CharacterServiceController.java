 package com.data.chardatatype.controller;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.chardatatype.model.DistinctValueListChar;
import com.data.chardatatype.model.ProfilerInfoChar;
import com.data.chardatatype.model.RegexInfoChar;
import com.data.chardatatype.service.CharacterDataTypeService;

@RestController
@RequestMapping("/dimension")
public class CharacterServiceController {
	
	@Autowired
	CharacterDataTypeService characterService;
	
	
	@GetMapping("/booleanDimension")
	public String getDimensions()
	{
		ProfilerInfoChar profilerInfo = loadProfileInfoObj();
		JSONObject jsonObject = new JSONObject();
		jsonObject.append("NullCHeck", characterService.NullCheck(profilerInfo));
		jsonObject.append("Accuracy", characterService.AccuracyCheck(profilerInfo));
		jsonObject.append("Consistency",characterService.ConsistencyCheck(profilerInfo));
		jsonObject.append("Validity", characterService.ValidityCheck(profilerInfo));
		return jsonObject.toString();
	}
	
	
	@RequestMapping("/abcd")
	public ProfilerInfoChar loadProfileInfoObj() {
		
		ArrayList<DistinctValueListChar> distinctValueList = new ArrayList();
		ArrayList<RegexInfoChar> regexInfoList =   new ArrayList();
		
		distinctValueList.add(new DistinctValueListChar("value1",5));
		distinctValueList.add(new DistinctValueListChar("value2",3));
		distinctValueList.add(new DistinctValueListChar("value3",2));
		regexInfoList.add(new RegexInfoChar("regex1", 50));
		regexInfoList.add(new RegexInfoChar("regex2", 5));
		regexInfoList.add(new RegexInfoChar("regex3", 2));
		
		ProfilerInfoChar profilerInfo = new ProfilerInfoChar(1,"colname","date",20,100,10,3,distinctValueList,2,9,2,5,3,10,20,15,10,20,regexInfoList);
		//profilerInfoImpl.create(profilerInfo);
		return profilerInfo;
	}
	
}
