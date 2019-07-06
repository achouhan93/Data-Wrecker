package com.data.chardatatype.service;

import java.util.List;

import com.data.chardatatype.model.Dimensions;


public interface DatasetStatsProcessingService {

	public List<Dimensions> getDimensionResults(String columnName);
}
