package com.data.patternidentification.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnPatternModel {
	
	@Id
	 public ObjectId _id;
	String columnName;
	PropertyModel propertyModel;

}
