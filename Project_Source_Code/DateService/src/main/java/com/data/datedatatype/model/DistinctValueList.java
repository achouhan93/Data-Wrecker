package com.data.datedatatype.model;

public class DistinctValueList {

	private String value;
	private int valueCount;
		
	public DistinctValueList(String value, int valueCount) {
		super();
		this.value = value;
		this.valueCount = valueCount;
	}
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getValueCount() {
		return valueCount;
	}
	public void setValueCount(int valueCount) {
		this.valueCount = valueCount;
	}
	
}
