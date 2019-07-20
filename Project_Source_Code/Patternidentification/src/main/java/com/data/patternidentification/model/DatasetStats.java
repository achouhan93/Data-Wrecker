package com.data.patternidentification.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatasetStats {
	String columnName;
	PropertyModel propertyModel;
	private String columnDataType;
	private ColumnStats columnStats;
	private DimensionServices dimensionServices;

}
