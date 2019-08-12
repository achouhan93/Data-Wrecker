package com.data.wrecker.accuracyDimension.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.wrecker.accuracyDimension.service.TypesOfAccuracyToBeEffected;


@Service
@Transactional
public class TypesOfAccuracyServiceImpl implements TypesOfAccuracyToBeEffected{

	private static final Logger LOGGER = LogManager.getLogger();
	private Random rand = new Random(); 
	
	@Override
	public JSONObject interChangedValues(JSONObject jsonObj, String columnName) {
		LOGGER.info("Interchanging values "+jsonObj.toString());
		
		
		Iterator columnNames = jsonObj.keys();
		List<String> columnHeaders =new ArrayList<String>();
		
		while(columnNames.hasNext()) {
			String key = (String) columnNames.next();
			columnHeaders.add(key);
		}
		for(int i =0; i< columnHeaders.size(); i++) {
			if(columnHeaders.get(i).isEmpty() || columnHeaders.get(i).equals("_id") || columnHeaders.get(i).equals(columnName) ) {
				columnHeaders.remove(i);
			}
		}
		
		rand = new Random();
		int randomArrayIndex = rand.nextInt(columnHeaders.size());
		String randomColName = columnHeaders.get(randomArrayIndex);		
		String randomColValue;
		
		try {
			String colValue = jsonObj.getString(columnName);
			randomColValue = jsonObj.getString(randomColName);			
			jsonObj.put(columnName, randomColValue);
			jsonObj.put(randomColName, colValue);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		LOGGER.info("Interchanged0 values "+jsonObj.toString());
		
		return jsonObj;
	}

	@Override
	public String typosForValues(String colValue) {
		LOGGER.info("Typos");
		rand = new Random();
		int count = rand.nextInt(4);
		
		char[] chars = colValue.toCharArray();
		while(count < 0) {			
			chars[count] = (char) (rand.nextInt(26) + 'a');
		}
		return new String(chars);
	}

	@Override
	public String generateJunkValues(String colValue) {
		LOGGER.info("Generate Junk Values ");
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+{}:>?/.,;'[]";
		char[] characters = colValue.toCharArray();
		char[] newString = new char[characters.length];
		for(int i=0; i< colValue.length();i++) {
			rand = new Random();
			int index = rand.nextInt(characters.length);
			newString[i] = characters[index];			
		}
		return String.copyValueOf(newString);
	}

	@Override
	public String generateDates(String colValue) {
		LOGGER.info("Generating Dates");
		return null;
	}

	@Override
	public String shuffleString(String colValue) {
		LOGGER.info("Shuffle Strings");
		List<Character> characters = new ArrayList<Character>();
        for(char c:colValue.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(colValue.length());
        while(characters.size()!=0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
		return output.toString();
	}

}
