package com.data.datedatatype.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ProfilerInfo {

	@Id
	private int _id;
	private int totalRowCount;
	private int nullCount;
	private int distinctCount;
	private List<DistinctValueList> distinctvaluelist;
	private int uniqueCount;
	private int duplicateCount;
	private int minLength;
	private int maxLength;
	private int avgLength;
	private int trueCount;
	private int falseCount;
	private List<RegexInfo> regexInfo;

	public ProfilerInfo(int _id, int totalRowCount, int nullCount, int distinctCount,
			List<DistinctValueList> distinctvaluelist, int uniqueCount, int duplicateCount, int minLength,
			int maxLength, int avgLength, int trueCount, int falseCount, List<RegexInfo> regexInfo) {
		super();
		this._id = _id;
		this.totalRowCount = totalRowCount;
		this.nullCount = nullCount;
		this.distinctCount = distinctCount;
		this.distinctvaluelist = distinctvaluelist;
		this.uniqueCount = uniqueCount;
		this.duplicateCount = duplicateCount;
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.avgLength = avgLength;
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

	public List<DistinctValueList> getDistinctvaluelist() {
		return distinctvaluelist;
	}

	public void setDistinctvaluelist(List<DistinctValueList> distinctvaluelist) {
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

	public List<RegexInfo> getRegexInfo() {
		return regexInfo;
	}

	public void setRegexInfo(List<RegexInfo> regexInfo) {
		this.regexInfo = regexInfo;
	}

}
