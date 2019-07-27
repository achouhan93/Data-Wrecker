package com.data.integerdatatypeservice.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataProfilerInfo {

	@Id
	String _id;
	String fileName;
	List<DataSetStats> datasetStats;
}
