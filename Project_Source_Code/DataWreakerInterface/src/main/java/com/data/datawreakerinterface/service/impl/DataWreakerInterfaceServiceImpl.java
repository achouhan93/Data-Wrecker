package com.data.datawreakerinterface.service.impl;

import java.io.File;
import java.io.IOException;

import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.datawreakerinterface.exception.PatternIdentificationException;
import com.data.datawreakerinterface.service.DataWreakerIntefaceService;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

@Service
@Transactional
public class DataWreakerInterfaceServiceImpl implements DataWreakerIntefaceService {

	public String putCsvDataIntoMongo() throws PatternIdentificationException {
		Runtime r = Runtime.getRuntime();
		String fileName = null;
		File dir = new File(
				"D:\\software\\mongodb-win32-x86_64-2008plus-ssl-4.0.9\\mongodb-win32-x86_64-2008plus-ssl-4.0.9\\bin\\dataset");
		try {
			File[] listOfFiles = dir.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					System.out.println("File " + listOfFiles[i].getName());
					fileName = listOfFiles[i].getName();
				}
			}
			String[] collectionName= fileName.split("\\.");
			System.out.println("Executing shell command to import file data into MongoDB");

			r.exec("c:\\windows\\system32\\cmd.exe /c mongoimport -d testdataset -c " + collectionName[0]
					+ " --type csv --file " + fileName +" --headerline", null, dir);
			
			
			@SuppressWarnings("resource")
			MongoDatabase mydatabase = new MongoClient().getDatabase("testdataset");
			FindIterable<Document> mydatabaserecords = mydatabase.getCollection(collectionName[0]).find();
			MongoCursor<Document> iterator = mydatabaserecords.iterator();
			
			if (iterator.hasNext()) {
				System.out.println("Data import has failed!!!!!!!!!!!!");
				System.out.println("seems like collection with same dataset name already exist");
				throw new PatternIdentificationException(com.data.datawreakerinterface.exception.ErrorCodes.SOMETHING_WENT_WRONG);
			} 
		}
			catch (IOException e) {
				e.printStackTrace();
			}
		
		return "Data imported from file to MongoDB Successfully";
	}

}
