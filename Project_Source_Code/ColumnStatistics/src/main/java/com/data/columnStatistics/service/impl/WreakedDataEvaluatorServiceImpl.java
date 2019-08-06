package com.data.columnStatistics.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.data.columnStatistics.service.WreakedDataEvaluatorService;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

@Transactional
@Service
public class WreakedDataEvaluatorServiceImpl implements WreakedDataEvaluatorService {

	@Override
	public String wreakedDataEvaluation(String fileName) throws JSONException {
		System.out.println("inside wreakedDataEvaluation");
		
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("ReverseEngineering");
		DBCollection collection = db.getCollection("dataset2"); //giving the collection name 
		DBCursor cursor = collection.find();
		List<String> items = new ArrayList<>();
		JSONArray dbList = new JSONArray();
		
		try  {
			while (cursor.hasNext()) {
				String singleRowRecordStr = cursor.next().toString();
				JSONObject jsnobj = new JSONObject(singleRowRecordStr);
				
				
				Iterator columnNames = jsnobj.keys();
				/*columnNames.ke
				System.out.println("singleRowRecordJSON::"+columnNames);*/
			}
		}
			catch(JSONException e) {
				e.printStackTrace();
			}
		
		// add flag to the dataset by default value will be added as false.
		/*BasicDBObject query = new BasicDBObject();
		BasicDBObject update = new BasicDBObject();
		update.put("$set", new BasicDBObject("isAlreadyWreaked", false));
		UpdateResult result = collection.updateMany(query, update);*/
		
		
		return null;

		
	
		}
}
