package com.data.columnStatistics.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DatasetStats {

	private String columnName;
	private ProfilingInfoModel profilingInfo;
	private List<Dimensions> dimensionsList;

}
