package com.data.datedatatype.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.datedatatype.model.ProfilerInfo;
import com.data.datedatatype.repository.Impl.ProfilerInfoRepositoryImpl;
import com.data.datedatatype.service.DateDataTypeService;
import com.data.datedatatype.serviceimpl.DateDataTypeImpl; 


@RestController
public class DateDataTypeController {
	
	@Autowired
	private ProfilerInfoRepositoryImpl profilerInfo;
	 
	@RequestMapping("/dateDimension") 
	public String getDimensions() { 		  
	return profilerInfo.givemeString();
	 }
	
	@RequestMapping("/getAll")
	public List<ProfilerInfo> data() {
		return profilerInfo.getAll(); 
	}
	
	@RequestMapping("/abcd")
	public String adbb() {
		return "jhdhd";
	}
}
