package com.data.datawreakerinterface.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCodes {

	public static final String SOMETHING_WENT_WRONG = "PI0001 " + HttpStatus.SERVICE_UNAVAILABLE.value()
			+ ": Something went wrong : Consider renaming dataset name and try again";
	
}	