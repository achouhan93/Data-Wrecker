package com.data.columnStatistics.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangesLog {

	private String columnName;
	private String oldValue;
	private String newValue;
	private int oid;
	private String dimensionName;
	private String datasetName;
}
