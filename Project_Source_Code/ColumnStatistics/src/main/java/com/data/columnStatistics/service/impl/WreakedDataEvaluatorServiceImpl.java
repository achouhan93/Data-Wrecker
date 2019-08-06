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
		DBCollection collection = db.getCollection(fileName); //giving the collection name 
		DBCursor cursor = collection.find();
		List<String> items = new ArrayList<>();
		JSONArray dbList = new JSONArray();
		List<String> columnNamesList = new ArrayList<String>();
		
		
		try  {
			while (cursor.hasNext()) {
				String singleRowRecordStr = cursor.next().toString();
				JSONObject jsnobj = new JSONObject(singleRowRecordStr);
				dbList.put(jsnobj); //getting all the records into a list as json Objects
				//System.out.println("Data Record is "+jsnobj.toString());
				/*columnNames.ke
				System.out.println("singleRowRecordJSON::"+columnNames);*/
			}
		}
			catch(JSONException e) {
				e.printStackTrace();
			}
		//to read each record from the JSONArray dbList here i am Reading first 10 records
		
		String columnName;
		String colValue;
		
		columnNamesList = getColumnHeaders(dbList.getJSONObject(0));
		
		for(int i =0; i < 10; i++ ) {
			for(int j=0; j < columnNamesList.size();j++) {
				
				//reading each record
				colValue = dbList.getJSONObject(i).get(columnNamesList.get(j)).toString();
				columnName = columnNamesList.get(j);				
				System.out.println("Record Data ColumnName"+columnName+" ColumnValue "+colValue);
				
			}		
		}
		
		// add flag to the dataset by default value will be added as false.
		/*BasicDBObject query = new BasicDBObject();
		BasicDBObject update = new BasicDBObject();
		update.put("$set", new BasicDBObject("isAlreadyWreaked", false));
		UpdateResult result = collection.updateMany(query, update);*/
		
		
		return null;	
	
		}
	
	
	
	
	private List<String> getColumnHeaders(JSONObject jsonRecord) {
		Iterator columnNames = jsonRecord.keys();
		List<String> columnNamesList = new ArrayList<String>();
		
		while(columnNames.hasNext()) {
			String key = (String) columnNames.next();
			columnNamesList.add(key);
		}
		
		for(int i =0; i< columnNamesList.size(); i++) {
			if(columnNamesList.get(i).isEmpty() || columnNamesList.get(i).equals("_id") ) {
				columnNamesList.remove(i);
			}
		}	
		
		return columnNamesList;
	}
}
