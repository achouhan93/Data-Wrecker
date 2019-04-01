package com.data.datedatatype.model;

public class RegexInfo {

	private String regexPattern;
	private String regexPatternCount;	
	
	public RegexInfo(String regexPattern, String regexPatternCount) {
		super();
		this.regexPattern = regexPattern;
		this.regexPatternCount = regexPatternCount;
	}
	
	public String getRegexPattern() {
		return regexPattern;
	}
	public void setRegexPattern(String regexPattern) {
		this.regexPattern = regexPattern;
	}
	public String getRegexPatternCount() {
		return regexPatternCount;
	}
	public void setRegexPatternCount(String regexPatternCount) {
		this.regexPatternCount = regexPatternCount;
	}
	
	
	
	
}
