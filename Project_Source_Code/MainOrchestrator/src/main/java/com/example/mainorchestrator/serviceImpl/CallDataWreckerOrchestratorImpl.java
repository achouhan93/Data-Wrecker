package com.example.mainorchestrator.serviceImpl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mainorchestrator.entity.DatasetDetails;
import com.example.mainorchestrator.service.CallDataWreckerOrchestratorService;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;


@Service
@Transactional
public class CallDataWreckerOrchestratorImpl implements CallDataWreckerOrchestratorService{

	private int dataWreckingPercentage = 0;
	
	@Override
	public String callDataWreckerOrchestrator(DatasetDetails datasetDetails) {
		
		
		getColumnNames("ReverseEngineering", datasetDetails.getCollectionName());
		
		return "The wrecking Percentage is "+randomNumberGenerator() ;
		
	}
	
	private int randomNumberGenerator() {
		if(dataWreckingPercentage > 0) {
			return dataWreckingPercentage;
		}else {
			Random rand = new Random();
			dataWreckingPercentage = rand.nextInt(10) + 20;
			return dataWreckingPercentage;
		}		
	}
	
	@SuppressWarnings("deprecation")
	public List<String> getColumnNames(String databaseName, String collectionName) {
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB(databaseName);
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
		JSONObject json_obj;
		try {
			json_obj = dbList.getJSONObject(7);
			Iterator columnNames = json_obj.keys();
		
			while(columnNames.hasNext()) {
				String key = (String) columnNames.next();
				columnNamesList.add(key);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i =0; i< columnNamesList.size(); i++) {
			if(columnNamesList.get(i).isEmpty() || columnNamesList.get(i).equals("_id") ) {
				columnNamesList.remove(i);
			}
		}
		/*
		 * for(int j = 0;  j< dbList.length(); j++ ) {
			try {
			
				String jsonString =  dbList.getJSONObject(j).put("columnNames", columnNamesList).toString();
				DBObject dbObj = (DBObject) JSON.parse(jsonString);
				collection.save(dbObj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		*
		*/
		return columnNamesList;
		
	}

}
