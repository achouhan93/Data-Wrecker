package com.data.wrecker.orchestrator.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.wrecker.orchestrator.entity.DataProfilerInfo;
import com.data.wrecker.orchestrator.entity.DatasetDetails;
import com.data.wrecker.orchestrator.entity.DatasetStats;
import com.data.wrecker.orchestrator.entity.Dimensions;
import com.data.wrecker.orchestrator.repository.DataProfilerInfoRepository;
import com.data.wrecker.orchestrator.service.CallAllMicroservices;
import com.data.wrecker.orchestrator.service.CallDataTypeServices;
import com.data.wrecker.orchestrator.service.CallDimensionServices;
import com.data.wrecker.orchestrator.service.GetProfilerInfoFromServices;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

@Service
@Transactional
public class CallAllMicroservicesImpl implements CallAllMicroservices{

	@Autowired
	private GetProfilerInfoFromServices getProfilingServices;
	@Autowired
	private CallDataTypeServices callDatatypeService;
	@Autowired
	private DataProfilerInfoRepository dataProfilerInfoRepo;
	@Autowired
	private CallDimensionServices callDimensionServices;

	private static final Logger LOGGER = LogManager.getLogger();
	private static final String RESULT = "Success";
	
	@Override
	public String callDataprofilingServices(String fileName) {
		DatasetDetails datasetDetails;
		String collectionName = fileName;
		String result = "";
		LOGGER.info("Calling Pattern Identification service on collection "+fileName);
		datasetDetails = new DatasetDetails();

		datasetDetails = getProfilingServices.callPatternIdentificationService(collectionName);
		
		if(datasetDetails.getResult().equals(RESULT)) {
			
			LOGGER.info("Patterns are successfully identified Collection created name "+datasetDetails.getCollectionName());
			LOGGER.info("Calling Datatype Prediction Service");
			datasetDetails = new DatasetDetails();
			result = getProfilingServices.callColumnDatatypePredictionService(collectionName);
			LOGGER.info("Result after  Datatype Prediction Service "+result);
			if(result.equals(RESULT)) {
				
				LOGGER.info("Calling ColumnStatistics  Service"); 
				result = getProfilingServices.callColumnStatisticsService(collectionName);
				LOGGER.info("Result after ColumnStatistics Service "+result);
				if(result.equals(RESULT)) {
					
					LOGGER.info("ColumnStatistics Service Successful");
					LOGGER.info("Data profiling is Completed \n Now calling Datatype services ");
					result = callDatatypeServices(collectionName);
					LOGGER.info("Result after datatype services " + result);
					
				}else {
					
					LOGGER.info("ColumnStatistics Servvice has failed! ");
					result = "Failure in ColumnStatistics";
					
				}
				
			}else {
				
				LOGGER.info("Datatype Prediction Servvice has failed! ");
				result = "Failure in Datatype Prediction";
				
			}
			
		}else {
			
			LOGGER.info("PatternsIdentification Service has failed! ");
			result = "Failure in PatternsIdentification";
		}
		
		return result;
	}
	
	
	private String callDatatypeServices(String fileName) {
		
		int wreckPercentage = 20;
		String result = "";
		
		result = callDatatypeService.callBooleanService(fileName, wreckPercentage);
		
		if(result.equals(RESULT)) {
			LOGGER.info("Boolean service is Successful ");
			result = callDatatypeService.callDateService(fileName, wreckPercentage);
			
			if(result.equals(RESULT)) {
				
				LOGGER.info("Date service is Successful ");
				result = callDatatypeService.callCharacterService(fileName, wreckPercentage);
				if(result.equals(RESULT)) {
					
					LOGGER.info("Character service is Successful ");
					result = callDatatypeService.callIntegerService(fileName, wreckPercentage);
					if(result.equals(RESULT)) {
						
						LOGGER.info("Integer service is Successful ");
						
						result = callDatatypeService.callStringService(fileName,wreckPercentage);
						if(result.equals(RESULT)) {
							result = "Success";
							LOGGER.info("String service is Successful ");
						}else {
							LOGGER.info("String service is UnSuccessful ");
							result = "Failure";
						}
						
					} else {
						
						LOGGER.info("Integer service is UnSuccessful ");
						result = "Failure";
					}
					
				} else {
					
					LOGGER.info("Character service is UnSuccessful ");
					result = "Failure";
				}
			
			} else {
				
				LOGGER.info("Date service is UnSuccessful ");
				result = "Failure";
			}
			
			
		} else {
			LOGGER.info("Boolean service is UnSuccessful ");
			result = "Failure";
		}
		return result;
	}
	
