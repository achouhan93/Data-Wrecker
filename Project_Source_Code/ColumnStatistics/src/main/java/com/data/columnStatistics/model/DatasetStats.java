package com.data.columnStatistics.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DatasetStats {
	
	private String columnName;
	private PropertyModel propertyModel;
	private String columnDataType;
	private ColumnStats columnStats;
	private DimensionServices dimensionServices;
}
