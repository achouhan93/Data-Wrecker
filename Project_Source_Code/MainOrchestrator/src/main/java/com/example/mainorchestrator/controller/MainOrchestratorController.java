package com.example.mainorchestrator.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mainorchestrator.entity.DatasetDetails;
import com.example.mainorchestrator.service.LoadFileIntoMongo;

@RestController
@RequestMapping("/main_orchestrator")
public class MainOrchestratorController {

	@Autowired
	LoadFileIntoMongo loadFileService;
	
	private DatasetDetails datasetDetails;
	
	
	@GetMapping("/loadFileIntoMongo")
	public DatasetDetails loadFileIntoMongo() { 
		datasetDetails = loadFileService.loadFileIntoMongo(); 
		String result = datasetDetails.getResult();
		Random rand = new Random();
		int wreckingPercentage = rand.nextInt(5) + 25;
		System.out.println("RandomNumber "+ wreckingPercentage);
		if(result.equals("Success")) {
			
			result = loadFileService.callDataWreckerOrchestrator(datasetDetails.getCollectionName(), wreckingPercentage);
		}
		return datasetDetails;
	}
	
	
	
}
