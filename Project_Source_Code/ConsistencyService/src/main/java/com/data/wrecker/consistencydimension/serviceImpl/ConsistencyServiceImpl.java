package com.data.wrecker.consistencydimension.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.wrecker.consistencydimension.model.ChangesLog;
import com.data.wrecker.consistencydimension.model.DataProfilerInfo;
import com.data.wrecker.consistencydimension.repository.ChangesLogsRepository;
import com.data.wrecker.consistencydimension.repository.DataProfilerInfoRepo;
import com.data.wrecker.consistencydimension.service.ConsistencyService;
import com.data.wrecker.consistencydimension.service.WaysofConsistencyToBeApplied;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

@Service
@Transactional
public class ConsistencyServiceImpl implements ConsistencyService{

	private static final Logger LOGGER = LogManager.getLogger();
	@Autowired
	private DataProfilerInfoRepo dataProfilerInfoRepo;
	private DataProfilerInfo dataProfilerInfo;
	@Autowired
	private WaysofConsistencyToBeApplied waysofConsistencyToBeApplied;
	private List<DataProfilerInfo> dataProfilerInfoList;
	Random rand;
	
	private List<ChangesLog> changesLogList;
	private ChangesLog changesLog;
	@Autowired
	private ChangesLogsRepository changesLogrepo;
	private Mongo mongo ;
	private DB db;
	
	
	@Override
	public String removeConsistencyDimension(String collectionName, String columnName,List<String> wreckingIds) {
		String firstCollectionName = getFirstVersionOfCollection(collectionName);
		JSONArray datasetArray = getDatasetFromDb(collectionName);
		String columnDataType  = getColumnDataType(firstCollectionName,columnName);
		changesLogList = new ArrayList<ChangesLog>();
		
		try {
			
		for(int j =0; j < wreckingIds.size(); j++ ) {
			
			String objectId = wreckingIds.get(j);
			
			for(int i=0; i < datasetArray.length();i++) {
				
				if(datasetArray.getJSONObject(i).getJSONObject("_id").getString("$oid").equals(objectId)) {
					String colValue = datasetArray.getJSONObject(i).get(columnName).toString();
					changesLog = new ChangesLog();
					changesLog.setColumnName(columnName);
					changesLog.setOid(objectId);
					changesLog.setDimensionName("Consistency");
					changesLog.setOldValue(colValue);
					colValue = removeConsistency(colValue, columnDataType);
					datasetArray.getJSONObject(i).put(columnName,colValue);
					datasetArray.getJSONObject(i).put("isWrecked", true);
					changesLog.setNewValue(colValue);
					changesLogList.add(changesLog);
					addToDb(changesLog);
				}				
			}
		}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return addIntoDatabase(collectionName, datasetArray);
	}
	
	private void addToDb(ChangesLog changesLog2) {
		changesLogrepo.insert(changesLog);		
	}

	private String removeConsistency(String colValue, String columnDatatype) {
		String result = "";
		switch(columnDatatype.toLowerCase()) {
		case "string": 
			LOGGER.info("String");
			result = callServicesForString(colValue);
			break;
		case "integer":	
			LOGGER.info("Integer");			
			result = callServicesForInteger(Integer.parseInt(colValue));
			break;
		case "character":	
			LOGGER.info("Character");
			result = callServicesForChar(colValue);
			break;
		case "date":
			LOGGER.info("Date");
			result = callServicesForDate(colValue);
			break;
		case "boolean":
			LOGGER.info("Boolean");
			result = callServicesForBoolean(colValue);
		}
		return result;
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
	
	
	private String callServicesForString(String colValue) {
		int randomNum = getRandomNumber(2);
		LOGGER.info("Random number selected "+ randomNum);
		String cha = "";
		switch(randomNum) { 
		case 0:
			if(isStringUpper(colValue)) {
				cha = waysofConsistencyToBeApplied.changeItIntoLowerCase(colValue);
			}else {
				cha = waysofConsistencyToBeApplied.reverseCase(colValue);
			}
			LOGGER.info("Value after Change "+cha);
			break;
		case 1:
			cha = waysofConsistencyToBeApplied.reverseCase(colValue);
			LOGGER.info("Value after Change "+cha);
			break;
		}
		return cha;
	}
	
	private String callServicesForChar(String colValue) {
		int randomNum = getRandomNumber(2);
		String cha = "";
		switch(randomNum) {
		case 0:
			if(isStringUpper(colValue)) {
				cha = waysofConsistencyToBeApplied.changeItIntoLowerCase(colValue);
			}else {
				cha = waysofConsistencyToBeApplied.reverseCase(colValue);
			}
			LOGGER.info("Value after Change "+cha);
			break;
		case 1:
			cha = waysofConsistencyToBeApplied.consistencyForCharacters(colValue);
			break;
		}
		return cha;
	}
	
	private String callServicesForInteger(int colValue) {
		getRandomNumber(2);
		String num = "";
		
		switch(2) {
		case 0: 
			num = waysofConsistencyToBeApplied.convertIntegerIntoBinary(colValue);
			break;
		case 1: 
			num = waysofConsistencyToBeApplied.convertToFloat(colValue);		
			
		}
		return num;
	}
	
	private String callServicesForBoolean(String colValue) {
		
		return waysofConsistencyToBeApplied.affectBooleanValues(Boolean.parseBoolean(colValue));
	}
	
	private String callServicesForDate(String colValue) {
		return waysofConsistencyToBeApplied.changeDateFormat(colValue);
	}
	
	private int getRandomNumber(int num) {
		rand = new Random();
		return rand.nextInt(num);		
	}
	
	
	private boolean isStringUpper(String value) {
		boolean stat = false;
		char[] charArray = value.toCharArray();
        
        for(int i=0; i < charArray.length; i++){
            
            //if the character is a letter
            if( Character.isLetter(charArray[i]) ){
                
                //if any character is not in upper case, return false
                if( !Character.isUpperCase( charArray[i] ))
                    stat = false;
                else
                	stat = true;
            }
        }
        
        return stat;
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
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }	
		 mongo.close();
		return collectionName;
	}
	
	private String getFirstVersionOfCollection(String collectionName) {
		String[] name = collectionName.split("_");
		String newCollectionName;
		if(Integer.parseInt(name[name.length - 1]) == 1) {
			newCollectionName = collectionName;
			 LOGGER.info("New Collection Name is "+ collectionName);
		}else {
			newCollectionName = name[0]+"_"+1;
			 LOGGER.info("New Collection Name is "+newCollectionName);
		}
		return newCollectionName;
	}
	
}
