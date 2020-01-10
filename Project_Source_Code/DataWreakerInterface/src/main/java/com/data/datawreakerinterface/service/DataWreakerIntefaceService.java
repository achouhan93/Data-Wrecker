package com.data.datawreakerinterface.service;

import java.io.IOException;

import org.json.JSONException;

import com.data.datawreakerinterface.exception.DataWreakernterfaceException;
import com.data.datawreakerinterface.model.DatasetDetails;

public interface DataWreakerIntefaceService {

	public DatasetDetails putCsvDataIntoMongo() throws DataWreakernterfaceException;
	
	public DatasetDetails exportDataAsCSV(String collectionName) throws DataWreakernterfaceException;


	public String referenceDataApiToMongo(String referenceApi, String columnName) throws JSONException;

	String putRefenceColumnDataToMongo() throws IOException;

}
