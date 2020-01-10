package com.data.wrecker.consistencydimension.serviceImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.// LOGGER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.wrecker.consistencydimension.model.DataProfilerInfo;
import com.data.wrecker.consistencydimension.model.DatasetStats;
import com.data.wrecker.consistencydimension.model.PatternModel;
import com.data.wrecker.consistencydimension.repository.DataProfilerInfoRepo;
import com.data.wrecker.consistencydimension.service.WaysofConsistencyToBeApplied;


@Service
@Transactional
public class ConsistencyToBeAppliedImpl implements WaysofConsistencyToBeApplied{

	@Autowired
	private DataProfilerInfoRepo dataProfilerInfoRepo;
	
	private DataProfilerInfo dataProfilerInfo;
	private List<DataProfilerInfo> dataProfilerInfoList;
	private Random rand = new Random();
	// private static final // LOGGER // LOGGER = LogManager.get// LOGGER();
	
	@Override
	public String interchangeColumnValues(String colValue, DataProfilerInfo datasetProfiler,String columnName,String fileName) {
		dataProfilerInfoList = dataProfilerInfoRepo.findAll();
		rand = new Random();
		for(int i =0; i < dataProfilerInfoList.size(); i++) {
			if(dataProfilerInfoList.get(i).getFileName().equals(fileName)) {
				dataProfilerInfo = new DataProfilerInfo();
				dataProfilerInfo = dataProfilerInfoList.get(i);
				break;				
			}
		}
		
		
		
		int randomIndex = rand.nextInt(dataProfilerInfo.getDatasetStats().size());
		while(dataProfilerInfo.getDatasetStats().get(randomIndex).getColumnName().equals(columnName)) {
			randomIndex = rand.nextInt(dataProfilerInfo.getDatasetStats().size());
		}
		int randomForDistinctVals = rand.nextInt(dataProfilerInfo.getDatasetStats().get(randomIndex).getProfilingInfo().getColumnStats().getDistinctValueList().size());
		colValue = dataProfilerInfo.getDatasetStats().get(randomIndex).getProfilingInfo().getColumnStats().getDistinctValueList().get(randomForDistinctVals);
		return colValue;
	}


	@Override
	public String changeItIntoLowerCase(String colValue) {
		// TODO Auto-generated method stub
		return colValue.toLowerCase();
	}

	@Override
	public String changeItIntoUpperLower(String colValue) {
		char[] characters = colValue.toCharArray();
		rand = new Random();
		int index;
		int repeatingCount = rand.nextInt(colValue.length()) + 1;
		for(int i =0; i < repeatingCount;i++) {
			index = rand.nextInt(colValue.length());
			Character.toUpperCase(characters[index]);
		}
		for(int j = 0; j< repeatingCount; j++) {
			index = rand.nextInt(colValue.length());
			Character.toLowerCase(characters[index]);
		}
		
		return String.copyValueOf(characters);
	}

