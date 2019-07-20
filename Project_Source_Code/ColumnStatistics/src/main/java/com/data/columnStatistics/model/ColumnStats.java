package com.data.columnStatistics.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ColumnStats {
	int rowCount;
	int nullCount;
	List<String> distinctValueList;
	int distinctCount;
	Map<String, Long> frequencyOfColumnValuesMap;
	List<String> uniqueValuesList;
	List<String> duplicateValuesList;
	int uniqueCount;
	int duplicateCount;
	boolean isPrimaryKey;
	int maxLength;
	int minLength;
	int averageLength;
	int maxValue;
	int minValue;
	int averageValue;
	double minValueDecimal;
	double maxValueDecimal;
	double averageValueDecimal;
	LocalDate minDate;
	LocalDate maxDate;
	long trueCount;
	long falseCount;
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getNullCount() {
		return nullCount;
	}
	public void setNullCount(int nullCount) {
		this.nullCount = nullCount;
	}
	public List<String> getDistinctValueList() {
		return distinctValueList;
	}
	public void setDistinctValueList(List<String> distinctValueList) {
		this.distinctValueList = distinctValueList;
	}
	public int getDistinctCount() {
		return distinctCount;
	}
	public void setDistinctCount(int distinctCount) {
		this.distinctCount = distinctCount;
	}
	public Map<String, Long> getFrequencyOfColumnValuesMap() {
		return frequencyOfColumnValuesMap;
	}
	public void setFrequencyOfColumnValuesMap(Map<String, Long> frequencyOfColumnValuesMap) {
		this.frequencyOfColumnValuesMap = frequencyOfColumnValuesMap;
	}
	public List<String> getUniqueValuesList() {
		return uniqueValuesList;
	}
	public void setUniqueValuesList(List<String> uniqueValuesList) {
		this.uniqueValuesList = uniqueValuesList;
	}
	public List<String> getDuplicateValuesList() {
		return duplicateValuesList;
	}
	public void setDuplicateValuesList(List<String> duplicateValuesList) {
		this.duplicateValuesList = duplicateValuesList;
	}
	public int getUniqueCount() {
		return uniqueCount;
	}
	public void setUniqueCount(int uniqueCount) {
		this.uniqueCount = uniqueCount;
	}
	public int getDuplicateCount() {
		return duplicateCount;
	}
	public void setDuplicateCount(int duplicateCount) {
		this.duplicateCount = duplicateCount;
	}
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}
	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public int getMinLength() {
		return minLength;
	}
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
	public int getAverageLength() {
		return averageLength;
	}
	public void setAverageLength(int averageLength) {
		this.averageLength = averageLength;
	}
	public int getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	public int getMinValue() {
		return minValue;
	}
	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}
	public int getAverageValue() {
		return averageValue;
	}
	public void setAverageValue(int averageValue) {
		this.averageValue = averageValue;
	}
	public double getMinValueDecimal() {
		return minValueDecimal;
	}
	public void setMinValueDecimal(double minValueDecimal) {
		this.minValueDecimal = minValueDecimal;
	}
	public double getMaxValueDecimal() {
		return maxValueDecimal;
	}
	public void setMaxValueDecimal(double maxValueDecimal) {
		this.maxValueDecimal = maxValueDecimal;
	}
	public double getAverageValueDecimal() {
		return averageValueDecimal;
	}
	public void setAverageValueDecimal(double averageValueDecimal) {
		this.averageValueDecimal = averageValueDecimal;
	}
	public LocalDate getMinDate() {
		return minDate;
	}
	public void setMinDate(LocalDate minDate) {
		this.minDate = minDate;
	}
	public LocalDate getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(LocalDate maxDate) {
		this.maxDate = maxDate;
	}
	public long getTrueCount() {
		return trueCount;
	}
	public void setTrueCount(long trueCount) {
		this.trueCount = trueCount;
	}
	public long getFalseCount() {
		return falseCount;
	}
	public void setFalseCount(long falseCount) {
		this.falseCount = falseCount;
	}	
	
}
