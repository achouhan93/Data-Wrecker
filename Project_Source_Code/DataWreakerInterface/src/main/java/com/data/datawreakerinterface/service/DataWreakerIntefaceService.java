package com.data.datawreakerinterface.service;

import com.data.datawreakerinterface.exception.DataWreakernterfaceException;
import com.data.datawreakerinterface.model.DatasetDetails;

public interface DataWreakerIntefaceService {

	public DatasetDetails putCsvDataIntoMongo() throws DataWreakernterfaceException;

}
