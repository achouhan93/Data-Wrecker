package com.data.completenessDimension.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.completenessDimension.model.ChangesLog;
import com.data.completenessDimension.repository.ChangesLogsRepository;
import com.data.completenessDimension.service.CompletenessDimensionService;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;


@Service
@Transactional
public class CompletenessDimensionServiceImpl implements CompletenessDimensionService{

	private static final Logger LOGGER = LogManager.getLogger();
	private List<ChangesLog> changesLogList;
	private ChangesLog changesLog;
	@Autowired
	private ChangesLogsRepository changesLogrepo;
	private Mongo mongo;
	private DB db;

	
	@Override
	public String removeValues(String collectionName, String columnName,List<String> wreckingIdsForDimension) {
		// TODO Auto-generated method stub
		JSONArray datasetArray = getDatasetFromDb(collectionName);
		changesLogList = new ArrayList<ChangesLog>();
		
		List<Integer> recordIndexes = new ArrayList<Integer>();

		for(String str : wreckingIdsForDimension) {
			recordIndexes.add(Integer.valueOf(str));
		}
		
		try {
		for(int j =0; j < recordIndexes.size(); j++ ) {
			
			changesLog = new ChangesLog();
			changesLog.setColumnName(columnName);
			changesLog.setOid(recordIndexes.get(j));
			changesLog.setDimensionName("Completeness");
			changesLog.setDatasetName(collectionName);
			changesLog.setOldValue(datasetArray.getJSONObject(recordIndexes.get(j)).get(columnName).toString());
			datasetArray.getJSONObject(recordIndexes.get(j)).put(columnName, "");
			datasetArray.getJSONObject(recordIndexes.get(j)).put("isWrecked", true);
			changesLog.setNewValue("");
			changesLogList.add(changesLog);
			//addToDb(changesLog);
		
		}
		
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addToDb(changesLogList);
		
		return addIntoDatabase(collectionName,datasetArray);		
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
			LOGGER.info("New Collection Name is "+ name[0]+"_1");
		}else {
			versionNumber = Integer.parseInt(name[name.length - 1]) + 1;
			newCollectionName = name[0]+"_"+versionNumber;
			LOGGER.info("New Collection Name is "+newCollectionName);
		}
			
		DBCollection collection = db.createCollection(collectionName, null);
		 for (int i =0; i < jsonArray.length(); i++) {
		 
			 DBObject dbObject;
			try {
				dbObject = (DBObject) JSON.parse(jsonArray.getJSONObject(i).toString());
				collection.save(dbObject);
				//LOGGER.info("Data added!");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }		
		 mongo.close();
		return collectionName;
	}
	
	
	

}
