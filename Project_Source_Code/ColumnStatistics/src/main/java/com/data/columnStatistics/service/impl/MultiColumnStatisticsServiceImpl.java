package com.data.columnStatistics.service.impl;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.columnStatistics.model.DataProfilerInfo;
import com.data.columnStatistics.model.MultiColumnStats;
import com.data.columnStatistics.repository.ColumnStatsRepo;
import com.data.columnStatistics.service.MultiColumnStatisticsService;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

@Transactional
@Service
public class MultiColumnStatisticsServiceImpl implements MultiColumnStatisticsService {
	@Autowired
	private ColumnStatsRepo columnStatsRepo;

	@Autowired
	private LinearRegressionNumericalServiceImpl linearRegressionNumericalServiceImpl;
	
	@Autowired
	private ClassicalRegressionBooleanServiceImpl classicalRegressionBooleanServiceImpl;

	private Mongo mongo;
	private DB db;

	@Override
	public String multiColumnDataEvaluation(String collectionName) throws JSONException {
		DataProfilerInfo dataProfilerInfo = null;

		dataProfilerInfo = getDatasetCollectionFromMongo(collectionName, dataProfilerInfo);

		ArrayList<String> numericaColumns = new ArrayList<String>();
		ArrayList<String> booleanColumns = new ArrayList<String>();
		ArrayList<String> allColumns = new ArrayList<String>();

		//System.out.println("dataProfilerInfo" + dataProfilerInfo);

		getNumericalAndBoolColumnsInCollection(dataProfilerInfo, numericaColumns, booleanColumns, allColumns);

		//System.out.println("numericaColumns" + numericaColumns);
		// DECIMAL AND INTEGER LINEAR REGRESSION------------------------
		JSONArray dbList = readCompleteDataset(collectionName);

		evaluateNumericalColumnDependancy(dataProfilerInfo, numericaColumns, dbList);

		//System.out.println("booleanColumns" + booleanColumns);
		//Boolean - classical regression
		evaluateBooleanColumnDependancy(dataProfilerInfo, booleanColumns, allColumns, dbList);
		
		return "Success";
	}

	private void evaluateBooleanColumnDependancy(DataProfilerInfo dataProfilerInfo, ArrayList<String> booleanColumns,
			ArrayList<String> allColumns, JSONArray dbList) throws JSONException {
		for (int j = 0; j < booleanColumns.size(); j++) {
			MultiColumnStats multiColumnStats = new MultiColumnStats();
			String booleanColumn = booleanColumns.get(j);
			List<String> dependantColumnNameList = new ArrayList<String>();

			
				for (int columnIterator = 0; columnIterator < allColumns.size(); columnIterator++) {

					String evaluatingColumn = allColumns.get(columnIterator);
					ArrayList<String> evaluatingColumnData = new ArrayList<String>();
					ArrayList<String> booleanColumnData = new ArrayList<String>();
					if (booleanColumn != evaluatingColumn) {
						for (int i = 0; i < dbList.length(); i++) {

							String booleanColumnValue = dbList.getJSONObject(i).get(booleanColumn).toString();
							booleanColumnData.add(booleanColumnValue);

							String evaluatingColValue = dbList.getJSONObject(i).get(evaluatingColumn).toString();
							evaluatingColumnData.add(evaluatingColValue);
						}
						//System.out.println("1st column selected :" + booleanColumn + " comparing with 2nd column selected :" + evaluatingColumn);
						
						Boolean isColumnDependent = classicalRegressionBooleanServiceImpl
								.ClassicalRegressionBooleanEvaluator(booleanColumnData, evaluatingColumnData);

						if (isColumnDependent == true) {
							dependantColumnNameList.add(evaluatingColumn);
							System.out.println(booleanColumn + " is co-related with " + dependantColumnNameList+ "--------------------");
							System.out.println();

						}
					}
					else
					{
						continue;
					}
			}
				multiColumnStats.setDependantColumnNames(dependantColumnNameList);

				for (int i = 0; i < dataProfilerInfo.getDatasetStats().size(); i++) {
					if (dataProfilerInfo.getDatasetStats().get(i).getColumnName() == booleanColumn) {
						dataProfilerInfo.getDatasetStats().get(i).getProfilingInfo().getColumnStats()
								.setMultiColumnStats(multiColumnStats);
					}
				}	
		}
		columnStatsRepo.save(dataProfilerInfo);
	}

