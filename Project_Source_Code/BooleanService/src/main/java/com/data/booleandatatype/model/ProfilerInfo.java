package com.data.booleandatatype.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ProfilerInfo {

	@Id
	private int _id;
	private String columnname;
	private String columndatatype;
	private int wreckingPercentage;
	private int totalRowCount;
	private int nullCount;
	private int distinctCount;
	private ArrayList<DistinctValueList> distinctvaluelist;
	private int uniqueCount;
	private int duplicateCount;
	private int minLength;
	private int maxLength;
	private int avgLength;
	private int minValue;
	private int maxValue;
	private int avgValue;
	private int trueCount;
	private int falseCount;
	private ArrayList<RegexInfo> regexInfo;
	
	public ProfilerInfo(int _id, String columnname, String columndatatype, int wreckingPercentage, int totalRowCount,
			int nullCount, int distinctCount, ArrayList<DistinctValueList> distinctvaluelist, int uniqueCount,
			int duplicateCount, int minLength, int maxLength, int avgLength, int minValue, int maxValue, int avgValue,
			int trueCount, int falseCount, ArrayList<RegexInfo> regexInfo) {
		super();
		this._id = _id;
		this.columnname = columnname;
		this.columndatatype = columndatatype;
		this.wreckingPercentage = wreckingPercentage;
		this.totalRowCount = totalRowCount;
		this.nullCount = nullCount;
		this.distinctCount = distinctCount;
		this.distinctvaluelist = distinctvaluelist;
		this.uniqueCount = uniqueCount;
		this.duplicateCount = duplicateCount;
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.avgLength = avgLength;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.avgValue = avgValue;
		this.trueCount = trueCount;
		this.falseCount = falseCount;
		this.regexInfo = regexInfo;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	public String getColumndatatype() {
		return columndatatype;
	}

	public void setColumndatatype(String columndatatype) {
		this.columndatatype = columndatatype;
	}

	public int getWreckingPercentage() {
		return wreckingPercentage;
	}

	public void setWreckingPercentage(int wreckingPercentage) {
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

	public ArrayList<DistinctValueList> getDistinctvaluelist() {
		return distinctvaluelist;
	}

	public void setDistinctvaluelist(ArrayList<DistinctValueList> distinctvaluelist) {
		this.distinctvaluelist = distinctvaluelist;
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

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
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

	public ArrayList<RegexInfo> getRegexInfo() {
		return regexInfo;
	}

	public void setRegexInfo(ArrayList<RegexInfo> regexInfo) {
		this.regexInfo = regexInfo;
	}	
	
	
	}
