package com.data.wrecker.validityDimension.service;

import java.util.List;

import org.json.JSONException;

public interface ValidityDimensionService {

	public String removeValidityDimension(String collectionName, String columnName,int wreckingIds) throws JSONException;
}
