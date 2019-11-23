package com.data.integerdatatypeservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Dimensions {

	String dimensionName;
	boolean status;
	String reason;
	int remainingWreakingCount;
}
