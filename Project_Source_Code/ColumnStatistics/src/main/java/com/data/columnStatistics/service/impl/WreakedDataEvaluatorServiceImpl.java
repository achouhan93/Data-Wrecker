package com.data.columnStatistics.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import com.data.columnStatistics.model.DataProfilerInfo;
import com.data.columnStatistics.model.DatasetStats;
import com.data.columnStatistics.repository.ColumnStatsRepo;
import com.data.columnStatistics.service.WreakedDataEvaluatorService;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

@Transactional
@Service
public class WreakedDataEvaluatorServiceImpl implements WreakedDataEvaluatorService {
	
	private static final Logger LOGGER = LogManager.getLogger();

	@Autowired
	ColumnStatsRepo columnStatsRepo;

	@Override
	public String wreakedDataEvaluation(String fileName) throws JSONException {
		System.out.println("inside wreakedDataEvaluation");

		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("ReverseEngineering");
		DBCollection collection = db.getCollection(fileName); // giving the collection name
		DBCursor cursor = collection.find();
		List<String> items = new ArrayList<>();
		JSONArray dbList = new JSONArray();
		List<String> columnNamesList = new ArrayList<String>();
		int datasetSize = 0;
		try {
			while (cursor.hasNext()) {
				String singleRowRecordStr = cursor.next().toString();
				JSONObject jsnobj = new JSONObject(singleRowRecordStr);
				jsnobj.put("isWreaked",false);
				dbList.put(jsnobj); // getting all the records into a list as json Objects
				datasetSize++;
				// System.out.println("Data Record is "+jsnobj.toString());
				/*
				 * columnNames.ke System.out.println("singleRowRecordJSON::"+columnNames);
				 */
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// to read each record from the JSONArray dbList here i am Reading first 10
		// records

		String columnName;
		String colValue;

		List<DatasetStats> columnPatternModel = null;
		String dataTypeOfAColumn = null;
		List<DataProfilerInfo> datasetStatsList = columnStatsRepo.findAll();
		DataProfilerInfo dataProfilerInfo = new DataProfilerInfo();
		List<String> columnDataTypeList = new ArrayList<String>();
		// columnPatternModel.get(i).getProfilingInfo();
		columnNamesList = getColumnHeaders(dbList.getJSONObject(0));
		columnNamesList.remove("isWreaked");
		System.out.println("+++++"+columnNamesList);
		for (int i = 0; i < datasetStatsList.size(); i++) {
			if (datasetStatsList.get(i).getFileName().equals(fileName)) {
				dataProfilerInfo = datasetStatsList.get(i);
				columnPatternModel = dataProfilerInfo.getDatasetStats();
				for (int j = 0; j < columnNamesList.size(); j++) {
					dataTypeOfAColumn = columnPatternModel.get(j).getProfilingInfo().getColumnDataType();
					columnDataTypeList.add(dataTypeOfAColumn);
				}
			}

		}
		System.out.println("++++++++++" + columnDataTypeList);
		for (int i = 0; i < datasetSize; i++) {
			for (int j = 0; j < columnNamesList.size(); j++) {

				// reading each record
				colValue = dbList.getJSONObject(i).get(columnNamesList.get(j)).toString();
				columnName = columnNamesList.get(j);
				System.out.println("Record Data ColumnName " + columnName + " ColumnValue " + colValue + " DataType "
						+ columnDataTypeList.get(j));
				//String isWreaked = null;
				switch (columnDataTypeList.get(j)) {
				case "Boolean":
					if (!(colValue.equalsIgnoreCase("True") || colValue.equalsIgnoreCase("False")
							|| colValue.equalsIgnoreCase("1") || colValue.equalsIgnoreCase("0"))
							|| colValue.equals(null)) {
						System.out.println("Record Data ColumnName:" + columnName + " ColumnValue:" + colValue
								+ " DataType:" + columnDataTypeList.get(j) );
						dbList.getJSONObject(i).put("isWreaked",true);
						System.out.println("+++++++++++"+dbList);
					}
					break;
				case "String":
					System.out.println("Record Data ColumnName " + columnName + " ColumnValue " + colValue
							+ " DataType " + columnDataTypeList.get(j));
					break;
				case "Integer":
					if (colValue.matches("^(\\+|-)?\\d+$")) {
						System.out.println("Record Data ColumnName " + columnName + " ColumnValue " + colValue
								+ " DataType " + columnDataTypeList.get(j) );
						dbList.getJSONObject(i).put("isWreaked",true);
					}
					break;
				case "Double":
					if (colValue.matches("^-?[0-9]\\d*(\\.\\d+)?$")) {
						System.out.println("Record Data ColumnName " + columnName + " ColumnValue " + colValue
								+ " DataType " + columnDataTypeList.get(j));
						dbList.getJSONObject(i).put("isWreaked",true);
					}
					break;
				case "Date":
					int count = 0;
					count = StringUtils.countMatches(colValue, ".");
					count = StringUtils.countMatches(colValue, "/");
					count = StringUtils.countMatches(colValue, "-");
					if (count == 2) {
						System.out.println("Record Data ColumnName " + columnName + " ColumnValue " + colValue
								+ " DataType " + columnDataTypeList.get(j));
						dbList.getJSONObject(i).put("isWreaked",true);
					}
					break;
				case "Character":
					if (colValue.length() == 1) {
						System.out.println("Record Data ColumnName " + columnName + " ColumnValue " + colValue
								+ " DataType " + columnDataTypeList.get(j));
						dbList.getJSONObject(i).put("isWreaked",true);
					}
					break;
				}

			}
		}

		return addIntoDatabase(fileName,dbList);

	}

	private List<String> getColumnHeaders(JSONObject jsonRecord) {
		Iterator columnNames = jsonRecord.keys();
		List<String> columnNamesList = new ArrayList<String>();

		while (columnNames.hasNext()) {
			String key = (String) columnNames.next();
			columnNamesList.add(key);
		}

		for (int i = 0; i < columnNamesList.size(); i++) {
			if (columnNamesList.get(i).isEmpty() || columnNamesList.get(i).equals("_id")) {
				columnNamesList.remove(i);
			}
		}

		return columnNamesList;
	}
	
	
	private String addIntoDatabase(String collectionName, JSONArray jsonArray) {
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("ReverseEngineering");
		String[] name = collectionName.split("_");
		List<Document> jsonList = new ArrayList<Document>();
		int lastUnderscoreIndex = collectionName.lastIndexOf("_");
        String collectionNameWithoutPreviousVersion = collectionName.substring(0, lastUnderscoreIndex);
        System.out.println("collectionNameWithoutPreviousVersion :"+collectionNameWithoutPreviousVersion);
		String newCollectionName;
		int versionNumber;
		if(!collectionNameWithoutPreviousVersion.isEmpty()) {
			newCollectionName = collectionNameWithoutPreviousVersion+"_1";
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
				//LOGGER.info("Data added!");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		return newCollectionName;
	}
}