	@Override
	public String changeDateFormat(String colValue, DatasetStats datasetStats) {
		String newDate = "";
		List<PatternModel> patternsList  = datasetStats.getProfilingInfo().getPatternsIdentified();
		PatternModel patternModel = getMostOccuredPattern(patternsList);
		
		String dateFormat = patternModel.getPattern();
		
		String[] dateFormats = {"dd-MM-yy","dd-MM-yyyy","MM-dd-yyyy","yyyy-MM-dd","EEEEE MMMMM yyyy HH:mm:ss.SSSZ","yyyy-MM-dd HH:mm:ss"};
		int ind =0;
		rand = new Random();
		 try {
			 DateFormat srcDf = new SimpleDateFormat(dateFormat);
			 
			 Date date = srcDf.parse(colValue);
			 
			 ind = rand.nextInt(dateFormats.length);
			 
		 	 DateFormat destDf = new SimpleDateFormat(dateFormats[ind]);
			 
			 newDate = destDf.format(date);
			 
			 while(newDate.equals(colValue)) {
			
				 ind = rand.nextInt(dateFormats.length);
				 
				 destDf = new SimpleDateFormat(dateFormats[ind]);
				 
				 newDate = destDf.format(date);
				 
			 }
			 
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 // LOGGER.info("New Date "+newDate);
		return newDate;
	}

	private PatternModel getMostOccuredPattern(List<PatternModel> patternsList) {
		
		List<Integer> occuranceCounts = new ArrayList<Integer>();
		PatternModel patternModel = new PatternModel();
		
		for(int occ = 0; occ<patternsList.size(); occ++) {
			
			occuranceCounts.add(patternsList.get(occ).getOccurance());
		}
		int maxCount = Collections.max(occuranceCounts);
		
		for(int maxVal = 0; maxVal < patternsList.size(); maxVal++) {
		
			if(patternsList.get(maxVal).getOccurance() == maxCount) {
				patternModel= patternsList.get(maxVal);
				break;
			}
			
		}
		return patternModel;
	}
	
	@Override
	public String affectBooleanValues(boolean colValue) {
		String boolValue;
		int index =0;
		String[] truthValues = {"tRuE","TRue","TruE","Treu","Ture","t","T","1","Trre"};
		String[] falseValues = {"faLsE","FaLSE","FaLSe","FAls","f","0","FASE","Fals","FLSE","Fale"};
		rand = new Random();
		if(colValue) {
		boolValue = String.valueOf(colValue);
		index = rand.nextInt(truthValues.length);
		
		boolValue = truthValues[index];		
		}else {
			boolValue = String.valueOf(colValue);
			index = rand.nextInt(falseValues.length);
			boolValue = falseValues[index];
		}
		// LOGGER.info("Value "+boolValue);
		return boolValue;
	}
	
	@Override
	public String affectCurrencyValues(String colValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String convertIntegerIntoBinary(int colValue) {		
		return Integer.toBinaryString(colValue);
	}

	@Override
	public String consistencyForCharacters(String colValue) {
		/*dataProfilerInfoList = dataProfilerInfoRepo.findAll();
		List<String> distinctValueList = null;
		rand = new Random();
		for(int i =0; i < dataProfilerInfoList.size(); i++) {
			if(dataProfilerInfoList.get(i).getFileName().equals(collectionName)) {
				dataProfilerInfo = new DataProfilerInfo();
				dataProfilerInfo = dataProfilerInfoList.get(i);
				break;				
			}
		}
		
		for(int j=0; j< dataProfilerInfo.getDatasetStats().size(); j++) {
			if(dataProfilerInfo.getDatasetStats().get(j).getColumnName().equals("")) {
				distinctValueList = dataProfilerInfo.getDatasetStats().get(j).getProfilingInfo().getColumnStats().getDistinctValueList();
			}
		}
		*/
		 char c = (char)(rand.nextInt(26) + 'a');
		 char c2 = (char)(rand.nextInt(26) + 'a');
		return Character.toString(c) + Character.toString(c2) ;
	}
	
	
	
	private boolean isPresentInList(List<String> distinctValueList, String s) {
		boolean status = false;
		for(int i=0; i< distinctValueList.size();i++) {
			if(distinctValueList.get(i).equals(s)) {
				status =  true;
			}else {
				status =  false;
			}
		}
		return status;
	}

	@Override
	public String reverseCase(String colValue) {
		return reverseStringCase(colValue);
	}


	@Override
	public String changeItIntoUpperCase(String colValue) {
		// TODO Auto-generated method stub
		return colValue.toUpperCase();
	}
	
	private String reverseStringCase(String stringValue) {
		char[] chars = stringValue.toCharArray();
		int count = rand.nextInt(stringValue.length());
	    while(count < chars.length)
	    {
	    	int index = rand.nextInt(chars.length);
	        char c = chars[index];
	        if (Character.isUpperCase(c))
	        {
	            chars[index] = Character.toLowerCase(c);
	        }
	        else if (Character.isLowerCase(c))
	        {
	            chars[index] = Character.toUpperCase(c);
	        }
	        count++;
	    }
		return new String(chars);
	}


	@Override
	public String convertToFloat(int colValue) {
	
		float val = (float)(colValue);
		
		return String.valueOf(val);
		
	}
}
