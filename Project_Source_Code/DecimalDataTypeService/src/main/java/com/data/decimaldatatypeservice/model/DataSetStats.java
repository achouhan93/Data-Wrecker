package com.data.decimaldatatypeservice.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DataSetStats {

	private String columnName;
	private ProfilingInfoModel profilingInfo;
	List<Dimensions> dimensionsList;

}