	@Override
	public String callDimensionServices(String collectionName,int wreckingPercentage) {
		List<String> columnHeaders = new ArrayList<String>();
		columnHeaders = getColumnHeaders(collectionName);
		
		DataProfilerInfo dataprofilerInfo = getDataprofileInfo(collectionName);
		List<DatasetStats> datasetStatsList = dataprofilerInfo.getDatasetStats();
		
		/*for(int j =0; j< datasetStatsList.size(); j++) {
			if(datasetStatsList.get(j).getColumnName().equals(columnHeaders.get(j))) {
				List<Dimensions> dimensionList = new ArrayList<Dimensions>();
				dimensionList = getDimensionResults(datasetStatsList.get(j));
				result = callDimensionServicesForWrecking(dimensionList, columnHeaders.get(j));
				
			}			
		}*/
		
		int totalRowCount = datasetStatsList.get(1).getProfilingInfo().getColumnStats().getRowCount();
		int totalNumberOfRecordsToWreck = numberOfrecordsTobePicked(totalRowCount,wreckingPercentage);
		List<Integer> uniqueIdsToWreck = getIdsToWreck(totalNumberOfRecordsToWreck, totalRowCount);
		wreckRecordsWithIDs(uniqueIdsToWreck, collectionName);
		return "Total records can be wreked "+totalNumberOfRecordsToWreck;
	}
	
	private List<Dimensions> getDimensionResults(DatasetStats datasetStats) {	
		List<Dimensions> dimensionList = new ArrayList<Dimensions>();
		dimensionList = datasetStats.getDimensionList().getDimensionsList();
		return dimensionList;
	}
	
	private String callDimensionServicesForWrecking(List<Dimensions> dimensionList, String columnName) {
		String res="";
		for(int i=0; i< dimensionList.size(); i++) {
			if(dimensionList.get(i).isStatus()) {
				res = callDimension(dimensionList.get(i).getDimensionName(), columnName);
			}		
		}
		return res;
		
	}
	
	private String callDimension(String dimensionName, String columnName ) {
		String result = "";
		switch (dimensionName) {
		
		case "Completeness":
			LOGGER.info("Completeness Called ColumnName "+columnName);
			result = "Completeness";
			break;
			
		case "Uniqueness":
			LOGGER.info("Uniqueness Called ColumnName "+columnName);
			result = "Uniqueness";
			break;
		
		case "Consistency":
			LOGGER.info("Consistency Called ColumnName "+columnName);
			result = "Consistency";
			break;
		
		case "Accuracy":
			LOGGER.info("Accuracy Called ColumnName "+columnName);
			result = "Accuracy";
			break;	
		
		case "Validity":
			LOGGER.info("Validity Called ColumnName "+columnName);
			result = "Validity";
			break;	
			
		}
		
		return result;
	}
	private DataProfilerInfo getDataprofileInfo(String collectionName) {
		List<DataProfilerInfo> dataprofileInfoList = new ArrayList<DataProfilerInfo>();
		dataprofileInfoList = dataProfilerInfoRepo.findAll();
		DataProfilerInfo dataProfilerInfo = null;
		for(int i =0; i < dataprofileInfoList.size(); i++) {
			dataProfilerInfo = new DataProfilerInfo();
			if(dataprofileInfoList.get(i).getFileName().equals(collectionName)) {
				dataProfilerInfo = new DataProfilerInfo();
				dataProfilerInfo = dataprofileInfoList.get(i);
				break;
			}
	}
		return dataProfilerInfo;
}
	
	private int numberOfrecordsTobePicked(int rowCount,int wreckingPercentage) {
		
		return (rowCount * wreckingPercentage) / 100 ;
	}
	
	private List<Integer> getIdsToWreck(int recordsCount, int totalRowCount) {
		
		ArrayList<Integer> recordIds = new ArrayList<Integer>();
		ArrayList <Integer> uniqueIds = new ArrayList<Integer>();
	
		int index =0;
		for(index = 0; index < totalRowCount; index ++) {	
			recordIds.add(index);			
		}
		
		Collections.shuffle(recordIds);

		for( index =0; index < recordsCount; index++) {
			uniqueIds.add(recordIds.get(new Integer(index)));			
		}
		
		return uniqueIds;
	}
	
