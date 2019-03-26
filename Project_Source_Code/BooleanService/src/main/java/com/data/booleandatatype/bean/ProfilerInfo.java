package com.data.booleandatatype.bean;

public class ProfilerInfo {

	private int totalRowCount;
	private int nullCount;
	private int distinctCount;
	private int uniqueCount;
	private int duplicateCount;
	private int minLength;
	private int maxLength;
	private int avgLength;
	private int maxValue;
	private int minValue;
	private int avgValue;
	private int trueCount;
	private int falseCount;
	private int wreckingPercentage;
	
	
	
	public ProfilerInfo(int totalRowCount, int nullCount, int distinctCount, int uniqueCount, int duplicateCount,
			int minLength, int maxLength, int avgLength, int maxValue, int minValue, int avgValue, int trueCount,
			int falseCount, int wreckingPercentage) {
		super();
		this.totalRowCount = totalRowCount;
		this.nullCount = nullCount;
		this.distinctCount = distinctCount;
		this.uniqueCount = uniqueCount;
		this.duplicateCount = duplicateCount;
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.avgLength = avgLength;
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.avgValue = avgValue;
		this.trueCount = trueCount;
		this.falseCount = falseCount;
		this.wreckingPercentage = wreckingPercentage;
	}
	
	
	public int getTotalRowCount() {
		return totalRowCount;
	}
	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}
	public int getNullCount() {
		return nullCount;
	}
	public void setNullCount(int nullCount) {
		this.nullCount = nullCount;
	}
	public int getDistinctCount() {
		return distinctCount;
	}
	public void setDistinctCount(int distinctCount) {
		this.distinctCount = distinctCount;
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
	public int getMinLength() {
		return minLength;
	}
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public int getAvgLength() {
		return avgLength;
	}
	public void setAvgLength(int avgLength) {
		this.avgLength = avgLength;
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
	public int getAvgValue() {
		return avgValue;
	}
	public void setAvgValue(int avgValue) {
		this.avgValue = avgValue;
	}
	public int getTrueCount() {
		return trueCount;
	}
	public void setTrueCount(int trueCount) {
		this.trueCount = trueCount;
	}
	public int getFalseCount() {
		return falseCount;
	}
	public void setFalseCount(int falseCount) {
		this.falseCount = falseCount;
	}


	public int getWreckingPercentage() {
		return wreckingPercentage;
	}


	public void setWreckingPercentage(int wreckingPercentage) {
		this.wreckingPercentage = wreckingPercentage;
	}
	
	
	
	
	
}
