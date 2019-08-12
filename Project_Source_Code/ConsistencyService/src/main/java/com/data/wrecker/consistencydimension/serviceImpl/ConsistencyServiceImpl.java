package com.data.wrecker.consistencydimension.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.wrecker.consistencydimension.model.DataProfilerInfo;
import com.data.wrecker.consistencydimension.repository.DataProfilerInfoRepo;
import com.data.wrecker.consistencydimension.service.ConsistencyService;
import com.data.wrecker.consistencydimension.service.WaysofConsistencyToBeApplied;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

@Service
@Transactional
public class ConsistencyServiceImpl implements ConsistencyService{

	private static final Logger LOGGER = LogManager.getLogger();
	@Autowired
	private DataProfilerInfoRepo dataProfilerInfoRepo;
	private DataProfilerInfo dataProfilerInfo;
	@Autowired
	private WaysofConsistencyToBeApplied waysofConsistencyToBeApplied;
	private List<DataProfilerInfo> dataProfilerInfoList;
	private Random rand;
	
	@Override
	public String removeConsistencyDimension(String collectionName, String columnName) {
		JSONArray datasetArray = getDatasetFromDb(collectionName);
		String columnDataType  = getColumnDataType(collectionName,columnName);
		List<Integer> randomValues = new ArrayList<Integer>();
		Random randomGenerator = new Random();
		for(int i=0;i<5;i++) {
			int randomNumber = randomGenerator.nextInt(50) ;
			randomValues.add(randomNumber);
			LOGGER.info("Random Number  " +randomNumber);
		}
		
		for(int j =0; j < randomValues.size(); j++ ) {
			try {
				String colValue = datasetArray.getJSONObject(randomValues.get(j)).get(columnName).toString();
				LOGGER.info("Record " + datasetArray.getJSONObject(randomValues.get(j)).toString());
				LOGGER.info("Col Value "+ colValue);
				colValue = removeConsistency(colValue, columnDataType);
				LOGGER.info("Col Value after wrecking "+ colValue);
				datasetArray.getJSONObject(randomValues.get(j)).put(columnName,colValue);
				datasetArray.getJSONObject(randomValues.get(j)).put("isWrecked", true);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return null;
	}
	
	private String removeConsistency(String colValue, String columnDatatype) {
		String result = "";
		switch(columnDatatype.toLowerCase()) {
		case "string": 
			LOGGER.info("String");
			result = callServicesForString(colValue);
			break;
		case "integer":	
			LOGGER.info("Integer");			
			result = callServicesForInteger(Integer.parseInt(colValue));
			break;
		case "character":	
			LOGGER.info("Character");
			result = callServicesForChar(colValue);
			break;
		case "date":
			LOGGER.info("Date");
			result = callServicesForDate();
			break;
		case "boolean":
			LOGGER.info("Boolean");
			result = callServicesForBoolean();
		}
		return result;
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
	
	private String getColumnDataType(String collectionName,String columnName) {
		dataProfilerInfoList = dataProfilerInfoRepo.findAll();
		String colDatatype = "";
		for(int i =0; i < dataProfilerInfoList.size(); i++) {
			if(dataProfilerInfoList.get(i).getFileName().equals(collectionName)) {
				dataProfilerInfo = new DataProfilerInfo();
				dataProfilerInfo = dataProfilerInfoList.get(i);
				break;				
			}
		}	
		for(int i=0; i<dataProfilerInfo.getDatasetStats().size(); i++) {
			
			if(dataProfilerInfo.getDatasetStats().get(i).getColumnName().equals(columnName)) {
				colDatatype = dataProfilerInfo.getDatasetStats().get(i).getProfilingInfo().getColumnDataType();
				break;
			}
		}
		return colDatatype;		
	}
	
	
	private String callServicesForString(String colValue) {
		int randomNum = getRandomNumber(2);
		LOGGER.info("Random number selected "+ randomNum);
		String cha = "";
		switch(randomNum) { 
		case 0:
			if(isStringUpper(colValue)) {
				cha = waysofConsistencyToBeApplied.changeItIntoLowerCase(colValue);
			}else {
				cha = waysofConsistencyToBeApplied.reverseCase(colValue);
			}
			LOGGER.info("Value after Change "+cha);
			break;
		case 1:
			cha = waysofConsistencyToBeApplied.reverseCase(colValue);
			LOGGER.info("Value after Change "+cha);
			break;
		}
		return cha;
	}
	
	private String callServicesForChar(String colValue) {
		int randomNum = getRandomNumber(2);
		String cha = "";
		switch(randomNum) {
		case 0:
			if(isStringUpper(colValue)) {
				cha = waysofConsistencyToBeApplied.changeItIntoLowerCase(colValue);
			}else {
				cha = waysofConsistencyToBeApplied.reverseCase(colValue);
			}
			LOGGER.info("Value after Change "+cha);
			break;
		case 1:
			cha = waysofConsistencyToBeApplied.consistencyForCharacters(colValue);
			break;
		}
		return cha;
	}
	
	private String callServicesForInteger(int colValue) {
		getRandomNumber(2);
		String num = "";
		
		switch(2) {
		case 0: 
			num = waysofConsistencyToBeApplied.convertIntegerIntoBinary(colValue);
			break;
		case 1: 
			num = Integer.toString(waysofConsistencyToBeApplied.affectNumbers(colValue));		
			
		}
		return num;
	}
	
	private String callServicesForBoolean() {
		return null;
	}
	
	private String callServicesForDate() {
		return null;
	}
	
	private int getRandomNumber(int num) {
		rand = new Random();
		return rand.nextInt(num);		
	}
	
	
	private boolean isStringUpper(String value) {
		boolean stat = false;
		char[] charArray = value.toCharArray();
        
        for(int i=0; i < charArray.length; i++){
            
            //if the character is a letter
            if( Character.isLetter(charArray[i]) ){
                
                //if any character is not in upper case, return false
                if( !Character.isUpperCase( charArray[i] ))
                    stat = false;
                else
                	stat = true;
            }
        }
        
        return stat;
	}
	
}
