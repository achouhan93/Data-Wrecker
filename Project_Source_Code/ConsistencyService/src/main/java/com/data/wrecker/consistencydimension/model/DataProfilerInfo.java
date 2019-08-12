package com.data.wrecker.consistencydimension.model;

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
	List<DatasetStats> datasetStats;
	
}
