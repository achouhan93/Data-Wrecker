package com.data.datawreakerinterface.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionUtils {

	public static String getErrorMessageFromError(String string) {
		return string.substring(11).trim();
	}

	public static HttpStatus getHTTPStatusCode(String string) {
		return HttpStatus.valueOf(Integer.parseInt(string.substring(7, 10).trim()));
	}
}
