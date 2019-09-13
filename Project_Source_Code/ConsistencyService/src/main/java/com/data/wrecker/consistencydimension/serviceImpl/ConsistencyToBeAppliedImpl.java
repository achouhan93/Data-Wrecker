package com.data.wrecker.consistencydimension.serviceImpl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.wrecker.consistencydimension.model.DataProfilerInfo;
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
	public String changeDateFormat(String colValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String affectBooleanValues(boolean colValue) {
		String boolValue = String.valueOf(colValue);
		switch(boolValue) {
		
		case "True":
			boolValue = "TREU";
		
		}
		return null;
	}

	@Override
	public int affectNumbers(int colValue) {
		// TODO Auto-generated method stub
		rand = new Random();
		colValue = colValue + rand.nextInt(colValue) + 1;
		
		return colValue;
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
		char[] chars = colValue.toCharArray();
		int count = rand.nextInt(colValue.length());
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
	public String changeItIntoUpperCase(String colValue) {
		// TODO Auto-generated method stub
		return colValue.toUpperCase();
	}
}
