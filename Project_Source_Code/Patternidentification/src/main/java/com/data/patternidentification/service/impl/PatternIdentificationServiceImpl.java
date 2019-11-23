package com.data.patternidentification.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.patternidentification.dao.PatternIdentificationDao;
import com.data.patternidentification.exception.PatternIdentificationException;
import com.data.patternidentification.model.DataProfilerInfo;
import com.data.patternidentification.model.DatasetStats;
import com.data.patternidentification.model.PatternModel;
import com.data.patternidentification.model.ProfilingInfoModel;
import com.data.patternidentification.repositories.ColumnPatternRepository;
import com.data.patternidentification.service.PatternIdentificationService;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

@Service
@Transactional

public class PatternIdentificationServiceImpl implements PatternIdentificationService {

	private static final Logger LOGGER = LogManager.getLogger();

	@Autowired
	PatternIdentificationDao patternIdentificationDao;

	@Autowired
	private ColumnPatternRepository columnPatternRepository;

	@Override
	public DataProfilerInfo getPatternidentificationData(String collectionName) throws PatternIdentificationException {
		LOGGER.info("Inside Service");
		// mocking the object of columnheaderdata :::: expecting this array to be passed
		// by orchestrator
		/*
		 * columnHeaders = new ArrayList<String>(); columnHeaders.add("Date");
		 * columnHeaders.add("statecode"); columnHeaders.add("county");
		 * columnHeaders.add("eq_site_limit");
		 */

		List<String> columnHeaders = getColumnNames("ReverseEngineering", collectionName);

		DatasetStats columnPatternDetails = null;
		ProfilingInfoModel propertyData = null;
		DataProfilerInfo dataProfilerInfo = null;
		List<DatasetStats> columnPatternDetailsList = new ArrayList<DatasetStats>();
		try {
			/*
			 * File file = new File("D:\\FL_insurance_sample.csv"); // File file = new
			 * File("D:\\SRH_ACS\\research proj\\dataset\\dataset 1 - List<String> lines =
			 * null; lines = Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
			 * int i = 0; do { List<String> columnData = new ArrayList<>(); for (String line
			 * : lines) { if (columnIterationCnt < lines.size()) { String[] array =
			 * line.split(","); columnCnt = array.length;
			 * columnData.add(array[columnIterationCnt]); } } LOGGER.info(columnData);
			 */
			// read dataset from mongo
			// DB db = mongo.getDB("yourdb");

			// JSONObject completedataset = columnPatternRepository.findAll();
			for (int z = 0; z < columnHeaders.size(); z++) {
				MongoClient mongoClient = new MongoClient();
				MongoDatabase database = mongoClient.getDatabase("ReverseEngineering");
				MongoCollection<Document> collection = database.getCollection(collectionName);

				List<String> columnData = new ArrayList<>();
				try (MongoCursor<Document> cur = collection.find().iterator()) {
					while (cur.hasNext()) {
						columnData.add((String) cur.next().get(columnHeaders.get(z)).toString().trim());

					}
				}
				mongoClient.close();
				// System.out.println(" " + columnData);

				List<PatternModel> patternDataList = new ArrayList<PatternModel>();
				propertyData = new ProfilingInfoModel();
				for (int columnDataIterator = 0; columnDataIterator < columnData.size(); columnDataIterator++) {
					String columnStr = null;
					String integerFilteredStr = null;
					String capAphabetFillteredStr = null;
					String smallAphabetFillteredStr = null;
					Boolean matchFound = false;
					columnPatternDetails = new DatasetStats();
					/*
					 * if (columnDataIterator == 0) { continue; }
					 */
					if (columnHeaders.get(z).contains("Date") || columnHeaders.get(z).contains("Time")) {
						smallAphabetFillteredStr = findPatternForDate(columnData.get(columnDataIterator));
					}
					// patternIdentificationLogic
					if (columnData.get(columnDataIterator).trim() != null && smallAphabetFillteredStr == null) {
						/*
						 * if ( ( columnData.get(columnDataIterator).length() == 1 &&
						 * (columnData.get(columnDataIterator).equals("0") ||
						 * columnData.get(columnDataIterator).equals("1")) ||
						 * ((columnData.get(columnDataIterator).length() == 4 ||
						 * (columnData.get(columnDataIterator).length() == 5)) &&
						 * (columnData.get(columnDataIterator).equalsIgnoreCase("True") ||
						 * columnData.get(columnDataIterator).equalsIgnoreCase("False"))) ) )
						 */
						if ((columnData.get(columnDataIterator).length() == 1
								&& (columnData.get(columnDataIterator).equals("0")
										|| columnData.get(columnDataIterator).equals("1")
										|| columnData.get(columnDataIterator).equalsIgnoreCase("T")
										|| columnData.get(columnDataIterator).equalsIgnoreCase("F")))
								|| ((columnData.get(columnDataIterator).length() == 4
										&& columnData.get(columnDataIterator).equalsIgnoreCase("True"))
										|| (columnData.get(columnDataIterator).length() == 5
												&& columnData.get(columnDataIterator).equalsIgnoreCase("False")))) {
							smallAphabetFillteredStr = columnData.get(columnDataIterator);
						} else {
							columnStr = columnData.get(columnDataIterator);
							integerFilteredStr = columnStr.replaceAll("[0-9]", "9");
							capAphabetFillteredStr = integerFilteredStr.replaceAll("[A-Z]", "X");
							smallAphabetFillteredStr = capAphabetFillteredStr.replaceAll("[a-z]", "x");

						}
					}
					PatternModel patternData = new PatternModel();
					// to increase occurrence if there exist pattern already
					int occurancecnt = 1;
					if (columnDataIterator == 0) {
						patternData.setPattern(smallAphabetFillteredStr);
						patternData.setOccurance(occurancecnt);
						patternDataList.add(patternData);
						propertyData.setPatternsIdentified(patternDataList);

					} else {
						// Existing pattern found
						for (int j = 0; j < propertyData.getPatternsIdentified().size(); j++) {
							if (propertyData.getPatternsIdentified().get(j).getPattern()
									.equals(smallAphabetFillteredStr)) {
								occurancecnt = propertyData.getPatternsIdentified().get(j).getOccurance() + 1;
								propertyData.getPatternsIdentified().get(j).setOccurance(occurancecnt);
								matchFound = true;
							}
						}
						// new pattern entry
						if (matchFound == false) {
							occurancecnt = 1;
							patternData.setPattern(smallAphabetFillteredStr);
							patternData.setOccurance(occurancecnt);
							patternDataList.add(patternData);
							propertyData.setPatternsIdentified(patternDataList);
						}
					}
				}

				columnPatternDetails.setColumnName(columnHeaders.get(z));
				columnPatternDetails.setProfilingInfo(propertyData);
				columnPatternDetailsList.add(columnPatternDetails);

				columnData.clear();

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PatternIdentificationException(
					com.data.patternidentification.exception.ErrorCodes.SOMETHING_WENT_WRONG);
		}
		dataProfilerInfo = new DataProfilerInfo();
		dataProfilerInfo.setFileName(collectionName);
		dataProfilerInfo.setDatasetStats(columnPatternDetailsList);
		columnPatternRepository.save(dataProfilerInfo);
		return dataProfilerInfo;

	}

	public String findPatternForDate(String value) throws PatternIdentificationException {
		// Date date = null;
		String finalDatePattern = null;
		String[] datepieces = null;
		String[] dataPattern = new String[2];
		String dateseparator = null;
		int dateFormatIterator = 0;
		if (!value.isEmpty() &&  null != value ) {
			if (value.contains("-")) {
				datepieces = value.split("-");
				dateseparator = "-";
			} else if (value.contains(".")) {
				datepieces = value.split("\\.");
				dateseparator = ".";
			} else {
				datepieces = value.split("/");
				dateseparator = "/";
			}
			System.out.println("date: " + datepieces[0] + "   " + datepieces[1] + "  " + datepieces[2] + "  ");
			int date1 = Integer.parseInt(datepieces[0]);
			int date2 = Integer.parseInt(datepieces[1]);
			int date3 = Integer.parseInt(datepieces[2]);
			//as year cannot be in middle
			dataPattern[0]=datepieces[0];
			dataPattern[1]=datepieces[2];
			
			int i;
			for (i = 0; i < 2; i++) {
				switch (dataPattern[i].length()) {
				case 4:
					dataPattern[i] = "yyyy";
					break;
				case 2:
				
					if (date1 < 12 && date2 < 12) {
						dataPattern[i] = "dd"+dateseparator+"MM";
						
					} else if (date1 > 12 || date2 < 12) {
						dataPattern[i] = "dd"+dateseparator+"MM";
						
					}
					else
					{
						dataPattern[i] = "MM-dd";
					}
					
					break;
				}
			}
			
		 finalDatePattern = dataPattern[0] +dateseparator+ dataPattern[1];
		 System.out.println("finalDatePattern:"+finalDatePattern);
			/*
			 * List<String> dateFormats =
			 * Arrays.asList("dd-MM-yyyy","dd/MM/yyyy","dd.MM.yyyy"); // "yyyy-MM-dd", //
			 * "yyyy-dd-MM", // "yyyy-MM-dd // HH:mm:ss", // "yyyy-MM-dd // HH:mm:ss:SSS",
			 * // "yyyy-MM-dd // HH:mm:ss.SSS // Z", // "dd/MM/yyyy");
			 */ // try {

			/*
			 * while (date == null && dateFormatIterator < dateFormats.size()) { try {
			 * SimpleDateFormat sdf = new
			 * SimpleDateFormat(dateFormats.get(dateFormatIterator)); date =
			 * sdf.parse(value); if (!value.equals(sdf.format(date))) { date = null;
			 * dateFormatIterator++; } } catch (ParseException ex) { return null; } }
			 */
			/*
			 * } catch (Exception ex) { ex.printStackTrace(); throw new
			 * PatternIdentificationException(
			 * com.data.patternidentification.exception.ErrorCodes.SOMETHING_WENT_WRONG);
			 * 
			 * }
			 */
			// return dateFormats.get(dateFormatIterator);
			
		}
		return finalDatePattern;
	}

	@SuppressWarnings("deprecation")
	public List<String> getColumnNames(String databaseName, String collectionName) {
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB(databaseName);
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
		JSONObject json_obj;
		try {
			json_obj = dbList.getJSONObject(0);
			Iterator columnNames = json_obj.keys();

			while (columnNames.hasNext()) {
				String key = (String) columnNames.next();
				columnNamesList.add(key);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < columnNamesList.size(); i++) {
			if (columnNamesList.get(i).isEmpty() || columnNamesList.get(i).equals("_id")) {
				columnNamesList.remove(i);
			}
		}
		/*
		 * for(int j = 0; j< dbList.length(); j++ ) { try {
		 * 
		 * String jsonString = dbList.getJSONObject(j).put("columnNames",
		 * columnNamesList).toString(); DBObject dbObj = (DBObject)
		 * JSON.parse(jsonString); collection.save(dbObj); } catch (JSONException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); } }
		 */
		return columnNamesList;

	}

}
