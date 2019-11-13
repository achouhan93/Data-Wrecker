package com.data.datedatatype.model;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatasetStats {
	String columnName;
	ProfilingInfoModel profilingInfo;
	List<Dimensions> dimensionsList;

}
