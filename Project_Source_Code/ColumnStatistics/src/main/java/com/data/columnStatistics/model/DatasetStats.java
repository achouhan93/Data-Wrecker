package com.data.columnStatistics.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DatasetStats {
	
	private String columnName;
	private ProfilingInfoModel profilingInfo;
	private DimensionInfoModel dimensionServices;
	
}
