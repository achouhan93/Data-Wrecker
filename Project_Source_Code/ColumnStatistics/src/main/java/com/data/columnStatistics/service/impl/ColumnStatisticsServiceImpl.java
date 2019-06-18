package com.data.columnStatistics.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.columnStatistics.dao.ColumnStatisticsDaoMongo;
import com.data.columnStatistics.model.ColumnStatisticsModel;
import com.data.columnStatistics.service.ColumnStatisticsService;

@Service
@Transactional
public class ColumnStatisticsServiceImpl implements ColumnStatisticsService {
	@Autowired
	// ColumnStatisticsDao columnStatisticsDao;
	ColumnStatisticsDaoMongo columnStatisticsDaoMongo;

	@Override
	public String getColumnStatistics(String dbName, String collectionName, String columnName, String columnDataType, String dateFormat, String booleanTrueValue, String booleanFalseValue) {
		ColumnStatisticsModel columnStatisticsModel=new ColumnStatisticsModel();
		List<String> columnValuesList = columnStatisticsDaoMongo.getColumnValues(dbName, collectionName, columnName);
		int rowCount = columnValuesList.size();
		columnStatisticsModel.setRowCount(rowCount);
		System.out.println("");
		System.out.println("");
		System.out.println(" Column Statistics ");
		System.out.println("");
		System.out.println("Total Row count :" + rowCount);
		List<String> columnValuesListWithoutNull = getListWithoutNull(columnValuesList);
		int nullCount = rowCount - columnValuesListWithoutNull.size();
		columnStatisticsModel.setNullCount(nullCount);
		System.out.println("Null count :" + nullCount);
		List<String> distinctValueList = columnValuesListWithoutNull.stream().distinct().collect(Collectors.toList());
		columnStatisticsModel.setDistinctValueList(distinctValueList);
		int distinctCount = distinctValueList.size();
		columnStatisticsModel.setDistinctCount(distinctCount);
		System.out.println("Distinct values list :" + distinctValueList);
		System.out.println("Total Distinct values :" + distinctCount);
		System.out.println(" ");	
		// Get count of each value in map
		Map<String, Long> frequencyOfColumnValuesMap = columnValuesListWithoutNull.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		columnStatisticsModel.setFrequencyOfColumnValuesMap(frequencyOfColumnValuesMap);
		// Unique values list
		List<String> uniqueValuesList = new ArrayList<>();
		List<String> duplicateValuesList = new ArrayList<>();
		int uniqueCount = 0;
		int duplicateCount = 0;
		for (Map.Entry<String, Long> entry : frequencyOfColumnValuesMap.entrySet()) {
			//System.out.println(entry.getKey() + ": " + entry.getValue());
			if (entry.getValue() == 1) {
				uniqueCount = uniqueCount + 1;
				uniqueValuesList.add(entry.getKey());
			}
			if (entry.getValue() > 1) {
				duplicateCount = duplicateCount + 1;
				duplicateValuesList.add(entry.getKey());
			}
		};
		columnStatisticsModel.setUniqueValuesList(uniqueValuesList);
		columnStatisticsModel.setUniqueCount(uniqueCount);
		columnStatisticsModel.setDuplicateValuesList(duplicateValuesList);
		columnStatisticsModel.setUniqueCount(uniqueCount);
		System.out.println("Unique value List: " + uniqueValuesList);
		System.out.println("Total unique values :" + uniqueCount);
		System.out.println("Duplicate value List: " + duplicateValuesList);
		System.out.println("Total Duplicate values :" + duplicateCount);
		boolean isPrimaryKey = false;
		if (rowCount == distinctCount) {
			isPrimaryKey = true;
		}
		;
		columnStatisticsModel.setPrimaryKey(isPrimaryKey);
		System.out.println(" ");
		System.out.println("IsPrimaryKey: " + isPrimaryKey);
		System.out.println(" ");

		int maxLength = 0;
		int minLength = 0;
		int averageLength = 0;
		int maxValue = 0;
		int minValue = 0;
		int averageValue = 0;
		double minValueDecimal=0.0;
		double maxValueDecimal=0.0;
		double averageValueDecimal=0.0;
		LocalDate minDate = null;
		LocalDate maxDate = null;
		long trueCount = 0;
		long falseCount = 0;
		switch (columnDataType) {
		case "String":
			maxLength = getMaxLength(columnValuesListWithoutNull);
			minLength = getMinLength(columnValuesListWithoutNull);
			averageLength = getAvgLength(columnValuesListWithoutNull);
			break;
		case "Integer":
			maxValue = getMaxValueInteger(columnValuesListWithoutNull);
			minValue = getMinValueInteger(columnValuesListWithoutNull);
			averageValue = getAvgValueInteger(columnValuesListWithoutNull);
			break;
		case "Decimal":
			maxValueDecimal = getMaxValueDecimal(columnValuesListWithoutNull);
			minValueDecimal = getMinValueDecimal(columnValuesListWithoutNull);
			averageValueDecimal = getAvgValueDecimal(columnValuesListWithoutNull);
			break;
		case "Date":
			maxDate = getMaxDate(columnValuesListWithoutNull,dateFormat);
			minDate = getMinDate(columnValuesListWithoutNull,dateFormat);
			break;
		case "Boolean":
			//trueCount = getTrueCount(frequencyOfColumnValuesMap, booleanTrueValue);
			//falseCount = getFalseCount(columnValuesListWithoutNull);
			trueCount=frequencyOfColumnValuesMap.get(booleanTrueValue);
			falseCount=frequencyOfColumnValuesMap.get(booleanFalseValue);
			break;
		default:
			break;
		};
		columnStatisticsModel.setMaxValue(maxValue);
		columnStatisticsModel.setMinValue(minValue);
		columnStatisticsModel.setAverageValue(averageValue);
		System.out.println("Max Value : " + maxValue);
		System.out.println("Min Value : " + minValue);
		System.out.println("Average Value : " + averageValue);
		
		columnStatisticsModel.setMaxValueDecimal(maxValueDecimal);
		columnStatisticsModel.setMinValueDecimal(minValueDecimal);
		columnStatisticsModel.setAverageValueDecimal(averageValueDecimal);
		System.out.println("Max Value Decimal: " + maxValueDecimal);
		System.out.println("Min Value Decimal: " + minValueDecimal);
		System.out.println("Average Value Decimal: " + averageValueDecimal);
		
		columnStatisticsModel.setMaxLength(maxLength);
		columnStatisticsModel.setMinLength(minLength);
		columnStatisticsModel.setAverageLength(averageLength);
		System.out.println("Max length : " + maxLength);
		System.out.println("Min length : " + minLength);
		System.out.println("Average length : " + averageLength);
		
		columnStatisticsModel.setMaxDate(maxDate);
		columnStatisticsModel.setMinDate(minDate);
		System.out.println("Max Date : " + maxDate);
		System.out.println("Min Date : " + minDate);
		
		columnStatisticsModel.setTrueCount(trueCount);
		columnStatisticsModel.setFalseCount(falseCount);
		System.out.println("True Count: " + trueCount);
		System.out.println("False Count: " + falseCount);
		
		columnStatisticsDaoMongo.saveColumnStatistics(columnStatisticsModel,dbName,collectionName,columnName);
		return ("Service IMPL input column name : " + columnName);
	}

