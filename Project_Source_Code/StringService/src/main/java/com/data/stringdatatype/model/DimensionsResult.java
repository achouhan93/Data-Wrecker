package com.data.stringdatatype.model;

public class DimensionsResult {

	private boolean nullCheck;
	private boolean accuracyCheck;
	private boolean consistencyCheck;
	private boolean validityCheck;
	
	public boolean isNullCheck() {
		return nullCheck;
	}
	public void setNullCheck(boolean nullCheck) {
		this.nullCheck = nullCheck;
	}
	public boolean isAccuracyCheck() {
		return accuracyCheck;
	}
	public void setAccuracyCheck(boolean accuracyCheck) {
		this.accuracyCheck = accuracyCheck;
	}
	public boolean isConsistencyCheck() {
		return consistencyCheck;
	}
	public void setConsistencyCheck(boolean consistencyCheck) {
		this.consistencyCheck = consistencyCheck;
	}
	public boolean isValidityCheck() {
		return validityCheck;
	}
	public void setValidityCheck(boolean validityCheck) {
		this.validityCheck = validityCheck;
	}
	

	
}
