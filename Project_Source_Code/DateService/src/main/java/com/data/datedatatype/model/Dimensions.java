package com.data.datedatatype.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Dimensions {

	private String dimensionName;
	private boolean status;
	private String reason;
	private int remainingWreakingCount;
}
