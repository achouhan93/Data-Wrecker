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
	GetProfilerInfoFromServices getProfilerInfoFromServices;
	@Autowired
	CallDataTypeServices callDatatypeServices;
	private DataProfilerInfo dataProfilerInfo;
	
	@GetMapping("/getPatterns")
	public DataProfilerInfo getRandomizer(@RequestParam String fileName) {		
		dataProfilerInfo = getProfilerInfoFromServices.callPatternIdentificationService(fileName);
		return dataProfilerInfo;
	}
	
	@GetMapping("/getColumnDatatype")
	public String getColumnDatatype(@RequestParam String fileName) {
		return getProfilerInfoFromServices.callColumnDatatypePredictionService(fileName);
	}
	
	@GetMapping("/getColumnStatistics")
	public String getColumnStats(@RequestParam String fileName) {
		return getProfilerInfoFromServices.callColumnStatisticsService(fileName);	
	}
	
	@GetMapping("/callDateService")
	public String callDateDataTyprService(@RequestParam String fileName) {
		return callDatatypeServices.callDateService(fileName, 25);
	}
	
	@GetMapping("/callBooleanService")
	public String callBooleanDataTypeService(@RequestParam String fileName) {
		return callDatatypeServices.callBooleanService(fileName, 25);
	}
	
	@GetMapping("/callCharacterService")
	public String callCharacterDataTypeService(@RequestParam String fileName) {
		return callDatatypeServices.callCharacterService(fileName, 25);
	}
	
	@GetMapping("/callIntegerService")
	public String callIntegerDataTypeService(@RequestParam String fileName) {
		return callDatatypeServices.callIntegerService(fileName, 25);
	}
	
	@GetMapping("/callDecimalService")
	public String callDecimalDataTypeService(@RequestParam String fileName) {
		return callDatatypeServices.callDecimalService(fileName, 25);
	}
	
}
