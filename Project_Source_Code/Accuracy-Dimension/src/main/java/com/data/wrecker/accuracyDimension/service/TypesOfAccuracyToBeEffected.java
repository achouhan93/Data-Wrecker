package com.data.wrecker.accuracyDimension.service;

import org.json.JSONObject;

import com.data.wrecker.accuracyDimension.model.DatasetStats;

public interface TypesOfAccuracyToBeEffected {

	public JSONObject interChangedValues(JSONObject jsonObj, String columnName );
	
	public String typosForValues(String colValue);
	
	public String generateJunkValues(String colValue);
	
	public String generateDates(String colValue);
	
	public String shuffleString(String colValue);
	
	public int convertIntToOppositeSign(int colValue);
	
	public String addYearsToDate(DatasetStats datasetStats, String date);
	
	
}
