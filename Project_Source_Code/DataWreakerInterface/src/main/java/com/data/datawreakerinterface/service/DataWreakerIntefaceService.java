package com.data.datawreakerinterface.service;

import com.data.datawreakerinterface.exception.PatternIdentificationException;

public interface DataWreakerIntefaceService {

	public String putCsvDataIntoMongo() throws PatternIdentificationException;

}
