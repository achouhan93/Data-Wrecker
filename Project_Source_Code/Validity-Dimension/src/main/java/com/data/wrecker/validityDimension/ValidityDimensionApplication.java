package com.data.wrecker.validityDimension;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ValidityDimensionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValidityDimensionApplication.class, args);
//		ArrayList<String> columnData = new ArrayList<String>();
//		columnData.add("5");
//		columnData.add("10");
//		columnData.add("15");
//		columnData.add("4");
//		columnData.add("6");
//		columnData.add("1");
//		columnData.add("20");
//		double mean = calculateMean(columnData);
//		double stddev = stdDeviation(columnData);
//		System.out.println("Mean: "+Math.round(mean));
//		
//		System.out.println("std.deviation: "+stddev);
//		
//	
//	
//	
//	}
//	public static double calculateMean(ArrayList<String> columnData) {
//	    double sum = 0;
//	    for (int i = 0; i < columnData.size(); i++) {
//	        sum = sum + Double.parseDouble(columnData.get(i));
//	    }
//	    double datasize = (double)columnData.size();
//	    double meanValue = sum / datasize;
//	   
//	    return meanValue;
//	}
//	
//	public static double stdDeviation (ArrayList<String> columnData)
//	{
//	    double mean = calculateMean(columnData);
//	    double temp = 0;
//
//	    for (int i = 0; i < columnData.size(); i++)
//	    {
//	    	double val = Double.parseDouble(columnData.get(i));
//
//	        double squrDiffToMean = Math.pow(val - mean, 2);
//
//	        temp += squrDiffToMean;
//	    }
//
//	    double meanOfDiffs = (double) temp / (double) (columnData.size());
//
//	    return Math.sqrt(meanOfDiffs);
	}

}
