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
		String URL = "http://localhost:8086/dimension/booleanDatatypeDimensions?columnName="+fileName+"&wreckingPercentage="+wreckingPercentage;		
		result = callRestTemplates(URL);
		if(result.equals("Success")) {
			LOGGER.info("Boolean Service has been completed  ");
			LOGGER.info("Starting Date service ");
			URL = "http://localhost:8087/dimension/dateDatatypeDimensions?fileName="+fileName+"&wreckingPercentage="+wreckingPercentage;
			result = callRestTemplates(URL);
			if(result.equals("Success")) {
				LOGGER.info("Date Service has been completed  ");
				LOGGER.info("Starting Character service ");
				URL = "http://localhost:8088/dimension/characterDatatypeDimensions?fileName="+fileName+"&wreckingPercentage="+wreckingPercentage;
				result = callRestTemplates(URL);
				if(result.equals("Success")) {
					LOGGER.info("Character Service has been completed  ");
					LOGGER.info("Starting String service ");
					URL = "http://localhost:8090/dimension/stringDatatypeDimensions/?fileName="+fileName+"&wreckingPercentage="+wreckingPercentage;
					result = callRestTemplates(URL);
					if(result.equals("Success")) {
					LOGGER.info("String Service has been completed  ");
					LOGGER.info("Starting Integer service ");
					URL = "http://localhost:8089/integerDataType/integerDataTypeDecision?wreakingDataRecordPosition="+wreckingPercentage+"&collectionName="+fileName;
					result = callRestTemplates(URL);
					if(result.equals("Success")) {
						LOGGER.info("Integer Service has been completed  ");
					}else {
					 result = "Failure in Itegering Service";
					}
					
					}else {
						result = "Failure in String Service";
					}
				}else {
					result = "Failure in Character Service";
				}
			}
		}else {
			result = "Failure in Boolean Service";
		}
		return result;
	}

	
	private String callRestTemplates(String URL) {
		return new RestTemplate().getForObject(URL,String.class);
	}
	
}
