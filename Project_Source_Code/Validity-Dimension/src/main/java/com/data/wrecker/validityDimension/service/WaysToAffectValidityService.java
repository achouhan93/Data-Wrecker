package com.data.wrecker.validityDimension.service;

import java.util.ArrayList;

import com.data.wrecker.validityDimension.model.DatasetStats;

public interface WaysToAffectValidityService {

	public String generateJunkValues(String colValue);
	
	public String shuffleString(String colValue);
	
	public String generateStringAndSpecialChars(String colValue);

	public int convertIntToOppositeSign(int colValue);
	
	public String convertBoolIntoPositiveNegative(String colValue);
	
	public String replaceCharacterWithSpecialChars(String colValue);

	public String invalidateInteger(int colValue,ArrayList<String> columnData);
	
	public String addYearsToDate(DatasetStats datasetStats, String date);
	
	public String generateInvalidDates(String colValue);

	public String invalidDecimal(String colValue,ArrayList<String> columnData);
}
