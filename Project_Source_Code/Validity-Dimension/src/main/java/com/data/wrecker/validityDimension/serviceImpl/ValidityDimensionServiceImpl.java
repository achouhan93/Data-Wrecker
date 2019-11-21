package com.data.wrecker.validityDimension.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.// LOGGER;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.wrecker.validityDimension.model.ChangesLog;
import com.data.wrecker.validityDimension.model.DataProfilerInfo;
import com.data.wrecker.validityDimension.repository.ChangesLogsRepository;
import com.data.wrecker.validityDimension.repository.DataProfilerInfoRepo;
import com.data.wrecker.validityDimension.service.ValidityDimensionService;
import com.data.wrecker.validityDimension.service.WaysToAffectValidityService;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

@Service
@Transactional
public class ValidityDimensionServiceImpl implements ValidityDimensionService {

	// private static final // LOGGER // LOGGER = LogManager.get// LOGGER();
	@Autowired
	private DataProfilerInfoRepo dataProfilerInfoRepo;
	private DataProfilerInfo dataProfilerInfo;
	@Autowired
	private WaysToAffectValidityService waysToAffectValidityService;
	private List<DataProfilerInfo> dataProfilerInfoList;
	Random rand;

	private List<ChangesLog> changesLogList;
	private ChangesLog changesLog;
	@Autowired
	private ChangesLogsRepository changesLogrepo;
	private Mongo mongo;
	private DB db;

	@Override
	public String removeValidityDimension(String collectionName, String columnName, List<String> wreckingIds) {
		String firstCollectionName = getFirstVersionOfCollection(collectionName);
		JSONArray datasetArray = getDatasetFromDb(collectionName);
		String columnDataType = getColumnDataType(firstCollectionName, columnName);
		changesLogList = new ArrayList<ChangesLog>();
		List<Integer> recordIndexes = new ArrayList<Integer>();

		for(String str : wreckingIds) {
			recordIndexes.add(Integer.valueOf(str));
		}
		

		try {
			for (int j = 0; j < recordIndexes.size(); j++) {
				
						String colValue = datasetArray.getJSONObject(recordIndexes.get(j)).get(columnName).toString();
						changesLog = new ChangesLog();
						changesLog.setColumnName(columnName);
						changesLog.setOid(recordIndexes.get(j));
						changesLog.setDimensionName("Validity");
						changesLog.setDatasetName(collectionName);
						changesLog.setOldValue(colValue);
						colValue = removeValidity(colValue, columnDataType);
						datasetArray.getJSONObject(recordIndexes.get(j)).put(columnName, colValue);
						datasetArray.getJSONObject(recordIndexes.get(j)).put("isWrecked", true);
						changesLog.setNewValue(colValue);
						changesLogList.add(changesLog);
						//addToDb(changesLog);
					
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addToDb(changesLogList);
		
		return addIntoDatabase(collectionName, datasetArray);
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
		// mongo.close();
		return collectionName;
	}

	private void addToDb(List<ChangesLog> changesLogList) {
		for(int i =0; i < changesLogList.size(); i++) {
			changesLogrepo.insert(changesLogList.get(i));
		}
	}

	private JSONArray getDatasetFromDb(String collectionName) {
		mongo = new Mongo("localhost", 27017);
		db = mongo.getDB("ReverseEngineering");
		DBCollection collection = db.getCollection(collectionName); // giving the collection name
		DBCursor cursor = collection.find();
		JSONArray dbList = new JSONArray();
		List<String> columnNamesList = new ArrayList<String>();

		while (cursor.hasNext()) {
			String str = cursor.next().toString();
			try {
				JSONObject jsnobj = new JSONObject(str);
				dbList.put(jsnobj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// mongo.close();
		return dbList;
	}

	private String getColumnDataType(String collectionName, String columnName) {
		dataProfilerInfoList = dataProfilerInfoRepo.findAll();
		String colDatatype = "";
		for (int i = 0; i < dataProfilerInfoList.size(); i++) {
			if (dataProfilerInfoList.get(i).getFileName().equals(collectionName)) {
				dataProfilerInfo = new DataProfilerInfo();
				dataProfilerInfo = dataProfilerInfoList.get(i);
				break;
			}
		}
		for (int i = 0; i < dataProfilerInfo.getDatasetStats().size(); i++) {

			if (dataProfilerInfo.getDatasetStats().get(i).getColumnName().equals(columnName)) {
				colDatatype = dataProfilerInfo.getDatasetStats().get(i).getProfilingInfo().getColumnDataType();
				break;
			}
		}
		return colDatatype;
	}

	private String getFirstVersionOfCollection(String collectionName) {
		String[] name = collectionName.split("_");
		String newCollectionName;
		if (Integer.parseInt(name[name.length - 1]) == 1) {
			newCollectionName = collectionName;
			// LOGGER.info("New Collection Name is " + collectionName);
		} else {
			newCollectionName = name[0] + "_" + 1;
			// LOGGER.info("New Collection Name is " + newCollectionName);
		}
		return collectionName;
	}

	private String removeValidity(String colValue, String columnDatatype) {
		String result = " ";
		switch (columnDatatype.toLowerCase()) {
		case "string":
			// LOGGER.info("String");
			if (!colValue.isEmpty()) {
				result = callServicesForString(colValue);
			}
			break;
		case "integer":
			// LOGGER.info("Integer");
			if (!colValue.isEmpty() && colValue.matches("^[0-9]*$")) {
				result = callServicesForInteger(Integer.parseInt(colValue));
			}
			break;
		case "character":
			// LOGGER.info("Character");
			if (!colValue.isEmpty()) {
				result = callServicesForChar(colValue);
			}
			break;
		case "date":
			// LOGGER.info("Date");
			if (!colValue.isEmpty()) {
				result = callServicesForDate(colValue);
			}
			break;
		case "boolean":
			// LOGGER.info("Boolean");
			if (!colValue.isEmpty()) {
				result = callServicesForBoolean(colValue);
			}
			break;
			// decimal use this regex - impl pending
		case "decimal":
			// LOGGER.info("decimal");
			if (!colValue.isEmpty() && colValue.matches("^(\\d*\\.)?\\d+$")) {
				result = callServicesForDecimal(colValue);
			}
		}
		return result;
	}

	private String callServicesForDate(String colValue) {

		return waysToAffectValidityService.generateInvalidDates(colValue);
	}

	private String callServicesForBoolean(String colValue) {

		return waysToAffectValidityService.convertBoolIntoPositiveNegative(colValue);
	}
	
	private String callServicesForDecimal(String colValue) {

		return waysToAffectValidityService.invalidDecimal(colValue);
	}

	private String callServicesForInteger(int parseInt) {
		// TODO Auto-generated method stub
		return waysToAffectValidityService.invalidateInteger(parseInt);
	}

	private String callServicesForChar(String colValue) {

		return waysToAffectValidityService.replaceCharacterWithSpecialChars(colValue);

	}

	private String callServicesForString(String colValue) {
		rand = new Random();
		int num = rand.nextInt(3);
		String result = "";
		switch (num) {

		case 0:
			result = waysToAffectValidityService.generateJunkValues(colValue);
			break;
		case 1:
			result = waysToAffectValidityService.generateStringAndSpecialChars(colValue);
			break;
		case 2:
			result = waysToAffectValidityService.shuffleString(colValue);
			break;
		default:
			result = waysToAffectValidityService.generateStringAndSpecialChars(colValue);
			break;
			

		}
		return result;
	}

}
