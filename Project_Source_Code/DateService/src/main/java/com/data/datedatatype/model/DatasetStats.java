package com.data.datedatatype.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection = "DatasetStats")
public class DatasetStats {
	@Id
	private ObjectId  _id;
	private String columnName;
	private PropertyModel propertyModel;
	private String columnDataType;
	private ColumnStats columnStats;
	private DimensionServices dimensionServices;

}
