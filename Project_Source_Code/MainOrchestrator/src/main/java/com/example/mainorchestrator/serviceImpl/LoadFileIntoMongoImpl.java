package com.example.mainorchestrator.serviceImpl;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.mainorchestrator.entity.DatasetDetails;
import com.example.mainorchestrator.service.LoadFileIntoMongo;

@Service
@Transactional
public class LoadFileIntoMongoImpl implements LoadFileIntoMongo{

	private static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public DatasetDetails loadFileIntoMongo() {
		DatasetDetails datasetDetails = new DatasetDetails();
		datasetDetails = new RestTemplate().getForObject("http://localhost:8080/dataWreakerInterface/dataPopulation", DatasetDetails.class);
		String result = "";
		if(datasetDetails.getResult().equals("Data imported from file to MongoDB Successfully")) {
		
			Random rand = new Random();
			int wreckingPercentage = rand.nextInt(10) + 20;
			datasetDetails.setWreckingPercentage(wreckingPercentage);
			result = "Success";
			LOGGER.info("Data has successfully loaded into MongoDb and The Wrecking percentage calsulated is "+wreckingPercentage);
			
			LOGGER.info("Calling Data Wrecker Orchestrator Service ");
			
			
			/*collectionName = datasetDetails.getCollectionName();
			LOGGER.info("Data imported Successfully Now calling Pattern Identification service created name "+datasetDetails.getCollectionName());
			datasetDetails = new DatasetDetails();
			String URL = "http://localhost:8081/patternIdentification/getPossiblePatternsForData?fileName="+collectionName;
			datasetDetails = new RestTemplate().getForObject("http://localhost:8081/patternIdentification/getPossiblePatternsForData?fileName="+collectionName, DatasetDetails.class);
			
			if(datasetDetails.getResult().equals("Success")) {
				LOGGER.info("Patterns are successfully identified Collection created name "+datasetDetails.getCollectionName());
				LOGGER.info("Calling Datatype Prediction Service");
				URL = "http://localhost:8082/columnDataTypePrediction/getDataTypeOfAColumns?collectionName="+datasetDetails.getCollectionName();
				result = new RestTemplate().getForObject(URL,String.class);
				LOGGER.info("Result after  Datatype Prediction Service "+result);
				if(result.equals("Success")) {
					URL = "http://localhost:8083/columnStatistics/getColumnStats?fileName="+datasetDetails.getCollectionName();
					result = new RestTemplate().getForObject(URL,String.class);
					LOGGER.info("Result after ColumnStatistics Service "+result);
				}else {
					LOGGER.info("ColumnStatistics Service has failed! ");
					result = "Failure in ColumnStatistics";
				}
			}else {
				LOGGER.info("PatternsIdentification Service has failed! ");
				result = "Failure in PatternsIdentification";
			}*/
		}else {
			LOGGER.info("Failed to load file into Mongo! ");
			result = "Failed to load file into Mongo.........!!!!!";
		}		
		datasetDetails.setResult(result);
		return  datasetDetails;
	}

	@Override
	public String callDataWreckerOrchestrator(String fileName, int wreckingPercentage) {	
		String result = "";
		String URL = "http://localhost:8091/data_wrecker_orchestrator/getDataprofileInfo?fileName="+fileName;		
		result = callRestTemplates(URL);
		if(result.equals("Success")) {
			return result;
		}else {
			return "Failure in profiling services";
		}
	}

	
	private String callRestTemplates(String URL) {
		return new RestTemplate().getForObject(URL,String.class);
	}
	
}
