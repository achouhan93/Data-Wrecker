package com.data.columnStatistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.columnStatistics.service.ColumnStatisticsService;

@RestController
@RequestMapping("/column-statistics")
public class ColumnStatisticsController {
	
	@Autowired
	ColumnStatisticsService columnStatisticsService;
	@GetMapping("/")
	public String getColumnStatistics() {
		String dbName="test";
		String columnName="Region";//Region/Sales Channel/Total Profit/Order Date/Date(weatherAusData)
		String collectionName="salesDataSmall";//weatherAusData/salesDataSmall
		String columnDataType="String";//String/Double/Integer/Date/Boolean
		String dateFormat="dd-MM-yy";
		String booleanTrueValue="Online";//True/1/Y/y
		String booleanFalseValue="Offline";//False/0/N/n
		return "Column Statistics Working "+columnStatisticsService.getColumnStatistics(dbName,collectionName, columnName, columnDataType, dateFormat, booleanTrueValue, booleanFalseValue);
	}
//	@GetMapping("/getCSV")
//	public String getCSV() {
//		return columnStatisticsService.getCSV();
//	}
}
