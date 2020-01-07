package com.data.columnStatistics.service.impl;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.columnStatistics.model.ColumnStats;
import com.data.columnStatistics.model.DataProfilerInfo;
import com.data.columnStatistics.model.DatasetStats;
import com.data.columnStatistics.model.ProfilingInfoModel;
import com.data.columnStatistics.repository.ColumnStatsRepo;
import com.data.columnStatistics.service.MultiColumnStatisticsService;
import com.mongodb.BasicDBObject;
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
	private LinearRegressionServiceImpl LinearRegressionServiceImpl;
	
	private Mongo mongo;
	private DB db;
	ArrayList<Float> firstColumnData = new ArrayList<Float>();
	ArrayList<Float> secoundColumnData = new ArrayList<Float>();

	@Override
	public String multiColumnDataEvaluation(String collectionName) throws JSONException {
		DataProfilerInfo dataProfilerInfo = null;

		com.data.columnStatistics.model.ProfilingInfoModel profilingInfoModel = new ProfilingInfoModel();
		DatasetStats datasetStats = null;
		List<DatasetStats> datasetStatsList = null;
		ColumnStats columnStats = null;
		List<DataProfilerInfo> dataProfilerInfoList = columnStatsRepo.findAll();
		for (int i = 0; i < dataProfilerInfoList.size(); i++) {
			if (dataProfilerInfoList.get(i).getFileName().equals(collectionName)) {
				dataProfilerInfo = dataProfilerInfoList.get(i);
				break;
			}
		}
		ArrayList<String> numericaColumns = new ArrayList<String>();
		System.out.println("dataProfilerInfo" + dataProfilerInfo);
		for (int i = 0; i < dataProfilerInfo.getDatasetStats().size(); i++) {
			String dataTypeOfColumn = dataProfilerInfo.getDatasetStats().get(i).getProfilingInfo().getColumnDataType();
			String columnName = dataProfilerInfo.getDatasetStats().get(i).getColumnName();
			if (dataTypeOfColumn.equalsIgnoreCase("Integer") || dataTypeOfColumn.equalsIgnoreCase("Decimal")) {
				numericaColumns.add(columnName);
			}
		}
		System.out.println("numericaColumns" + numericaColumns);

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

		for (int j = 0; j < numericaColumns.size(); j++) {
			for (int k = 0; k < numericaColumns.size(); k++) {
				if (j == k) {
					for (int i = 0; i < dbList.length(); i++) {
						String firstColValue = dbList.getJSONObject(0).get(numericaColumns.get(j)).toString();
						Float firstColValueFloat = Float.parseFloat(firstColValue);
						firstColumnData.add(firstColValueFloat);
						String secoundColValue = dbList.getJSONObject(0).get(numericaColumns.get(k)).toString();
						Float secoundColValueFloat = Float.parseFloat(secoundColValue);
						secoundColumnData.add(secoundColValueFloat);
					}
					LinearRegressionServiceImpl.linearRegessionEvaluator(firstColumnData,secoundColumnData);
					
					/*
					 * for (int i=0; i<dbList.length(); i++) { String colValue =
					 * dbList.getJSONObject(0).get("Year").toString();
					 * firstColumnData.add(colValue); }
					 */
				}
			}
		}
		return null;
	}
}
