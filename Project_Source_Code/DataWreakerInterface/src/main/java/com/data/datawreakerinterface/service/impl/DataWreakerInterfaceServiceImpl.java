package com.data.datawreakerinterface.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.datawreakerinterface.service.DataWreakerIntefaceService;

@Service
@Transactional
public class DataWreakerInterfaceServiceImpl implements DataWreakerIntefaceService {
	
	public String putCsvDataIntoMongo() {
		Runtime r = Runtime.getRuntime();

		//Process p = null;
		File dir=new File("D:\\software\\mongodb-win32-x86_64-2008plus-ssl-4.0.9\\mongodb-win32-x86_64-2008plus-ssl-4.0.9\\bin");
		try {
			System.out.println("Executing shell command to import file data into MongoDB");
			//p =
			r.exec("c:\\windows\\system32\\cmd.exe /c mongoimport -d testdataset -c TexasPublicSalary --type csv --file Texas_Public_Salaries.csv --headerline" ,null,dir);
			System.out.println("Data is imported from csv to MongoDB");
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return "Data imported from file to MongoDB Successfully";
	}

}
