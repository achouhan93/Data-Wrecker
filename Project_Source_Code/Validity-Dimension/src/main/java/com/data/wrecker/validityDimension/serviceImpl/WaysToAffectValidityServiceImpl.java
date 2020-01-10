package com.data.wrecker.validityDimension.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.// LOGGER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.wrecker.validityDimension.model.DataProfilerInfo;
import com.data.wrecker.validityDimension.model.DatasetStats;
import com.data.wrecker.validityDimension.model.PatternModel;
import com.data.wrecker.validityDimension.repository.DataProfilerInfoRepo;
import com.data.wrecker.validityDimension.service.WaysToAffectValidityService;

@Service
@Transactional
public class WaysToAffectValidityServiceImpl  implements WaysToAffectValidityService{

	@Autowired
	private DataProfilerInfoRepo dataProfilerInfoRepo;
	//private static final // LOGGER // LOGGER = LogManager.get// LOGGER();
	private Random rand = new Random(); 
	private List<DataProfilerInfo> dataProfilerInfoList;
	private List<DatasetStats> datasetStatsList;
	private DatasetStats datasetStats;
	private DataProfilerInfo dataProfilerInfo;
	
	
	@Override
	public String generateJunkValues(String colValue) {
		// LOGGER.info("Generate Junk Values ");
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
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
	public String shuffleString(String colValue) {
		// LOGGER.info("Shuffle Strings");
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

	@Override
	public String generateStringAndSpecialChars(String colValue) {
		// LOGGER.info("Generate Junk Values ");
		String str = "!@#$%^&*()_+{}:>?/.,;'[]";
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
	public int convertIntToOppositeSign(int colValue) {
		
		return -colValue;
	}

	@Override
	public String convertBoolIntoPositiveNegative(String colValue) {
		rand = new Random();
		String value = "";
		
		switch(colValue.toLowerCase()) {
		
		case "true":
			if(rand.nextInt(2)  > 0) {
				value = "+";
			}else {
				value = "+Ve";
			}
			break;
		case "false":
			if(rand.nextInt(2)  > 0) {
				value = "-";
			}else {
				value = "-Ve";
			}
			break;
		case "1":
			if(rand.nextInt(2)  > 0) {
				value = "+1";
			}else {
				value = "-1";
			}
			break;
		case "0":
			if(rand.nextInt(2)  > 0) {
				value = "+0";
			}else {
				value = "-0";
			}
			break;
		default:
			value = "Tr";
			
		}
		return value;
	}

	

	@Override
	public String invalidateInteger(int colValue,ArrayList<String> columnData) {
	 int length = String.valueOf(colValue).length();
	 
	 // LOGGER.info("Generate Junk Values ");
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		char[] characters = str.toCharArray();
		char[] newString = new char[characters.length];
		for(int i=0; i< length;i++) {
			rand = new Random();
			int index = rand.nextInt(characters.length);
			newString[i] = characters[index];			
		}
		return String.copyValueOf(newString); 
	 
	}
	
	@Override
	public String invalidDecimal(String colValue,ArrayList<String> columnData) {
	 int length = String.valueOf(colValue).length();
	 
	 // LOGGER.info("Generate Junk Values ");
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()_+{}|,<>?[]./";
		char[] characters = str.toCharArray();
		char[] newString = new char[characters.length];
		for(int i=0; i< length;i++) {
			rand = new Random();
			int index = rand.nextInt(characters.length);
			newString[i] = characters[index];			
		}
		return String.copyValueOf(newString); 
	 
	}
	
	@Override
	public String addYearsToDate(DatasetStats datasetStats, String date){
		String dateFormatPattern = ""; 
		rand = new Random();
		int yearsTobeAdded = rand.nextInt(1000) + 100;
		
		List<PatternModel> patternsIdentified  = datasetStats.getProfilingInfo().getPatternsIdentified();
		for(int i = 0; i < patternsIdentified.size();i++) {
			if(Pattern.matches(date, patternsIdentified.get(i).getPattern())) {
				dateFormatPattern = patternsIdentified.get(i).getPattern(); 
				SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern);
				Calendar c = Calendar.getInstance();
				try {
					c.setTime(sdf.parse(date));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.add(Calendar.YEAR, yearsTobeAdded);
				date = sdf.format(c.getTime());
			}
		}
		return date;  
	}
	
private DatasetStats getDatasetStats(String colName, String cllectionName) {
		
		dataProfilerInfoList = new ArrayList<DataProfilerInfo>();
		
		dataProfilerInfoList = dataProfilerInfoRepo.findAll();
		for(int i =0; i < dataProfilerInfoList.size(); i++) {
			if(dataProfilerInfoList.get(i).getFileName().equals(cllectionName)) {
				dataProfilerInfo = new DataProfilerInfo();
				datasetStatsList = dataProfilerInfoList.get(i).getDatasetStats();
				for(int j =0; j < datasetStatsList.size(); j++) {
					if(datasetStatsList.get(j).getColumnName().equals(colName)) {
						datasetStats = new DatasetStats();
						datasetStats = datasetStatsList.get(j);
						break;
					}
				}	
			}
		}
		
		return datasetStats;
	}

@Override
public String replaceCharacterWithSpecialChars(String colValue) {
	String chars = "!@#$%^&*()_+-=,./;'[] {}:?><";
	char[] charArray = chars.toCharArray();
	rand = new Random();
	int randNum = rand.nextInt(charArray.length);	
	return Character.toString(charArray[randNum]);
	
}

@Override
public String generateInvalidDates(String colValue) {
	rand = new Random();
	int date = rand.nextInt(31) + 32;
	int month = rand.nextInt(12) + 12;
	int year = rand.nextInt(3000) + 2000;
	String [] months = {"JANE","FEBU","MARK","APLR","MAI","JAME","JLIY","AGUS","SPTB","OCBMR","NVMBR","DCBRF"};
	if(colValue.contains("/")) {
		colValue = date + "/" +month +"/" + year;
	}else if(colValue.contains("-")) {
		colValue = date + "-" +month +"-" + year;
	}else {
		colValue = date + " " +months[rand.nextInt(months.length)] +" " + year;
	}
	return colValue;
}




}
