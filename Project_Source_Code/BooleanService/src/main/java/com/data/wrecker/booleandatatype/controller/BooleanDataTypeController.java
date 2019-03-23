package com.data.wrecker.booleandatatype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.data.wrecker.booleandatatype.bean.ProfilerInfo;
import com.data.wrecker.booleandatatype.service.BooleanDataTypeImpl;

@RestController
@RequestMapping("/demo")
public class BooleanDataTypeController {

	@Autowired
	BooleanDataTypeImpl booleanservice;
	

	@RequestMapping("/profiler")
	@ResponseBody
	public ProfilerInfo returnProfilerInfo() {
		ProfilerInfo profilerInfo = new ProfilerInfo(100,0,2,50,1,1,1,1,1,1,1,50,50,20);		
		return profilerInfo;
	}
	
	
}
