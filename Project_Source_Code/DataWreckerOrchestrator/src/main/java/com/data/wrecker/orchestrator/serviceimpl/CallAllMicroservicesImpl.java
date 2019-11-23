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
	private CallDimensionServices callDimensionService;

	private static final Logger LOGGER = LogManager.getLogger();
	private static final String RESULT = "Success";
	private Mongo mongo;
	private DB db;

	@Override
	public String callDataprofilingServices(String fileName, int wreckPercentage) {
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
					result = callDatatypeServices(collectionName, wreckPercentage);
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


	private String callDatatypeServices(String fileName, int wreckPercentage) {


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

							LOGGER.info("String service is Successful ");
							result =  callDatatypeService.callDecimalService(fileName, wreckPercentage);

							if(result.equals(RESULT)) {
								LOGGER.info("Decimal service is Successful ");
								result = "Success";
							}else {
								LOGGER.info("String service is UnSuccessful ");
								result = "Failure";
							}
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
	public String callAllDimensionServices(String collectionName,int wreckingPercentage) {
		List<String> columnHeaders = new ArrayList<String>();
		columnHeaders = getColumnHeaders(collectionName);

		DataProfilerInfo dataprofilerInfo = getDataprofileInfo(collectionName);
		List<DatasetStats> datasetStatsList = dataprofilerInfo.getDatasetStats();

		int totalRowCount = datasetStatsList.get(1).getProfilingInfo().getColumnStats().getRowCount();
		int totalNumberOfRecordsToWreck = numberOfrecordsTobePicked(totalRowCount,wreckingPercentage);
		List<Integer> uniqueIdsToWreck = getIdsToWreck(totalNumberOfRecordsToWreck, totalRowCount);

		return wreckRecordsWithIDs(uniqueIdsToWreck, collectionName);
	}

	private String callDimension(List<Integer> wreckingIdsForDimension, String dimensionName, String columnName, String collectionName ) {
		String result = "";

		switch (dimensionName) {

		case "Completeness":
			result = callDimensionService.callCompletenessService(wreckingIdsForDimension, columnName, collectionName);
			break;

		case "Consistency":
			result = callDimensionService.callConsistencyService(wreckingIdsForDimension, columnName, collectionName);
			break;

		case "Uniqueness":
			result = callDimensionService.callUniquenessService(wreckingIdsForDimension, columnName, collectionName);
			break;

		case "Accuracy":
			result = callDimensionService.callAccuracyServcie(wreckingIdsForDimension, columnName, collectionName);
			break;

		case "Validity":
			result = callDimensionService.callValidityServcie(wreckingIdsForDimension, columnName, collectionName);
			break;
		default:
			result = collectionName;

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
		mongo = new Mongo("localhost", 27017);
		db = mongo.getDB("ReverseEngineering");
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

		mongo.close();
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


	private String wreckRecordsWithIDs(List<Integer> uniqueIdList, String collectionName) {
		JSONArray datasetArray = getDatasetFromDb(collectionName);
		//List<String> objectIds = new ArrayList<String>();
		List<String> columnNames = new ArrayList<String>();
		String newCollectionName = collectionName;
		int i=0;

		columnNames = getColumnHeaders(collectionName);

		for(i =0; i < columnNames.size(); i++ ) {

			String colName = columnNames.get(i);
			int totalRecordsToWreck = getTotalRecordsToWreck(collectionName, colName);

			List<Integer> recordIndexes = new ArrayList<Integer>();


			for(int j =0; j < totalRecordsToWreck; j++) {
				if(!uniqueIdList.isEmpty()) {
					recordIndexes.add(uniqueIdList.get(0));
					uniqueIdList.remove(0);
				}

			}

			newCollectionName = callDimensionServices(recordIndexes, colName, newCollectionName);
		}
		return newCollectionName;
	}


	private String callDimensionServices(List<Integer> objectIdsToWreck, String colName, String collectionName) {

		String firstVersionOfCollection = getFirstVersionOfCollection(collectionName);
		// LOGGER.info("First Version "+firstVersionOfCollection);
		DataProfilerInfo dataprofilerInfo = getDataprofileInfo(firstVersionOfCollection);

		List<Dimensions> dimensionsList = new ArrayList<Dimensions>();
		List<Integer> wreckingIdsForDimension = new ArrayList<Integer>();
		String newCollectionName = collectionName;
		int i =0;
		for( i =0; i < dataprofilerInfo.getDatasetStats().size(); i++ ) {
			if(dataprofilerInfo.getDatasetStats().get(i).getColumnName().equals(colName)) {
				dimensionsList = new ArrayList<Dimensions>();
				dimensionsList = dataprofilerInfo.getDatasetStats().get(i).getDimensionsList();
				break;
			}
		}
		if(!dimensionsList.isEmpty()) {


			for(i =0; i< dimensionsList.size(); i++) {
				int dimensionWreckingCount = 0;
				wreckingIdsForDimension = new ArrayList<Integer>();

				if(dimensionsList.get(i).isStatus() == true) {

					dimensionWreckingCount =  dimensionsList.get(i).getRemainingWreakingCount();
					wreckingIdsForDimension = getDatasetRecordIDS(dimensionWreckingCount, firstVersionOfCollection);
						 
					 }

					newCollectionName =  callDimension(wreckingIdsForDimension, dimensionsList.get(i).getDimensionName(), colName, newCollectionName);
			}
		}

		return newCollectionName;
	}


	private List<Integer> getDatasetRecordIDS(int wreckingCount, String collectionName) {

		JSONArray datasetArray = getDatasetFromDb(collectionName);
		List<Integer> wreckingIdsForDimension = new ArrayList<Integer>();
		Random rand = new Random();
		for(int t=0; t< wreckingCount; t++) {
			rand = new Random();
			wreckingIdsForDimension.add(rand.nextInt(datasetArray.length()));
		}
		return wreckingIdsForDimension;
	}


	private int getTotalRecordsToWreck(String collectionName, String columnName) {

		DataProfilerInfo dataprofilerInfo = getDataprofileInfo(collectionName);
		List<Dimensions> dimensionsList = new ArrayList<Dimensions>();
		int i =0;
		int totalCount =0;
		for( i =0; i < dataprofilerInfo.getDatasetStats().size(); i++ ) {
			if(dataprofilerInfo.getDatasetStats().get(i).getColumnName().equals(columnName)) {
				dimensionsList = new ArrayList<Dimensions>();
				dimensionsList = dataprofilerInfo.getDatasetStats().get(i).getDimensionsList();
			}
		}
		if(!dimensionsList.isEmpty()) {
			for(i=0; i< dimensionsList.size(); i++) {
				totalCount = totalCount + dimensionsList.get(i).getRemainingWreakingCount();
			}
		}

		return totalCount;

	}

	private String getFirstVersionOfCollection(String collectionName) {
		String[] name = collectionName.split("_");
		String newCollectionName;

		if(Integer.parseInt(name[name.length - 1]) == 1) {
			newCollectionName = collectionName;
			// LOGGER.info("New Collection Name is "+ collectionName);
		}else {
			newCollectionName = name[0]+"_"+0;
			// LOGGER.info("New Collection Name is "+newCollectionName);
		}
		return newCollectionName;
	}


}
