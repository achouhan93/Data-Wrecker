package com.data.wrecker.consistencydimension.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangesLog {

	private String columnName;
	private String oldValue;
	private String newValue;
	private String oid;
	private String dimensionName;
}
