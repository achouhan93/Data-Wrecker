package com.data.stringdatatype.model;

public class DistinctValueListString {

	private String distinctValueName;
	private int distinctValueCount;
	
	
	
	public DistinctValueListString(String distinctValueName, int distinctValueCount) {
		super();
		this.distinctValueName = distinctValueName;
		this.distinctValueCount = distinctValueCount;
	}
	public String getDistinctValueName() {
		return distinctValueName;
	}
	public void setDistinctValueName(String distinctValueName) {
		this.distinctValueName = distinctValueName;
	}
	public int getDistinctValueCount() {
		return distinctValueCount;
	}
	public void setDistinctValueCount(int distinctValueCount) {
		this.distinctValueCount = distinctValueCount;
	}
	
	
	
}
