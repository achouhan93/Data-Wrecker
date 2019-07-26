package com.data.integerdatatypeservice.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DataSetStats {

	private String columnName;
	private ProfilingInfoModel profilingInfo;
	private DimensionInfoModel dimensionList;

}
