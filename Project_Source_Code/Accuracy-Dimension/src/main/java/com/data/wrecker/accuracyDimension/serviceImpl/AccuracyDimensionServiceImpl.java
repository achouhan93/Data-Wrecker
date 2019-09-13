package com.data.wrecker.accuracyDimension.serviceImpl;

import java.util.ArrayList;
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

import com.data.wrecker.accuracyDimension.model.ChangesLog;
import com.data.wrecker.accuracyDimension.model.DataProfilerInfo;
import com.data.wrecker.accuracyDimension.model.DatasetStats;
import com.data.wrecker.accuracyDimension.repository.ChangesLogsRepository;
import com.data.wrecker.accuracyDimension.repository.DataProfilerInfoRepo;
import com.data.wrecker.accuracyDimension.service.AccuracyDimensionService;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;


@Service
@Transactional
public class AccuracyDimensionServiceImpl implements AccuracyDimensionService{

	@Autowired
	private DataProfilerInfoRepo dataProfilerInfoRepo;
	@Autowired
	private TypesOfAccuracyServiceImpl accuracyServiceImpl;
	private DataProfilerInfo dataProfilerInfo;
	private List<DataProfilerInfo> dataProfilerInfoList;
	private Random rand;
	private List<DatasetStats> datasetStatsList;
	private DatasetStats datasetStats;
	private static final Logger LOGGER = LogManager.getLogger();
	private List<ChangesLog> changesLogList;
	private ChangesLog changesLog;
	@Autowired
	private ChangesLogsRepository changesLogrepo;
	
	
	
	
	@Override
	public String removeAccuracyDimension(String collectionName, String columnName,List<String> wreckingIds) {
		JSONArray datasetArray = getDatasetFromDb(collectionName);
		String columnDataType  = getColumnDataType(collectionName,columnName);
		List<Integer> randomValues = new ArrayList<Integer>();
		Random randomGenerator = new Random();
		for(int i=0;i<5;i++) {
			int randomNumber = randomGenerator.nextInt(50) ;
			randomValues.add(randomNumber);
			LOGGER.info("Random Number  " +randomNumber);
		}
		try {
		for(int j =0; j < wreckingIds.size(); j++ ) {
			String objectId = wreckingIds.get(j);
			for(int i=0;i<datasetArray.length();i++) {
				if(datasetArray.getJSONObject(i).getJSONObject("_id").getString("$oid").equals(objectId)) {
					
					JSONObject jsonObj = datasetArray.getJSONObject(i);
					jsonObj = removeAccuracy(columnName, columnDataType, jsonObj);					
					// datasetArray.getJSONObject(randomValues.get(j)).put("isWrecked", true);
					LOGGER.info("Wrecked Data \n" +  jsonObj.toString());
					
				}				
			}	
		}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	private JSONArray getDatasetFromDb(String collectionName) {
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("ReverseEngineering");
		DBCollection collection = db.getCollection(collectionName); //giving the collection name 
		DBCursor cursor = collection.find();
		JSONArray dbList = new JSONArray();
		List<String> columnNamesList = new ArrayList<String>();
		
		
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
	
	private String getColumnDataType(String collectionName,String columnName) {
	
		dataProfilerInfoList = dataProfilerInfoRepo.findAll();
		String colDatatype = "";
		for(int i =0; i < dataProfilerInfoList.size(); i++) {
			if(dataProfilerInfoList.get(i).getFileName().equals(collectionName)) {
				dataProfilerInfo = new DataProfilerInfo();
				dataProfilerInfo = dataProfilerInfoList.get(i);
				break;				
			}
		}	
		
		for(int i=0; i<dataProfilerInfo.getDatasetStats().size(); i++) {
			
			if(dataProfilerInfo.getDatasetStats().get(i).getColumnName().equals(columnName)) {
				colDatatype = dataProfilerInfo.getDatasetStats().get(i).getProfilingInfo().getColumnDataType();
				break;
			}
		}
		
		return colDatatype;		
	
	}
	
	private JSONObject removeAccuracy(String colName, String columnDatatype, JSONObject jsonObj) {
		String result = "";
		switch(columnDatatype.toLowerCase()) {
		case "string": 
			LOGGER.info("String");
			jsonObj = callServicesForString(jsonObj,colName);			
			break;
		case "integer":	
			LOGGER.info("Integer");
				jsonObj = callServicesForInteger(jsonObj,colName);
			break;
		case "character":	
			LOGGER.info("Character");
			jsonObj = callServicesForString(jsonObj,colName);
			break;
		case "date":
			LOGGER.info("Date");
			jsonObj = callServicesForDate(jsonObj,colName);
			break;
		case "boolean":
			LOGGER.info("Boolean");
			jsonObj = callServicesForBoolean(jsonObj,colName);
		}
		
		return jsonObj;
	}



	private JSONObject callServicesForBoolean(JSONObject jsonObj, String colName) {
		rand = new Random();
		int options = rand.nextInt(4);
		switch(options) {
		case 1: 
			break;
		case 2:
			break;
		case 3:
			jsonObj = accuracyServiceImpl.interChangedValues(jsonObj, colName);
			break;
		
		}
		return jsonObj;
	}



	private JSONObject callServicesForDate(JSONObject jsonObj, String colName) {
		rand = new Random();
		int options = rand.nextInt(2);
		String collectionName = "";
		String result = "";
		DatasetStats datasetStatsInfo = getDatasetStats(colName, collectionName);
		try {
			
		switch(options) {
			case 0: 
				jsonObj = accuracyServiceImpl.interChangedValues(jsonObj, colName);
				LOGGER.info("AfterApplying Accuracy interChangedValues "+jsonObj.toString());
				break;
			case 1:
				result = accuracyServiceImpl.addYearsToDate(datasetStatsInfo, jsonObj.getString(colName));
				jsonObj.put(colName, result);
				LOGGER.info("AfterApplying Accuracy addYearsToDate "+jsonObj.toString());
				break;				
			}
		}catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			
		
		return jsonObj;
	}



	private JSONObject callServicesForInteger(JSONObject jsonObj, String colName) {
		rand = new Random();
		int options = rand.nextInt(2);
		int result = 0;
		try {
			
			switch(options) {
			case 0: 
				jsonObj = accuracyServiceImpl.interChangedValues(jsonObj, colName);
				LOGGER.info("AfterApplying Accuracy interChangedValues "+jsonObj.toString());
				break;
			case 1: 
				result = accuracyServiceImpl.convertIntToOppositeSign(jsonObj.getInt(colName));
				jsonObj.put(colName, result);
				LOGGER.info("AfterApplying Accuracy convertIntToOppositeSign "+jsonObj.toString());
				break;				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}



	private JSONObject callServicesForString(JSONObject jsonObj, String colName) {
		rand = new Random();
		int options = rand.nextInt(4);
		String result;	
		 
			try {
				switch(options) {
			case 0:
				result = accuracyServiceImpl.typosForValues(jsonObj.getString(colName));
				jsonObj.put(colName, result);
				LOGGER.info("AfterApplying Accuracy typosForValues "+jsonObj.toString());
				break;
			case 1:
				result = accuracyServiceImpl.shuffleString(jsonObj.getString(colName));
				jsonObj.put(colName, result);
				LOGGER.info("AfterApplying Accuracy shuffleString"+jsonObj.toString());
				break;
			case 2:
				result = accuracyServiceImpl.generateJunkValues(jsonObj.getString(colName));
				jsonObj.put(colName, result);
				LOGGER.info("AfterApplying Accuracy generateJunkValues "+jsonObj.toString());
				break;
			case 3: 
				jsonObj = accuracyServiceImpl.interChangedValues(jsonObj, colName);
				LOGGER.info("AfterApplying Accuracy interChangedValues "+jsonObj.toString());
				break;
				}
			} catch (JSONException e) {				
				e.printStackTrace();
			}
		return jsonObj;
	}

	
	private DatasetStats getDatasetStats(String colName, String cllectionName) {
		
		dataProfilerInfoList = new ArrayList<DataProfilerInfo>();
		
		dataProfilerInfoList = dataProfilerInfoRepo.findAll();
		for(int i =0; i < dataProfilerInfoList.size(); i++) {
			if(dataProfilerInfoList.get(i).getFileName().equals(cllectionName)) {
				dataProfilerInfo = new DataProfilerInfo();
				datasetStatsList = dataProfilerInfoList.get(i).getDatasetStats();
				for(int j =0; j < datasetStatsList.size(); j++) {
					if(datasetStatsList.get(j).getColumnName().equals(colName)) {
						datasetStats = new DatasetStats();
						datasetStats = datasetStatsList.get(j);
						break;
					}
				}	
			}
		}
		
		return datasetStats;
	}

}
