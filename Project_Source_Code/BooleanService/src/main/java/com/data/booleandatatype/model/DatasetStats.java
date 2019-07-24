package com.data.booleandatatype.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatasetStats {
	String columnName;
	ProfilingInfoModel profilingInfo;
	DimensionInfoModel dimentionList;

}
