package com.data.columnStatistics.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.data.columnStatistics.dao.ColumnStatisticsDaoMongo;
import com.data.columnStatistics.model.ColumnStats;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

@Repository
public class ColumnSatisticsDaoMongoImpl implements ColumnStatisticsDaoMongo {

	@Override
	public List<String> getColumnValues(String dbName, String collectionName, String columnName) {
		
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase(dbName);
		MongoCollection<Document> collection = database.getCollection(collectionName);
		List<String> items = new ArrayList<>();
		try (MongoCursor<Document> cur = collection.find().iterator()) {
			while (cur.hasNext()) {
				items.add((String) cur.next().get(columnName).toString());
			}
		}
		mongoClient.close();
		return items;
	}

	@Override
	public void saveColumnStatistics(ColumnStats columnStatisticsModel, String dbName, String collectionName, String columnName) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase(dbName);
		String collectionNameToStoreStatistics=collectionName+columnName+"Statistics";
		boolean collectionExists=checkCollection(database,collectionNameToStoreStatistics);
		System.out.println("------------collectionNameToStore------------"+collectionNameToStoreStatistics);
		if (!collectionExists) {
			database.createCollection(collectionNameToStoreStatistics);
		}
		MongoCollection<Document> collectionToStoreStatistics = database.getCollection(collectionNameToStoreStatistics);
		Document doc = new Document("columnName", columnName)
				.append("rowCount", columnStatisticsModel.getRowCount())
				.append("nullCount", columnStatisticsModel.getNullCount())
				.append("distinctValueList", columnStatisticsModel.getDistinctValueList())
				.append("distinctCount", columnStatisticsModel.getDistinctCount())
				.append("frequencyOfColumnValuesMap", columnStatisticsModel.getFrequencyOfColumnValuesMap())
				.append("uniqueValuesList", columnStatisticsModel.getUniqueValuesList())
				.append("duplicateValuesList", columnStatisticsModel.getDuplicateValuesList())
				.append("uniqueCount",columnStatisticsModel.getUniqueCount())
				.append("duplicateCount",columnStatisticsModel.getDuplicateCount())
				.append("isPrimaryKey", columnStatisticsModel.isPrimaryKey())
				.append("maxLength", columnStatisticsModel.getMaxLength())
				.append("minLength", columnStatisticsModel.getMinLength())
				.append("averageLength", columnStatisticsModel.getAverageLength())
				.append("maxValue", columnStatisticsModel.getMaxValue())
				.append("minValue", columnStatisticsModel.getMinValue())
				.append("averageValue", columnStatisticsModel.getAverageValue())
				.append("minValueDecimal", columnStatisticsModel.getMinValueDecimal())
				.append("maxValueDecimal", columnStatisticsModel.getMaxValueDecimal())
				.append("averageValueDecimal", columnStatisticsModel.getAverageValueDecimal())
				.append("minDate", columnStatisticsModel.getMinDate())
				.append("maxDate", columnStatisticsModel.getMaxDate())
				.append("trueCount", columnStatisticsModel.getTrueCount())
				.append("falseCount", columnStatisticsModel.getFalseCount());
		
		collectionToStoreStatistics.insertOne(doc);
		mongoClient.close();
	}

	private boolean checkCollection(MongoDatabase database, String collectionNameToStoreStatistics) {
		MongoIterable <String> collection =  database.listCollectionNames();
	    for(String s : collection) {
	        if(s.equals(collectionNameToStoreStatistics)) {
	            return true;
	        }
	    }
		return false;
	}
}
