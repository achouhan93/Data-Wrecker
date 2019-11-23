package com.data.wrecker.accuracyDimension.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.LOGGER;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
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
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;


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
	//private static final  LOGGER LOGGER = LogManager.get LOGGER();
	private List<ChangesLog> changesLogList;
	private ChangesLog changesLog;
	@Autowired
	private ChangesLogsRepository changesLogrepo;
	private Mongo mongo;
	private DB db;
	
	
	@Override
	public String removeAccuracyDimension(String collectionName, String columnName,List<String> wreckingIds) {
		String firstCollectionName = getFirstVersionOfCollection(collectionName);
		JSONArray datasetArray = getDatasetFromDb(collectionName);
		String columnDataType  = getColumnDataType(firstCollectionName,columnName);
		changesLogList = new ArrayList<ChangesLog>();
		List<Integer> recordIndexes = new ArrayList<Integer>();

		for(String str : wreckingIds) {
			recordIndexes.add(Integer.valueOf(str));
		}

		
		
		try {
		for(int j =0; j < recordIndexes.size(); j++ ) {
			
					changesLog = new ChangesLog();
					changesLog.setColumnName(columnName);
					changesLog.setOid(recordIndexes.get(j));
					changesLog.setDimensionName("Accuracy");
					changesLog.setDatasetName(collectionName);
					JSONObject jsonObj = datasetArray.getJSONObject(recordIndexes.get(j));
					changesLog.setOldValue(jsonObj.get(columnName).toString());
					jsonObj = removeAccuracy(columnName, columnDataType, jsonObj,collectionName);
					datasetArray.put(jsonObj);
					changesLog.setNewValue(jsonObj.get(columnName).toString());
					changesLogList.add(changesLog);
					//addToDb(changesLog);
		}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addToDb(changesLogList);
		
		return addIntoDatabase(collectionName,datasetArray);
	}
	
	
	
	private void addToDb(List<ChangesLog> changesLogList) {
		for(int i =0; i < changesLogList.size(); i++) {
			changesLogrepo.insert(changesLogList.get(i));
		}
	}
	
	

	private String addIntoDatabase(String collectionName, JSONArray jsonArray) {
		mongo = new Mongo("localhost", 27017);
		db = mongo.getDB("ReverseEngineering");
		String[] name = collectionName.split("_");
		List<Document> jsonList = new ArrayList<Document>();
		
		String newCollectionName;
		int versionNumber;
		if(name[name.length - 1].isEmpty()) {
			newCollectionName = name[0]+"_1";
			// LOGGER.info("New Collection Name is "+ name[0]+"_1");
		}else {
			versionNumber = Integer.parseInt(name[name.length - 1]) + 1;
			newCollectionName = name[0]+"_"+versionNumber;
			// LOGGER.info("New Collection Name is "+newCollectionName);
		}
			
		DBCollection collection = db.createCollection(collectionName, null);
		 for (int i =0; i < jsonArray.length(); i++) {
		 
			 DBObject dbObject;
			try {
				dbObject = (DBObject) JSON.parse(jsonArray.getJSONObject(i).toString());
				collection.save(dbObject);
				//// LOGGER.info("Data added!");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }		
		return collectionName;
	}


	private JSONArray getDatasetFromDb(String collectionName) {
		
		mongo = new Mongo("localhost", 27017);
		db = mongo.getDB("ReverseEngineering");
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
		mongo.close();
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
	
	private JSONObject removeAccuracy(String colName, String columnDatatype, JSONObject jsonObj,String collectionName) {
		
		switch(columnDatatype.toLowerCase()) {
		case "string": 
			// // LOGGER.info("String");
			jsonObj = callServicesForString(jsonObj,colName);			
			break;
		case "integer":	
			//// LOGGER.info("Integer");
				jsonObj = callServicesForInteger(jsonObj,colName);
			break;
		case "character":	
			// // LOGGER.info("Character");
			jsonObj = callServicesForString(jsonObj,colName);
			break;
		case "date":
			//// LOGGER.info("Date");
			jsonObj = callServicesForDate(jsonObj,colName,collectionName);
			break;
		case "boolean":
			//// LOGGER.info("Boolean");
			jsonObj = callServicesForBoolean(jsonObj,colName);
			break;
		case "decimal":
			//// LOGGER.info("Integer");
			jsonObj = callServicesForDecimal(jsonObj, colName);
		break;
		}
		
		return jsonObj;
	}



	private JSONObject callServicesForBoolean(JSONObject jsonObj, String colName) {
		rand = new Random();
		int options = rand.nextInt(4);
		String[] truthValues = {"tRuE","TRue","TruE","Treu","Ture","t","T","1","Trre"};
		String[] falseValues = {"faLsE","FaLSE","FaLSe","FAls","f","0","FASE","Fals","FLSE","Fale"};
		switch(options) {
		case 1: 
			try {
				jsonObj.put(colName, truthValues[rand.nextInt(truthValues.length - 1)]);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 2:
			try {
				jsonObj.put(colName, truthValues[rand.nextInt(falseValues.length - 1)]);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			jsonObj = accuracyServiceImpl.interChangedValues(jsonObj, colName);
			break;
		default:
			jsonObj = accuracyServiceImpl.interChangedValues(jsonObj, colName);
			break;
		
		}
		return jsonObj;
	}



	private JSONObject callServicesForDate(JSONObject jsonObj, String colName,String collectionName) {
		rand = new Random();
		int options = rand.nextInt(2);
		String result = "";
		DatasetStats datasetStatsInfo = getDatasetStats(colName, collectionName);
		try {
			
		switch(options) {
			case 0: 
				jsonObj = accuracyServiceImpl.interChangedValues(jsonObj, colName);
				// LOGGER.info("AfterApplying Accuracy interChangedValues "+jsonObj.toString());
				break;
			case 1:
				result = accuracyServiceImpl.addYearsToDate(datasetStatsInfo, jsonObj.getString(colName));
				jsonObj.put(colName, result);
				// LOGGER.info("AfterApplying Accuracy addYearsToDate "+jsonObj.toString());
				break;	
			default:
				jsonObj = accuracyServiceImpl.interChangedValues(jsonObj, colName);
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
				// LOGGER.info("AfterApplying Accuracy interChangedValues "+jsonObj.toString());
				break;
			case 1: 
				//result = accuracyServiceImpl.convertIntToOppositeSign();
				jsonObj.put(colName, result);
				// LOGGER.info("AfterApplying Accuracy convertIntToOppositeSign "+jsonObj.toString());
				break;
			default:
				jsonObj = accuracyServiceImpl.interChangedValues(jsonObj, colName);
				break;
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObj;
	}

	
	private JSONObject callServicesForDecimal(JSONObject jsonObj, String colName) {
		
		jsonObj = accuracyServiceImpl.interChangedValues(jsonObj, colName);
		return jsonObj;
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
				// LOGGER.info("AfterApplying Accuracy typosForValues "+jsonObj.toString());
				break;
			case 1:
				result = accuracyServiceImpl.shuffleString(jsonObj.getString(colName));
				jsonObj.put(colName, result);
				// LOGGER.info("AfterApplying Accuracy shuffleString"+jsonObj.toString());
				break;
			case 2:
				result = accuracyServiceImpl.generateJunkValues(jsonObj.getString(colName));
				jsonObj.put(colName, result);
				// LOGGER.info("AfterApplying Accuracy generateJunkValues "+jsonObj.toString());
				break;
			case 3: 
				jsonObj = accuracyServiceImpl.interChangedValues(jsonObj, colName);
				// LOGGER.info("AfterApplying Accuracy interChangedValues "+jsonObj.toString());
				break;
			default:
				jsonObj = accuracyServiceImpl.interChangedValues(jsonObj, colName);
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
	
	

	private String getFirstVersionOfCollection(String collectionName) {
		String[] name = collectionName.split("_");
		String newCollectionName;
		if(Integer.parseInt(name[name.length - 1]) == 1) {
			newCollectionName = collectionName;
			 // LOGGER.info("New Collection Name is "+ collectionName);
		}else {
			newCollectionName = name[0]+"_"+1;
			 // LOGGER.info("New Collection Name is "+newCollectionName);
		}
		return collectionName;
	}
	
}
