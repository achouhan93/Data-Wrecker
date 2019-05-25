package com.data.patternidentification.service.impl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.patternidentification.dao.PatternIdentificationDao;
import com.data.patternidentification.model.PatternIdentificationSubModel;
import com.data.patternidentification.service.PatternIdentificationService;

@Service
@Transactional

public class PatternIdentificationServiceImpl implements PatternIdentificationService {

	private static final Logger LOGGER = LogManager.getLogger();

	@Autowired
	PatternIdentificationDao patternIdentificationDao;

	@Override
	public Map<String, Map<String, Map<String, Integer>>> getPatternidentificationData(String fileName) {
		LOGGER.info("Inside Service");
		try {
			LOGGER.info("Inside DAO");
			int columnIterationCnt = 0;
			int columnCnt = 0;
			int occurancecnt = 1;
			File file = new File("D:\\FL_insurance_sample.csv");
			//File file = new File("D:\\SRH_ACS\\research proj\\dataset\\dataset 1 - building permit data.csv");
			List<String> lines = null;
			List<String> columnHeaders = new ArrayList<String>();
			Map<String, Map<String, Map<String,Integer>>> finalPatterns = new LinkedHashMap<String, Map<String, Map<String,Integer>>>();

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
				PatternIdentificationSubModel patternData = new PatternIdentificationSubModel();
				Map<String,Integer> patternData1 = new HashMap<String,Integer>();
				for (int columnDataIterator = 0; columnDataIterator < columnData.size(); columnDataIterator++) {
					String columnStr = null;
					String integerFilteredStr = null;
					String capAphabetFillteredStr = null;
					String smallAphabetFillteredStr = null;
					if (columnDataIterator == 0) {
						columnHeaders.add(columnData.get(columnDataIterator));
						continue;
					}
					if(columnData.get(0).contains("Date") || columnData.get(0).equalsIgnoreCase("Time"))
					{
						smallAphabetFillteredStr = findPatternForDate(columnData.get(columnDataIterator));
					}
					//patternIdentificationLogic
					if (columnData.get(columnDataIterator) != null && smallAphabetFillteredStr == null) {
						if ((columnData.get(columnDataIterator).length() == 1 && (columnData.get(columnDataIterator).equals("0") || columnData.get(columnDataIterator).equals("1")) ||  ((columnData.get(columnDataIterator).length() == 4 || (columnData.get(columnDataIterator).length() == 5)) && (columnData.get(columnDataIterator).equalsIgnoreCase("True") || columnData.get(columnDataIterator).equalsIgnoreCase("False")) ) )) {
							smallAphabetFillteredStr = columnData.get(columnDataIterator);
						}
						else
						{
							columnStr = columnData.get(columnDataIterator);
							 integerFilteredStr = columnStr.replaceAll("[0-9]", "0");
							 capAphabetFillteredStr = integerFilteredStr.replaceAll("[A-Z]", "X");
							 smallAphabetFillteredStr = capAphabetFillteredStr.replaceAll("[a-z]", "x");
					
						}
					}
					//to increase occurrence if there exist pattern already
							if (!patternData1.containsKey(smallAphabetFillteredStr)) {
								patternData.setColumnPatternIdentified(smallAphabetFillteredStr);
								patternData.setOccurance(occurancecnt);
								patternData1.put(patternData.getColumnPatternIdentified(),patternData.getOccurance());
							}
							else
							{
								int f = patternData1.get(smallAphabetFillteredStr);
								f++;
								patternData.setColumnPatternIdentified(smallAphabetFillteredStr);
								patternData.setOccurance(f);
								patternData1.put(patternData.getColumnPatternIdentified(),patternData.getOccurance());
							}
					
				}
				//sorting the map based on desc occurrence
				Map<String, Integer> patternDataSorted =patternData1.entrySet()
		                .stream()
		                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
		                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
				
				Map<String, Map<String,Integer>> allUniquePatternsData = new HashMap<String, Map<String,Integer>>();
				allUniquePatternsData.put("PatternsIdentifiedFor"+columnHeaders.get(i), patternDataSorted);	
				
				finalPatterns.put(columnHeaders.get(i), allUniquePatternsData);
				i++;
				columnIterationCnt++;
				columnData.clear();
			} while (i < columnCnt);
			return finalPatterns;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		

	}
	
	public String findPatternForDate(String value) {
        Date date = null;
        int dateFormatIterator =0;
    	List<String> dateFormats = Arrays.asList( "dd/MM/yyyy","MM/dd/yyyy", "yyyy/dd/MM","yyyy/MM/dd");// "yyyy-MM-dd", "yyyy-dd-MM", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss:SSS", "yyyy-MM-dd HH:mm:ss.SSS Z", "dd/MM/yyyy");
        try {
        	
        	/*String format1 = "yyyy-MM-dd";
        	String format1 = "";
        	String format1 = "MM/dd/yyyy";
        	String format1 = "dd-MM-yyyy";
        	String format1 = "yyyy-dd-MM";
        	String format1 = "yyyy-MM-dd HH:mm:ss";
        	String format1 = "yyyy-MM-dd HH:mm:ss:SSS";
        	String format1 = "yyyy-MM-dd HH:mm:ss.SSS Z";*/
        	while (date == null && dateFormatIterator < dateFormats.size())
        	{
        		try
        		{
        		SimpleDateFormat sdf = new SimpleDateFormat(dateFormats.get(dateFormatIterator));
        		date = sdf.parse(value);
        		if (!value.equals(sdf.format(date))) {
                    date = null;
                    dateFormatIterator++;
                }
        		}
        		catch (ParseException ex)
        		{
        			
        			//ex.printStackTrace();
        		}
        	}
        	return dateFormats.get(dateFormatIterator);
            
        } catch (Exception ex) {
           //ex.printStackTrace();
        }
        return null;
    }

}
