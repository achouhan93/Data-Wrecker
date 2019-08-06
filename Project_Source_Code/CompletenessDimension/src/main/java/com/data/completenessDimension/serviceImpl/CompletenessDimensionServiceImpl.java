package com.data.completenessDimension.serviceImpl;

import com.data.completenessDimension.service.CompletenessDimensionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	@Override
	public String removeValues() {
		// TODO Auto-generated method stub
		JSONArray datasetArray = getDatasetFromDb();
		String columnName = "Title";
		List<Integer> randomValues = new ArrayList<Integer>();
		Random randomGenerator = new Random();
		for(int i=0;i<5;i++) {
			int randomNumber = randomGenerator.nextInt(50) ;
			randomValues.add(randomNumber);
			LOGGER.info("Random Number  " +randomNumber);
		}
		for(int j =0; j < randomValues.size(); j++ ) {
			try {
				LOGGER.info("Old Data \n" +  datasetArray.getJSONObject(randomValues.get(j)));
				datasetArray.getJSONObject(randomValues.get(j)).put(columnName, "");
				LOGGER.info("Wrecked Data \n" +  datasetArray.getJSONObject(randomValues.get(j)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return "Success";
	}
	
	private JSONArray getDatasetFromDb() {
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("ReverseEngineering");
		DBCollection collection = db.getCollection("testdatasetSample1"); //giving the collection name 
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

}
