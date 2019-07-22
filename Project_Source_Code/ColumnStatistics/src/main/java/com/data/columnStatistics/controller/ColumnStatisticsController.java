package com.data.columnStatistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.columnStatistics.service.ColumnStatisticsService;

@RestController
@RequestMapping("/columnStatistics")
public class ColumnStatisticsController {
	
	@Autowired
	ColumnStatisticsService columnStatisticsService;
	@GetMapping("/getColumnStats")
	public String getColumnStatistics(@RequestParam String fileName) {
		String dateFormat="dd-MM-yy";
		String booleanTrueValue="Online";//True/1/Y/y
		String booleanFalseValue="Offline";//False/0/N/n
		String status = columnStatisticsService.getColumnStatistics(fileName,dateFormat, booleanTrueValue, booleanFalseValue);
		if(status.equals("Success")) {
			return "Success";
		}else {
			return "Error";
		}
	}
}
