package com.data.uniquenessDimension.serviceImpl;

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

import com.data.uniquenessDimension.model.ChangesLog;
import com.data.uniquenessDimension.repository.ChangesLogsRepository;
import com.data.uniquenessDimension.service.UniquenessDimensionService;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

@Service
@Transactional
public class UniquenessDimensionServiceImpl implements UniquenessDimensionService{

	private static final Logger LOGGER = LogManager.getLogger();
	private List<ChangesLog> changesLogList;
	private ChangesLog changesLog;
	@Autowired
	private ChangesLogsRepository changesLogrepo;
	
	
	@Override
	public String applyUniquenessDimension(String collectionName, String columnName,List<String> wreckingIdsForDimension) {
		JSONArray datasetArray = getDatasetFromDb(collectionName);
		JSONArray changedRecordObj= new JSONArray();
		changesLogList = new ArrayList<ChangesLog>();
		
		try {
			
		for(int j =0; j < wreckingIdsForDimension.size(); j++ ) {
				
				changedRecordObj= new JSONArray();				
				String objectId = wreckingIdsForDimension.get(j);
				
				for(int i =0; i < datasetArray.length(); i++) {
					
						String oid = datasetArray.getJSONObject(i).getJSONObject("_id").getString("$oid");
						if(oid.equals(objectId)) {
							// LOGGER.info("Initial length " + datasetArray.length() );
							changesLog = new ChangesLog();
							changesLog.setDimensionName("Uniqueness");
							changesLog.setDatasetName(collectionName);
							changesLog.setColumnName(columnName);
							changesLog.setOid(objectId);
							changesLog.setOldValue(datasetArray.getJSONObject(i).get(columnName).toString());
							JSONObject jsonObject = new JSONObject();
							jsonObject = datasetArray.getJSONObject(i);
							datasetArray.getJSONObject(i).put("isWrecked", true);
							jsonObject.remove("_id");
							jsonObject.put("isWrecked", true);				
							changedRecordObj.put(jsonObject);
							changesLog.setNewValue(datasetArray.getJSONObject(i).get(columnName).toString());
							changesLogList.add(changesLog);
							addToDb(changesLog);
							break;
						}
					
					
					
			}
		
		}
		
		
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		// LOGGER.info("Final length " + datasetArray.length() );
		
		for(int k=0;k<changedRecordObj.length();k++) {
			
			try {
				datasetArray.put(changedRecordObj.getJSONObject(k));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return addIntoDatabase(collectionName,datasetArray);
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
	
	private void addToDb(ChangesLog changesLog) {
		changesLogrepo.insert(changesLog);
	}
	
	
	private String addIntoDatabase(String collectionName, JSONArray jsonArray) {
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("ReverseEngineering");
		String[] name = collectionName.split("_");
		List<Document> jsonList = new ArrayList<Document>();
		
		String newCollectionName;
		int versionNumber;
		if(name[name.length - 1].isEmpty()) {
			newCollectionName = name[0]+"_1";
			LOGGER.info("New Collection Name is "+ name[0]+"_1");
		}else {
			versionNumber = Integer.parseInt(name[name.length - 1]) + 1;
			newCollectionName = name[0]+"_"+versionNumber;
			LOGGER.info("New Collection Name is "+newCollectionName);
		}
			
		DBCollection collection = db.createCollection(newCollectionName, null);
		 for (int i =0; i < jsonArray.length(); i++) {
		 
			 DBObject dbObject;
			try {
				dbObject = (DBObject) JSON.parse(jsonArray.getJSONObject(i).toString());
				collection.insert(dbObject);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }		
		return newCollectionName;
	}
	
}