	private double getAvgValueDecimal(List<String> columnValuesListWithoutNull) {
		List<Double> columnValuesListWithoutNullDecimal = columnValuesListWithoutNull.stream()
				.map(s -> Double.parseDouble(s)).collect(Collectors.toList());
		return (double) columnValuesListWithoutNullDecimal.stream().mapToDouble(val -> val).average().orElse(0.0);
	}

	private double getMinValueDecimal(List<String> columnValuesListWithoutNull) {
		List<Double> columnValuesListWithoutNullDecimal = columnValuesListWithoutNull.stream()
				.map(s -> Double.parseDouble(s)).collect(Collectors.toList());
		return Collections.min(columnValuesListWithoutNullDecimal);
	}

	private double getMaxValueDecimal(List<String> columnValuesListWithoutNull) {
		List<Double> columnValuesListWithoutNullDecimal = columnValuesListWithoutNull.stream()
				.map(s -> Double.parseDouble(s)).collect(Collectors.toList());
		return Collections.max(columnValuesListWithoutNullDecimal);
	}

	private int getAvgValueInteger(List<String> columnValuesListWithoutNull) {
		List<Integer> columnValuesListWithoutNullInteger = columnValuesListWithoutNull.stream()
				.map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		return (int) columnValuesListWithoutNullInteger.stream().mapToInt(val -> val).average().orElse(0.0);
	}

	private int getMinValueInteger(List<String> columnValuesListWithoutNull) {
		List<Integer> columnValuesListWithoutNullInteger = columnValuesListWithoutNull.stream()
				.map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		return Collections.min(columnValuesListWithoutNullInteger);
	}

	private int getMaxValueInteger(List<String> columnValuesListWithoutNull) {
		List<Integer> columnValuesListWithoutNullInteger = columnValuesListWithoutNull.stream()
				.map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		return Collections.max(columnValuesListWithoutNullInteger);
	}

	private LocalDate getMinDate(List<String> columnValuesListWithoutNull,String dateFormat) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		List<LocalDate> columnValuesListWithoutNullDate= columnValuesListWithoutNull.stream().map(date -> LocalDate.parse(date, formatter)).collect(Collectors.toList());
		return Collections.min(columnValuesListWithoutNullDate);
	}

	private LocalDate getMaxDate(List<String> columnValuesListWithoutNull,String dateFormat) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		List<LocalDate> columnValuesListWithoutNullDate= columnValuesListWithoutNull.stream().map(date -> LocalDate.parse(date, formatter)).collect(Collectors.toList());
		return Collections.max(columnValuesListWithoutNullDate);
	}

	private int getAvgLength(List<String> columnValuesListWithoutNull) {
		List<Integer> lengthOfEachValueList = getLengthOfEachValueList(columnValuesListWithoutNull);
		return (int) lengthOfEachValueList.stream().mapToInt(val -> val).average().orElse(0.0);
	}

	private int getMinLength(List<String> columnValuesListWithoutNull) {
		List<Integer> lengthOfEachValueList = getLengthOfEachValueList(columnValuesListWithoutNull);
		Collections.sort(lengthOfEachValueList);
		return lengthOfEachValueList.get(0);
	}

	private int getMaxLength(List<String> columnValuesListWithoutNull) {
		List<Integer> lengthOfEachValueList = getLengthOfEachValueList(columnValuesListWithoutNull);
		Collections.sort(lengthOfEachValueList);
		return lengthOfEachValueList.get(lengthOfEachValueList.size() - 1);
	}

	private List<Integer> getLengthOfEachValueList(List<String> columnValuesListWithoutNull) {
		List<Integer> lengthOfEachValueList = new ArrayList<>();
		for (String strTemp : columnValuesListWithoutNull) {
			lengthOfEachValueList.add(strTemp.length());
		}
		return lengthOfEachValueList;
	}

	private List<String> getListWithoutNull(List<String> list) {
		return list.stream().filter(Objects::nonNull).collect(Collectors.toList());
	}

}