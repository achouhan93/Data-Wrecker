package com.data.wrecker.orchestrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.wrecker.orchestrator.service.CallAllMicroservices;
import com.data.wrecker.orchestrator.service.CallDataTypeServices;

@RestController
@RequestMapping("/data_wrecker_orchestrator")
public class DataOrchestratorController {

	@Autowired
	CallAllMicroservices callAllServices;
	CallDataTypeServices callDatatypeServices;
	
	@GetMapping("/getDataprofileInfo")
	public String getDataprofileInfo(@RequestParam String fileName, @RequestParam int wreckingPercentage) {
		return callAllServices.callDataprofilingServices(fileName, wreckingPercentage);
	
	}
	
	@GetMapping("/applyDimensions")
	public String applyDimensions(@RequestParam String collectionName, @RequestParam int wreckingPercentage) {
		return callAllServices.callAllDimensionServices(collectionName,wreckingPercentage);
	}

}
