package com.data.stringdatatype.controller;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.stringdatatype.model.DistinctValueListString;
import com.data.stringdatatype.model.ProfilerInfoString;
import com.data.stringdatatype.model.RegexInfoString;
import com.data.stringdatatype.service.StringDataTypeService;


@RestController
@RequestMapping("/dimension")
public class StringServiceController {

	 
	@Autowired
	StringDataTypeService characterService;
	
	
	@GetMapping("/booleanDimension")
	public String getDimensions()
	{
		ProfilerInfoString profilerInfo = loadProfileInfoObj();
		JSONObject jsonObject = new JSONObject();
		jsonObject.append("NullCHeck", characterService.NullCheck(profilerInfo));
		jsonObject.append("Accuracy", characterService.AccuracyCheck(profilerInfo));
		jsonObject.append("Consistency",characterService.ConsistencyCheck(profilerInfo));
		jsonObject.append("Validity", characterService.ValidityCheck(profilerInfo));
		return jsonObject.toString();
	}
	
	 
	 @RequestMapping("/abcd")
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
		}
}
