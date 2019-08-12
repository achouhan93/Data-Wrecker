package com.data.uniquenessDimension.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Override
	public String applyUniquenessDimension(String collectionName, String columnName) {
		LOGGER.info(" Uniqueness Service Started ");
		JSONArray datasetArray = getDatasetFromDb(collectionName);
		List<Integer> randomValues = new ArrayList<Integer>();
		Random randomGenerator = new Random();
		for(int i=0;i<5;i++) {
			int randomNumber = randomGenerator.nextInt(50) ;
			randomValues.add(randomNumber);
			LOGGER.info("Random Number  " +randomNumber);
		}
		
		for(int j =0; j < randomValues.size(); j++ ) {
			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject = datasetArray.getJSONObject(randomValues.get(j));
				jsonObject.remove("_id");
				datasetArray.getJSONObject(randomValues.get(j)).put("isWrecked", true);
				jsonObject.put("isWrecked", true);
				
				datasetArray.put(jsonObject);
				LOGGER.info("datasetArray length " + datasetArray.length() );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		LOGGER.info("Final length " + datasetArray.length() );
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
		LOGGER.info("Length of Array before adding into DB \n"+jsonArray.toString());
		 for (int i =0; i < jsonArray.length(); i++) {
		 
			 DBObject dbObject;
			try {
				dbObject = (DBObject) JSON.parse(jsonArray.getJSONObject(i).toString());
				collection.insert(dbObject);
				LOGGER.info("Data added! "+i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }		
		return newCollectionName;
	}
	
}
