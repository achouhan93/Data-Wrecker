package com.data.columnStatistics.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.columnStatistics.dao.ColumnStatisticsDao;
import com.data.columnStatistics.service.ColumnStatisticsService;

@Service
@Transactional
public class ColumnStatisticsServiceImpl implements ColumnStatisticsService {
	@Autowired
	ColumnStatisticsDao columnStatisticsDao;

	@Override
	public String getColumnStatistics(String columnName) {
		@SuppressWarnings("unchecked")
		List<String> columnValuesList = (List<String>) columnStatisticsDao.getColumnValues(columnName);
		int totalRowCount= columnValuesList.size();
		List<String> columnValuesListWithoutNull = getListWithoutNull(columnValuesList);
		int nullCount= totalRowCount-columnValuesListWithoutNull.size();
		List<String> distinctValueList=columnValuesListWithoutNull.stream().distinct().collect(Collectors.toList());
		System.out.println("Distinct values list :"+distinctValueList);
		System.out.println(" ");
		// System.out.println(columnValues);
		Map<String, Long> frequencyMap = columnValuesListWithoutNull.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		for (Map.Entry<String, Long> entry : frequencyMap.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		return ("Service IMPL input column name : " + columnName);
	}

	private List<String> getListWithoutNull(List<String> list) {
		return list.stream().filter(Objects::nonNull).collect(Collectors.toList());
	}

}
