package com.data.booleandatatype.model;

public class DistinctValueList {

	private String distinctValueName;
	private int distinctValueCount;
		
	public DistinctValueList(String value, int valueCount) {
		super();
		this.distinctValueName = value;
		this.distinctValueCount = valueCount;
	}
	
	
	public String getValue() {
		return distinctValueName;
	}
	public void setValue(String value) {
		this.distinctValueName = value;
	}
	public int getValueCount() {
		return distinctValueCount;
	}
	public void setValueCount(int valueCount) {
		this.distinctValueCount = valueCount;
	}
	
}