	private JSONArray getDatasetFromDb(String collectionName) {
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("ReverseEngineering");
		DBCollection collection = db.getCollection(collectionName); //giving the collection name 
		DBCursor cursor = collection.find();
		JSONArray dbList = new JSONArray();
				
		
		while(cursor.hasNext()) {
			String str = cursor.next().toString();
			try {	
				JSONObject jsnobj = new JSONObject(str);
				dbList.put(jsnobj);				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	
		return dbList;
	}
	
	private List<String> getColumnHeaders(String collectionName) {
		List<String> columnHeaders = new ArrayList<String>();
		
		DataProfilerInfo dataprofilerInfo = getDataprofileInfo(collectionName);
		for(int i =0; i < dataprofilerInfo.getDatasetStats().size(); i++ ) {
			columnHeaders.add(dataprofilerInfo.getDatasetStats().get(i).getColumnName());
			//dataprofilerInfo.getDatasetStats().get(i).getDimensionList()
		}
		
		return columnHeaders;
	}
	
	
	private void wreckRecordsWithIDs(List<Integer> uniqueIdList, String collectionName) {
		JSONArray datasetArray = getDatasetFromDb(collectionName);
		List<String> objectIds = new ArrayList<String>();
		List<String> columnNames = new ArrayList<String>();
		int i=0;
		try {			
			for(i =0; i < uniqueIdList.size(); i++ ) {
				objectIds.add(datasetArray.getJSONObject(uniqueIdList.get(i)).getJSONObject("_id").getString("$oid"));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		
		columnNames = getColumnHeaders(collectionName);
		
		for(i =0; i < columnNames.size(); i++ ) {
	
			String colName = columnNames.get(i);
			int totalRecordsToWreck = getTotalRecordsToWreck(collectionName, colName);
			Collections.shuffle(objectIds);
			List<String> objectIdsToWreck = new ArrayList<String>();
			
			for(int j =0; j < totalRecordsToWreck; j++) {
				objectIdsToWreck.add(objectIds.get(j));
				objectIds.remove(j);				
			}
			
			
		}
		
	}
	
	
	private String callDimensionServices(List<String> objectIdsToWreck, String colName, String collectionName) {
		
		DataProfilerInfo dataprofilerInfo = getDataprofileInfo(collectionName);
		List<Dimensions> dimensionsList = new ArrayList<Dimensions>();
		List<String> wreckingIdsForDimension = new ArrayList<String>();
		
		int i =0;
		for( i =0; i < dataprofilerInfo.getDatasetStats().size(); i++ ) {
			if(dataprofilerInfo.getDatasetStats().get(i).getColumnName().equals(colName)) {
				dimensionsList = new ArrayList<Dimensions>();
				dimensionsList = dataprofilerInfo.getDatasetStats().get(i).getDimensionList().getDimensionsList();
				break;
			}
		}
		if(!dimensionsList.isEmpty()) {
			
			for(i =0; i< dimensionsList.size(); i++) {
				if(dimensionsList.get(i).isStatus()) {
					int dimensionWreckingCount = dimensionsList.get(i).getRemainingWreakingCount();
					Collections.shuffle(objectIdsToWreck);
					for(int j =0; j< dimensionWreckingCount; j++) {
						wreckingIdsForDimension.add(objectIdsToWreck.get(j));
						objectIdsToWreck.remove(j);
					}
					callDimension(dimensionsList.get(i).getDimensionName(), colName);
					
				}
			}			
		}
		
		return null;
	}
	
	private int getTotalRecordsToWreck(String collectionName, String columnName) {
		
		DataProfilerInfo dataprofilerInfo = getDataprofileInfo(collectionName);
		List<Dimensions> dimensionsList = new ArrayList<Dimensions>();
		int i =0;
		int totalCount =0;
		for( i =0; i < dataprofilerInfo.getDatasetStats().size(); i++ ) {
			if(dataprofilerInfo.getDatasetStats().get(i).getColumnName().equals(columnName)) {
				dimensionsList = new ArrayList<Dimensions>();
				dimensionsList = dataprofilerInfo.getDatasetStats().get(i).getDimensionList().getDimensionsList();
			}
		}
		if(!dimensionsList.isEmpty()) {
			for(i=0; i< dimensionsList.size(); i++) {
				totalCount = totalCount + dimensionsList.get(i).getRemainingWreakingCount();
			}			
		}

		return totalCount;
		
	}
	
}
