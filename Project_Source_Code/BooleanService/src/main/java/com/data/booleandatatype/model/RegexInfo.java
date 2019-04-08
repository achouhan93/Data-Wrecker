package com.data.booleandatatype.model;

public class RegexInfo {


	private String regexPattern;
	private int regexPatternCount;
	
	
	public RegexInfo(String regexPattern, int regexPatternCount) {
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


	public int getRegexPatternCount() {
		return regexPatternCount;
	}


	public void setRegexPatternCount(int regexPatternCount) {
		this.regexPatternCount = regexPatternCount;
	}	
	
	

	
}
