package com.data.patternidentification.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PatternIdentificaitonEntity {
	
	@Id
    private String id;
	

}
