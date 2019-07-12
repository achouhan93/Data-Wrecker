package com.data.patternidentification.exception;

public class PatternIdentificationException extends Exception {

	private static final long serialVersionUID = -1109307894927940472L;

	public PatternIdentificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public PatternIdentificationException(String message) {
		super(message);
	}
}
