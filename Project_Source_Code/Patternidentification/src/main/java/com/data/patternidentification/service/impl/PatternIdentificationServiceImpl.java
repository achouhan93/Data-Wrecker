
package com.data.patternidentification.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.patternidentification.dao.PatternIdentificationDao;
import com.data.patternidentification.exception.PatternIdentificationException;
import com.data.patternidentification.model.ColumnPatternModel;
import com.data.patternidentification.model.PatternIdentificationModel;
import com.data.patternidentification.model.PatternModel;
import com.data.patternidentification.model.PropertyModel;
import com.data.patternidentification.repositories.ColumnPatternRepository;
import com.data.patternidentification.service.PatternIdentificationService;
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
	public PatternIdentificationModel getPatternidentificationData(String fileName)
			throws PatternIdentificationException {
		LOGGER.info("Inside Service");
		List<String> columnHeaders = new ArrayList<String>();
		columnHeaders.add("Date");
		columnHeaders.add("statecode");
		columnHeaders.add("county");
		columnHeaders.add("eq_site_limit");
		
		ColumnPatternModel columnPatternDetails = null;
		PropertyModel propertyData = null;
		PatternIdentificationModel patternIdentificationModel = null;
		List<ColumnPatternModel> columnPatternDetailsList = new ArrayList<ColumnPatternModel>();
		try {
						/*File file = new File("D:\\FL_insurance_sample.csv");
			// File file = new File("D:\\SRH_ACS\\research proj\\dataset\\dataset 1 -
			List<String> lines = null;

			lines = Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
			int i = 0;

			do {
				List<String> columnData = new ArrayList<>();
				for (String line : lines) {

					if (columnIterationCnt < lines.size()) {
						String[] array = line.split(",");
						columnCnt = array.length;
						columnData.add(array[columnIterationCnt]);
					}

				}
				LOGGER.info(columnData);
*/				//read dataset from mongo
				//DB db = mongo.getDB("yourdb");
				
				//JSONObject completedataset = columnPatternRepository.findAll();
			for (int z=0; z<columnHeaders.size();z++)
			{
			MongoClient mongoClient = new MongoClient();
			MongoDatabase database = mongoClient.getDatabase("testdataset");
			MongoCollection<Document> collection = database.getCollection("FL_insurance_sample");
			
			List<String> columnData = new ArrayList<>();
			try (MongoCursor<Document> cur = collection.find().iterator()) {
				while (cur.hasNext()) {
					columnData.add((String) cur.next().get(columnHeaders.get(z)).toString());
				
				}
			}
			mongoClient.close();
			System.out.println("   "+columnData);
		
			
				List<PatternModel> patternDataList = new ArrayList<PatternModel>();
				propertyData = new PropertyModel();
				for (int columnDataIterator = 0; columnDataIterator < columnData.size(); columnDataIterator++) {
					String columnStr = null;
					String integerFilteredStr = null;
					String capAphabetFillteredStr = null;
					String smallAphabetFillteredStr = null;
					Boolean matchFound = false;
					columnPatternDetails = new ColumnPatternModel();
					/*if (columnDataIterator == 0) {
						continue;
					}*/
					if (columnData.get(0).equalsIgnoreCase("Date") || columnData.get(0).equalsIgnoreCase("Time")) {
						smallAphabetFillteredStr = findPatternForDate(columnData.get(columnDataIterator));
					}
					// patternIdentificationLogic
					if (columnData.get(columnDataIterator) != null && smallAphabetFillteredStr == null) {
						if ((columnData.get(columnDataIterator).length() == 1
								&& (columnData.get(columnDataIterator).equals("0")
										|| columnData.get(columnDataIterator).equals("1"))
								|| ((columnData.get(columnDataIterator).length() == 4
										|| (columnData.get(columnDataIterator).length() == 5))
										&& (columnData.get(columnDataIterator).equalsIgnoreCase("True")
												|| columnData.get(columnDataIterator).equalsIgnoreCase("False"))))) {
							smallAphabetFillteredStr = columnData.get(columnDataIterator);
						} else {
							columnStr = columnData.get(columnDataIterator);
							integerFilteredStr = columnStr.replaceAll("[0-9]", "0");
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
						propertyData.setPatternModel(patternDataList);

					} else {
						// Existing pattern found
						for (int j = 0; j < propertyData.getPatternModel().size(); j++) {
							if (propertyData.getPatternModel().get(j).getPattern().equals(smallAphabetFillteredStr)) {
								System.out.println("Match found");
								occurancecnt = propertyData.getPatternModel().get(j).getOccurance() + 1;
								propertyData.getPatternModel().get(j).setOccurance(occurancecnt);
								matchFound = true;
							}
						}
						// new pattern entry
						if (matchFound == false) {
							occurancecnt = 1;
							patternData.setPattern(smallAphabetFillteredStr);
							patternData.setOccurance(occurancecnt);
							patternDataList.add(patternData);
							propertyData.setPatternModel(patternDataList);
						}
					}
				}

				columnPatternDetails.setColumnName(columnData.get(0));
				columnPatternDetails.setPropertyModel(propertyData);
				columnPatternDetailsList.add(columnPatternDetails);
				
				columnData.clear();

		
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PatternIdentificationException(
					com.data.patternidentification.exception.ErrorCodes.SOMETHING_WENT_WRONG);
		}
		patternIdentificationModel = new PatternIdentificationModel();
		patternIdentificationModel.setDatasetStats(columnPatternDetailsList);
		columnPatternRepository.save(patternIdentificationModel);
		return patternIdentificationModel;

	}

	public String findPatternForDate(String value) throws PatternIdentificationException {
		Date date = null;
		int dateFormatIterator = 0;
		List<String> dateFormats = Arrays.asList("dd/MM/yyyy", "MM/dd/yyyy", "yyyy/dd/MM", "yyyy/MM/dd");
		// "yyyy-MM-dd",
		// "yyyy-dd-MM",
		// "yyyy-MM-dd
		// HH:mm:ss",
		// "yyyy-MM-dd
		// HH:mm:ss:SSS",
		// "yyyy-MM-dd
		// HH:mm:ss.SSS
		// Z",
		// "dd/MM/yyyy");
		try {

			while (date == null && dateFormatIterator < dateFormats.size()) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(dateFormats.get(dateFormatIterator));
					date = sdf.parse(value);
					if (!value.equals(sdf.format(date))) {
						date = null;
						dateFormatIterator++;
					}
				} catch (ParseException ex) {
					return null;
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new PatternIdentificationException(
					com.data.patternidentification.exception.ErrorCodes.SOMETHING_WENT_WRONG);

		}
		return dateFormats.get(dateFormatIterator);
	}

}
