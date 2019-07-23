package com.data.datedatatype.service;

import java.util.List;

import com.data.datedatatype.model.Dimensions;

public interface DatasetStatsProcessingService {

	public List<Dimensions> getDimensionResults(String columnName, int wreckingPercentage);
}
