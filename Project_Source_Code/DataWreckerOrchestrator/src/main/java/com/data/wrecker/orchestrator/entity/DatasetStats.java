package com.data.wrecker.orchestrator.entity;


import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DatasetStats {
	
	@Id
	private String  _id;
	private String columnName;
	private PropertyModel propertyModel;
	private String columnDataType;
	private ColumnStats columnStats;
	private DimensionServices dimensionServices;
}
