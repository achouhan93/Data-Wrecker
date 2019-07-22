package com.data.columndatatypeprediction.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSetStats {
	String columnName;
	ProfilingInfoModel profilingInfo;
	DimensionInfoModel dimentionList;

}
