package com.data.columndatatypeprediction.model;


import java.util.List;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSetStats {
	String columnName;
	ProfilingInfoModel profilingInfo;
	List<Dimensions> dimensionsList;

}
