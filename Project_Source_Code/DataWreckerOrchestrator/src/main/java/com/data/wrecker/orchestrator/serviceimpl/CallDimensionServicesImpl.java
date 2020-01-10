package com.data.wrecker.orchestrator.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.data.wrecker.orchestrator.service.CallDimensionServices;

@Service
@Transactional
public class CallDimensionServicesImpl implements CallDimensionServices{

	 private static final Logger LOGGER = LogManager.getLogger();
	private String url = "";

	@Override
	public String callCompletenessService(List<String> objectIds, String colName, String collectionName) {
		// TODO Auto-generated method stub
		/*LOGGER.info("Completeness Service Called for column "+colName);
		LOGGER.info("OBJECT ids length "+objectIds.size());*/
		url = "http://localhost:8092/dimension/completenessDimension?collectionName="+collectionName+"&columnName="+colName+"&wreckingIdcount="+objectIds.size();
		//System.out.println("collectionName: "+collectionName+" colName: "+colName+" completeness");
		 LOGGER.info("URL \n" +url);
		return new RestTemplate().getForObject(url, String.class);
	}

	@Override
	public String callUniquenessService(List<String> objectIds, String colName, String collectionName) {
		// TODO Auto-generated method stub
		/*LOGGER.info("Uniqueness Service Called for column "+colName);
		LOGGER.info("OBJECT ids length "+objectIds.size());*/
		url = "http://localhost:8093/dimension/uniquenessDimension?collectionName="+collectionName+"&columnName="+colName+"&wreckingIdsForDimension="+listToString(objectIds);
		// LOGGER.info("URL \n"+url);
		//System.out.println("collectionName: "+collectionName+" colName: "+colName+" uniqueness");
		return new RestTemplate().getForObject(url, String.class);
	}

	@Override
	public String callConsistencyService(List<String> objectIds, String colName, String collectionName) {
		// TODO Auto-generated method stub
		/*LOGGER.info("Consistency Service Called for column "+colName);
		LOGGER.info("OBJECT ids length "+objectIds.size());*/
		url = "http://localhost:8094/dimension/consistencyDimension?collectionName="+collectionName+"&columnName="+colName+"&wreckingIdsForDimension="+listToString(objectIds);
		//System.out.println("collectionName: "+collectionName+" colName: "+colName+" consistancy");
		return new RestTemplate().getForObject(url, String.class);
	}

	@Override
	public String callAccuracyServcie(List<String> objectIds, String colName, String collectionName) {
		// TODO Auto-generated method stub
		/*LOGGER.info("Accuracy Service Called for column "+colName);
		LOGGER.info("OBJECT ids length "+objectIds.size());*/
		url = "http://localhost:8095/dimension/accuracyDimension?collectionName="+collectionName+"&columnName="+colName+"&wreckingIdsForDimension="+listToString(objectIds);
		//System.out.println("collectionName: "+collectionName+" colName: "+colName+" accuracy");
		return new RestTemplate().getForObject(url, String.class);
	}

	@Override
	public String callValidityServcie(List<String> objectIds, String colName, String collectionName) {
		// TODO Auto-generated method stub
		// LOGGER.info("Validity Service Called for column "+colName);
		// LOGGER.info("OBJECT ids length "+objectIds.size());
		url = "http://localhost:8096/dimension/validityDimension?collectionName="+collectionName+"&columnName="+colName+"&wreckingIdsForDimension="+listToString(objectIds);
		//System.out.println("collectionName: "+collectionName+" colName: "+colName+" validity");
		return new RestTemplate().getForObject(url, String.class);
	}
	
	private String listToString(List<String> objIds) {
		StringBuilder namesStr = new StringBuilder();
		 
		 for(String name : objIds)
		    {
		        namesStr = namesStr.length() > 0 ? namesStr.append(",").append(name) : namesStr.append(name);
		    }
		
		return namesStr.toString();
	}
	
}