	private void evaluateNumericalColumnDependancy(DataProfilerInfo dataProfilerInfo, ArrayList<String> numericaColumns,
			JSONArray dbList) throws JSONException {
		for (int j = 0; j < numericaColumns.size(); j++) {
			MultiColumnStats multiColumnStats = new MultiColumnStats();
			String currentColumn = numericaColumns.get(j);
			List<String> dependantColumnNameList = new ArrayList<String>();
			for (int k = 0; k < numericaColumns.size(); k++) {
				String evaluatingColumn = numericaColumns.get(k);
				ArrayList<Float> evaluatingColumnData = new ArrayList<Float>();
				ArrayList<Float> numericalColumnData = new ArrayList<Float>();
				if (j != k) {
					for (int i = 0; i < dbList.length(); i++) {

						String numericalColumnValue = dbList.getJSONObject(i).get(currentColumn).toString();
						Float firstColValueFloat = Float.parseFloat(numericalColumnValue);
						numericalColumnData.add(firstColValueFloat);

						String evaluatingColumnValue = dbList.getJSONObject(i).get(evaluatingColumn).toString();
						Float evaluatingColumnValueFloat = Float.parseFloat(evaluatingColumnValue);
						evaluatingColumnData.add(evaluatingColumnValueFloat);
					}
					//System.out.println("1st column selected :" + currentColumn + " comparing with 2nd column selected :" + evaluatingColumn);
					Boolean isColumnDependent = linearRegressionNumericalServiceImpl
							.linearRegessionNumericalEvaluator(numericalColumnData, evaluatingColumnData);

					if (isColumnDependent == true) {
						dependantColumnNameList.add(evaluatingColumn);
						//System.out.println(numericaColumns.get(j) + "is co-related with " + numericaColumns.get(k)+ "-------------");

					}

				}

				else {
					continue;
				}
			}

			multiColumnStats.setDependantColumnNames(dependantColumnNameList);

			for (int i = 0; i < dataProfilerInfo.getDatasetStats().size(); i++) {
				if (dataProfilerInfo.getDatasetStats().get(i).getColumnName() == currentColumn) {
					dataProfilerInfo.getDatasetStats().get(i).getProfilingInfo().getColumnStats()
							.setMultiColumnStats(multiColumnStats);
				}
			}
		}
		columnStatsRepo.save(dataProfilerInfo);
	}

	private JSONArray readCompleteDataset(String collectionName) {
		mongo = new Mongo("localhost", 27017);
		db = mongo.getDB("ReverseEngineering");
		DBCollection collection = db.getCollection(collectionName); // giving the collection name
		DBCursor cursor = collection.find();
		JSONArray dbList = new JSONArray();

		while (cursor.hasNext()) {
			String str = cursor.next().toString();
			try {
				JSONObject jsnobj = new JSONObject(str);
				dbList.put(jsnobj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dbList;
	}

	private void getNumericalAndBoolColumnsInCollection(DataProfilerInfo dataProfilerInfo,
			ArrayList<String> numericaColumns, ArrayList<String> booleanColumns, ArrayList<String> allColumns) {
		for (int i = 0; i < dataProfilerInfo.getDatasetStats().size(); i++) {
			String dataTypeOfColumn = dataProfilerInfo.getDatasetStats().get(i).getProfilingInfo().getColumnDataType();
			String columnName = dataProfilerInfo.getDatasetStats().get(i).getColumnName();

			allColumns.add(columnName);

			if (dataTypeOfColumn.equalsIgnoreCase("Integer") || dataTypeOfColumn.equalsIgnoreCase("Decimal")) {
				numericaColumns.add(columnName);
			} else if (dataTypeOfColumn.equalsIgnoreCase("Boolean")) {
				booleanColumns.add(columnName);
			}

		}
	}

	private DataProfilerInfo getDatasetCollectionFromMongo(String collectionName, DataProfilerInfo dataProfilerInfo) {
		List<DataProfilerInfo> dataProfilerInfoList = columnStatsRepo.findAll();
		for (int i = 0; i < dataProfilerInfoList.size(); i++) {
			if (dataProfilerInfoList.get(i).getFileName().equals(collectionName)) {
				dataProfilerInfo = dataProfilerInfoList.get(i);
				break;
			}
		}
		return dataProfilerInfo;
	}
}
