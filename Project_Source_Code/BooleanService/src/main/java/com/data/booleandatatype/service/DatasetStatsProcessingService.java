package com.data.booleandatatype.service;

import java.util.List;

import com.data.booleandatatype.model.Dimensions;




public interface DatasetStatsProcessingService {

	public List<Dimensions> getDimensionResults(String columnName);
}
