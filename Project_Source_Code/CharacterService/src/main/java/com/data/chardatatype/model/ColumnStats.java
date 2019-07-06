package com.data.chardatatype.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ColumnStats {

	private int rowCount;
	private int nullCount;
	private List<String> distinctValueList;
	private int distinctCount;
	private Map<String, Long> frequencyOfColumnValuesMap;
	private List<String> uniqueValuesList;
	private List<String> duplicateValuesList;
	private int uniqueCount;
	private int duplicateCount;
	boolean isPrimaryKey;
	private int maxLength;
	private int minLength;
	private int averageLength;
	private int maxValue;
	private int minValue;
	private int averageValue;
	private double minValueDecimal;
	private double maxValueDecimal;
	private double averageValueDecimal;
	private LocalDate minDate;
	private LocalDate maxDate;
	private long trueCount;
	private long falseCount;
	
}
