package com.data.wrecker.orchestrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.wrecker.orchestrator.entity.DataProfilerInfo;
import com.data.wrecker.orchestrator.service.CallDataTypeServices;
import com.data.wrecker.orchestrator.service.GetProfilerInfoFromServices;

@RestController
@RequestMapping("/data_wrecker_orchestrator")
public class DataOrchestratorController {

	@Autowired
	GetProfilerInfoFromServices callPatternIdentificationService;
	@Autowired
	CallDataTypeServices callDatatypeServices;
	private DataProfilerInfo dataProfilerInfo;
	
	@GetMapping("/start")
	public DataProfilerInfo getRandomizer(@RequestParam String fileName) {		
		dataProfilerInfo = callPatternIdentificationService.callPatternIdentificationService(fileName);
		return dataProfilerInfo;
	}
	
	@GetMapping("/getColumnDatatype")
	public DataProfilerInfo getColumnDatatype() {
		return null;
	}
	
	@GetMapping("/getColumnStatistics")
	public String getColumnStats(@RequestParam String fileName) {
		return callPatternIdentificationService.callColumnStatisticsService(fileName);	
	}
	
	@GetMapping("/callDateService")
	public String callDateDataTyprService(@RequestParam String fileName) {
		return callDatatypeServices.callDateService(fileName, 25);
	}
}
