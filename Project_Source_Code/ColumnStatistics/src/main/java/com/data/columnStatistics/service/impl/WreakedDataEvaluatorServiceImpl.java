package com.data.columnStatistics.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
				jsnobj.put("isWrecked", false);
				dbList.put(jsnobj); // getting all the records into a list as json Objects isWrecked
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
		columnNamesList.remove("isWrecked");
		System.out.println("+++++" + columnNamesList);
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


		System.out.println("columnDataTypeList" + columnDataTypeList);
		for (int i = 0; i < datasetSize; i++) {
			for (int j = 0; j < columnNamesList.size(); j++) {

				// reading each record
				colValue = dbList.getJSONObject(i).get(columnNamesList.get(j)).toString();
				columnName = columnNamesList.get(j);
				/*
				 * System.out.println("Record Data ColumnName " + columnName + " ColumnValue " +
				 * colValue + " DataType " + columnDataTypeList.get(j));
				 */
				// String isWrecked = null;
				if (colValue.equals(null)) {
					dbList.getJSONObject(i).put("isWrecked", true);
					continue;
				} else {
					switch (columnDataTypeList.get(j)) {
					case "Boolean":
						if (!(colValue.equalsIgnoreCase("True") || colValue.equalsIgnoreCase("False"))) {
							/*
							 * System.out.println("Record Data ColumnName:" + columnName + " ColumnValue:" +
							 * colValue + " DataType:" + columnDataTypeList.get(j) );
							 */
							System.out.println("current value:"+colValue+" datatype of a column:"+columnDataTypeList.get(j));
							dbList.getJSONObject(i).put("isWrecked", true);
							System.out.println("invalid value: "+colValue+" for datatype :Boolean");
						}
						break;
						//int ^-?(0|[1-9]\\d*)(?<!-0)$
					case "Integer":
						if (!(colValue.matches("^-?[0-9][0-9,.]+$") 
								|| colValue.matches("^-?(0|[1-9]\\d*)(?<!-0)$") ) ) {
							/*
							 * System.out.println("Record Data ColumnName " + columnName + " ColumnValue " +
							 * colValue + " DataType " + columnDataTypeList.get(j) );
							 */
							System.out.println("invalid value: "+colValue+" for datatype :Integer");
							dbList.getJSONObject(i).put("isWrecked", true);
						}
						break;
						//dec ^-?[0-9][0-9,.]+$
					case "Decimal":
						if (!(colValue.matches("^-?[0-9][0-9,.]+$") 
								|| colValue.matches("^-?(0|[1-9]\\d*)(?<!-0)$") ) ) {
							/*
							 * System.out.println("Record Data ColumnName " + columnName + " ColumnValue " +
							 * colValue + " DataType " + columnDataTypeList.get(j));
							 */
							System.out.println("current value:"+colValue+" datatype of a column:"+columnDataTypeList.get(j));
							dbList.getJSONObject(i).put("isWrecked", true);
						}
						break;
					case "Date":
						
						int countdot = StringUtils.countMatches(colValue, ".");
						int countslash = StringUtils.countMatches(colValue, "/");
						int countdash = StringUtils.countMatches(colValue, "-");
						if (! ((countdot == 2) || (countslash == 2) || (countdash == 2))) {
							/*
							 * System.out.println("Record Data ColumnName " + columnName + " ColumnValue " +
							 * colValue + " DataType " + columnDataTypeList.get(j));
							 */
							System.out.println("current value:"+colValue+" datatype of a column:"+columnDataTypeList.get(j));
							dbList.getJSONObject(i).put("isWrecked", true);
						}
						break;
					case "Character":
						if ((colValue.length() == 1) && colValue.matches("/^[A-Za-z]+$/")) {
							/*
							 * System.out.println("Record Data ColumnName " + columnName + " ColumnValue " +
							 * colValue + " DataType " + columnDataTypeList.get(j));
							 */
							System.out.println("current value:"+colValue+" datatype of a column:"+columnDataTypeList.get(j));
							dbList.getJSONObject(i).put("isWrecked", true);
						}
						break;
						
					}
					/*
					 * if (dbList.getJSONObject(i).get("isWrecked").equals(true)) { break; }
					 */
				}
			}
		}
		String[] changedFileName = fileName.split("_");
		String updatedFileName = changedFileName[0]+"_1";
		dataProfilerInfo.setFileName(updatedFileName);
		columnStatsRepo.save(dataProfilerInfo);
		return addIntoDatabase(fileName, dbList);

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
		System.out.println("collectionNameWithoutPreviousVersion :" + collectionNameWithoutPreviousVersion);
		String newCollectionName;
		int versionNumber;
		if (!collectionNameWithoutPreviousVersion.isEmpty()) {
			newCollectionName = collectionNameWithoutPreviousVersion + "_1";
			LOGGER.info("New Collection Name is " + name[0] + "_1");
		} else {
			versionNumber = Integer.parseInt(name[name.length - 1]) + 1;
			newCollectionName = name[0] + "_" + versionNumber;
			LOGGER.info("New Collection Name is " + newCollectionName);
		}

		DBCollection collection = db.createCollection(newCollectionName, null);
		for (int i = 0; i < jsonArray.length(); i++) {

			DBObject dbObject;
			try {
				dbObject = (DBObject) JSON.parse(jsonArray.getJSONObject(i).toString());
				collection.insert(dbObject);
				// LOGGER.info("Data added!");
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
		}
		return "Success";
	}
}
