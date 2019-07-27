package com.data.integerdatatypeservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FrequencyOfColumnValues {

	private String columnDistinctValue;
	private Long columnDistinctValueOccurance;
	
}
