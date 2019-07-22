package com.data.columnStatistics.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DatasetStats {
	String columnName;
	ProfilingInfoModel profilingInfo;
	DimensionInfoModel dimentionList;
}
