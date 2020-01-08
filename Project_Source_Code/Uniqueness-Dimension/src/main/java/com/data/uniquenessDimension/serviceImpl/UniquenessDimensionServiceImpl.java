package com.data.uniquenessDimension.serviceImpl;

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
	private Mongo mongo;
	private DB db;


	@Override
	public String applyUniquenessDimension(String collectionName, String columnName,List<String> wreckingIdsForDimension) {
		JSONArray datasetArray = getDatasetFromDb(collectionName);
		JSONArray changedRecordObj= new JSONArray();
		changesLogList = new ArrayList<ChangesLog>();
		List<String> recordIndexes = new ArrayList<String>();

		for(String str : wreckingIdsForDimension) {
			recordIndexes.add(str);
		}
		try {

			changedRecordObj= new JSONArray();

		for(int j =0; j < recordIndexes.size(); j++ ) {

			for(int k = 0; k< datasetArray.length(); k++ ) {
				if(datasetArray.getJSONObject(k).has("_id") && datasetArray.getJSONObject(k).has("isWrecked")) {
					if(datasetArray.getJSONObject(k).getJSONObject("_id").getString("$oid").equals(recordIndexes.get(j)) && datasetArray.getJSONObject(k).getBoolean("isWrecked") == false) {
						
						JSONObject jsonObject = new JSONObject();
						jsonObject = datasetArray.getJSONObject(k);
						changesLog = new ChangesLog();
						changesLog.setDimensionName("Uniqueness");
						changesLog.setDatasetName(collectionName);
						changesLog.setColumnName(columnName);
						changesLog.setOid(" ");
						changesLog.setOldValue(datasetArray.getJSONObject(k).get(columnName).toString());

						datasetArray.getJSONObject(k).put("isWrecked", true);
						LOGGER.info("OBJ : "+datasetArray.getJSONObject(k).put("isWrecked", true).toString());
						jsonObject.put("_id","");
						jsonObject.put("isWrecked", true);
						changedRecordObj.put(jsonObject);
						//addIntoDatabase(collectionName,changedRecordObj);
						changesLog.setNewValue(datasetArray.getJSONObject(k).get(columnName).toString());
						changesLogList.add(changesLog);						
						
					}
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

		addToDb(changesLog);
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
		mongo = new Mongo("localhost", 27017);
		db = mongo.getDB("ReverseEngineering");
		String[] name = collectionName.split("_");
		List<Document> jsonList = new ArrayList<Document>();

		String newCollectionName;
		int versionNumber;
		if (name[name.length - 1].isEmpty()) {
			newCollectionName = name[0] + "_1";
			// LOGGER.info("New Collection Name is " + name[0] + "_1");
		} else {
			versionNumber = Integer.parseInt(name[name.length - 1]) + 1;
			newCollectionName = name[0] + "_" + versionNumber;
			// LOGGER.info("New Collection Name is " + newCollectionName);
		}

		DBCollection collection = db.createCollection(collectionName, null);
		for (int i = 0; i < jsonArray.length(); i++) {

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



}
