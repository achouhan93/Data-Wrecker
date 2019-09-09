package com.data.patternidentification;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PatternIdentification {
	
	private static final Logger LOGGER = LogManager.getLogger();
	//private static final String SAMPLE_CSV_FILE_PATH = "D:\\FL_insurance_sample.csv";

	public static void main(String[] args) throws IOException {
		SpringApplication.run(PatternIdentification.class, args);
		LOGGER.info("******************PatternIdentification : APPLICATION STARTED********************");
/*		int cnt = 0;
		File file = new File("D:\\FL_insurance_sample.csv");
		List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		List<String> Columndata = new ArrayList<>();
		for (int i=0;i<=lines.size();i++)
		{
		for (String line : lines)
		   { 
			if (cnt <= lines.size())
			{
		     String[] array = line.split(",");
		     Columndata.add(array[cnt]);
		     System.out.println(array[cnt]);
			}
			
		   }
		cnt++;
		}*/
		/*try (
	            Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
	            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
	        ) {
	            for (CSVRecord csvRecord : csvParser) {
	                // Accessing Values by Column Index
	                String name = csvRecord.get(0);
	                String email = csvRecord.get(1);
	                String phone = csvRecord.get(2);
	                String country = csvRecord.get(3);

	                System.out.println("Record No - " + csvRecord.getRecordNumber());
	                System.out.println("Name : " + name);
	                System.out.println("Email : " + email);
	                System.out.println("Phone : " + phone);
	                System.out.println("Country : " + country);
	                System.out.println("---------------\n\n");
	            }
	}*/
	}

}
