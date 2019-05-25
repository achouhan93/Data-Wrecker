package com.data.patternidentification.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatternIdenticationModel {
	private List<PatternIdentificationSubModel> column;
	int occurance ;
	String pattern;


	

}
