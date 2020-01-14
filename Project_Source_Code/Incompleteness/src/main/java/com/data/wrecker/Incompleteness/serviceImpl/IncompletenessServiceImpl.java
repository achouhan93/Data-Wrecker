package com.data.wrecker.Incompleteness.serviceImpl;

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
import org.springframework.web.client.RestTemplate;

import com.data.wrecker.Incompleteness.model.ChangesLog;
import com.data.wrecker.Incompleteness.model.ColumnStats;
import com.data.wrecker.Incompleteness.model.DatasetStats;
import com.data.wrecker.Incompleteness.repository.ChangesLogsRepository;
import com.data.wrecker.Incompleteness.service.IncompletenessService;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

@Service
@Transactional
public class IncompletenessServiceImpl implements IncompletenessService{

	private static final Logger LOGGER = LogManager.getLogger();
	private List<ChangesLog> changesLogList;
	private ChangesLog changesLog;
	@Autowired
	private ChangesLogsRepository changesLogrepo;
	private Mongo mongo;
	private DB db;
	private Random rand;
	
	@Override
	public String removeValues(String collectionName, String columnName, List<String> wreckingIdsForDimension) {

		// TODO Auto-generated method stub
		JSONArray datasetArray = getDatasetFromDb(collectionName);
		changesLogList = new ArrayList<ChangesLog>();
		
		List<Integer> recordIndexes = new ArrayList<Integer>();
		
		
		
		try {
		for(int j =0; j < recordIndexes.size(); j++ ) {
			
			changesLog = new ChangesLog();
			changesLog.setColumnName(columnName);
			//changesLog.setOid(datasetArray.getJSONObject(ds).getJSONObject("_id").get("$oid").toString());
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
		BasicDBObject whereQuery = new BasicDBObject();
		DBCollection collection = db.getCollection(collectionName); //giving the collection name 
		whereQuery.put("isWrecked", true);
		DBCursor cursor = collection.find();
		int cursor1 = collection.find(whereQuery).count();
		System.out.println("count "+cursor1);
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



	@Override
	public String removeValuesForDecimalAndInteger(String collectionName, String columnName, int wreckingCount,
			DatasetStats datasetStats) {
		int medianVal = datasetStats.getProfilingInfo().getColumnStats().getAverageValue();
		JSONArray datasetArray = getDatasetFromDb(collectionName);
		changesLogList = new ArrayList<ChangesLog>();
		int wreackedCount = 0;
		rand = new Random();
		int random = rand.nextInt(1);
		
		
		switch (random) {
		
		case 0:
			collectionName = removeThroughMlModels(collectionName, columnName, wreckingCount,datasetStats.getProfilingInfo().getColumnStats());
			break;
		case 1:
			collectionName = removeUsingMeanMediad(collectionName, columnName, wreckingCount, datasetStats);
			break;
		default:
			break;
		
		}
		
		return collectionName;
	}

	@Override
	public String removeValuesBoolean(String collectionName, String columnName, int wreckingCount,
			ColumnStats colStats) {
		return removeThroughMlModels(collectionName, columnName, wreckingCount,colStats);
	}

	private String removeThroughMlModels(String collectionName, String columnName, int wreckingCount,ColumnStats colStats) {
		
		if(colStats.getMultiColumnStats().getDependantColumnNames().size() > 0) {
		
			String url = "http://127.0.0.1:5000/?collectionName="+collectionName+"&columnName="+columnName;
			
			String modelResult = new RestTemplate().getForObject(url, String.class);
			JSONArray datasetArray = getDatasetFromDb(collectionName);
			
				
			if(modelResult.equals("Success")) {
				
				
				int wreckedCount = 0;
				changesLogList = new ArrayList<ChangesLog>();
				rand = new Random();
				System.out.println("Wrecking count "+wreckingCount);
				
				for(int index = 0; index< datasetArray.length();index++) {
					try {
						
						if(wreckedCount < wreckingCount) {
							
							if(datasetArray.getJSONObject(index).has("isWrecked") && datasetArray.getJSONObject(index).has("_id")) {
								if(datasetArray.getJSONObject(index).getBoolean("isWrecked") == false) {
									String oid = datasetArray.getJSONObject(index).getJSONObject("_id").getString("$oid");
									url = "http://127.0.0.1:5000/model?collectionName="+collectionName+"&oid="+oid+"&columnName="+columnName;
									String result =  new RestTemplate().getForObject(url, String.class);
									int res = Integer.parseInt(result);
									boolean resValue = (res == 1);
									boolean colValue = datasetArray.getJSONObject(index).getBoolean(columnName);
									if(colValue == resValue) {
										changesLog = new ChangesLog();
										changesLog.setColumnName(columnName);
										changesLog.setOid(datasetArray.getJSONObject(index).getJSONObject("_id").getString("$oid").toString());
										changesLog.setDimensionName("Completeness");
										changesLog.setDatasetName(collectionName);
										changesLog.setOldValue(datasetArray.getJSONObject(index).get(columnName).toString());
										datasetArray.getJSONObject(index).put(columnName, "");
										datasetArray.getJSONObject(index).put("isWrecked", true);
										changesLog.setNewValue("");
										changesLogList.add(changesLog);
										datasetArray.getJSONObject(index).put("isWrecked", true);
										System.out.println("Wrecked count "+wreckedCount);
										wreckedCount++;
									}									
								}
							}							
						}
						
					}catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			
			addToDb(changesLogList);
			
			return addIntoDatabase(collectionName,datasetArray);
			
		}else {
			
			LOGGER.info("There were no dependant columns identified");
			return collectionName;
			
		}
		
		
	}
	
	private String removeUsingMeanMediad(String collectionName, String columnName, int wreckingCount,DatasetStats datasetStats) {
		
		JSONArray datasetArray = getDatasetFromDb(collectionName);
		changesLogList = new ArrayList<ChangesLog>();
		int wreackedCount = 0;
		int medianVal = datasetStats.getProfilingInfo().getColumnStats().getAverageValue();
		
		for(int ds = 0; ds< datasetArray.length();ds++) {
			
			try {
				if(wreackedCount<wreckingCount) {						
					if(datasetArray.getJSONObject(ds).has("isWrecked") && datasetArray.getJSONObject(ds).has("_id")) {
						if(datasetArray.getJSONObject(ds).getBoolean("isWrecked") == false) {
							if(datasetArray.getJSONObject(ds).getInt(columnName) == medianVal) {
								changesLog = new ChangesLog();
								changesLog.setColumnName(columnName);
								changesLog.setOid(datasetArray.getJSONObject(ds).getJSONObject("_id").get("$oid").toString());
								changesLog.setDimensionName("Completeness");
								changesLog.setDatasetName(collectionName);
								changesLog.setOldValue(datasetArray.getJSONObject(ds).get(columnName).toString());
								datasetArray.getJSONObject(ds).put(columnName, "");
								datasetArray.getJSONObject(ds).put("isWrecked", true);
								changesLog.setNewValue("");
								changesLogList.add(changesLog);
								wreackedCount++;									
							}
						}
					}						
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	addToDb(changesLogList);
	
	return addIntoDatabase(collectionName,datasetArray);
	}
	
}
