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
		String columnName="questionDescription";
		return "Column Statistics Working "+columnStatisticsService.getColumnStatistics(columnName);		
	}
}
