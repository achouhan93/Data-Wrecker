package com.data.datedatatype.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.datedatatype.model.DistinctValueList;
import com.data.datedatatype.model.ProfilerInfo;
import com.data.datedatatype.model.RegexInfo;
import com.data.datedatatype.repositoryimpl.ProfilerInfoRepositoryImpl;
import com.data.datedatatype.service.DateDataTypeService;
import com.data.datedatatype.serviceimpl.DateDataTypeImpl; 


@RestController
@RequestMapping("/dimension")
public class DateDataTypeController {
	

	@Autowired
	private DateDataTypeService dateService;
	
	 
	@RequestMapping("/dateDimension") 
	public String getDimensions() { 	
		ProfilerInfo profilerInfo = loadProfileInfoObj();
		JSONObject jsonObject = new JSONObject();
		jsonObject.append("NullCHeck", dateService.NullCheck(profilerInfo));
		jsonObject.append("Accuracy", dateService.AccuracyCheck(profilerInfo));
		jsonObject.append("Consistency",dateService.ConsistencyCheck(profilerInfo));
		jsonObject.append("Validity", dateService.ValidityCheck(profilerInfo));
	return jsonObject.toString();
	 }
	
	/*@RequestMapping("/getAll")
	public List<ProfilerInfo> data() {
		return profilerInfoImpl.getAll(); 
	}*/
	
	@RequestMapping("/abcd")
	public ProfilerInfo loadProfileInfoObj() {
		
		ArrayList<DistinctValueList> distinctValueList = new ArrayList();
		ArrayList<RegexInfo> regexInfoList =   new ArrayList();
		
		distinctValueList.add(new DistinctValueList("value1",5));
		distinctValueList.add(new DistinctValueList("value2",3));
		distinctValueList.add(new DistinctValueList("value3",2));
		regexInfoList.add(new RegexInfo("regex1", 50));
		regexInfoList.add(new RegexInfo("regex2", 20));
		regexInfoList.add(new RegexInfo("regex3", 30));
		
		ProfilerInfo profilerInfo = new ProfilerInfo(1,"colname","date",20,100,10,3,distinctValueList,2,9,2,5,3,10,20,15,10,20,regexInfoList);
		//profilerInfoImpl.create(profilerInfo);
		return profilerInfo;
	}
	
	
}
