package com.data.stringdatatype.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Document(collection = "dataProfilerInfo")
public class DataProfilerInfo {
	
	@Id
	private String _id;
	private String fileName;
	private List<DatasetStats> datasetStats;
	
}
