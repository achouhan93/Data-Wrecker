package com.data.wrecker.orchestrator.entity;

import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataProfilerInfo {

	String fileName;
	List<DatasetStats> datasetStats;
}
