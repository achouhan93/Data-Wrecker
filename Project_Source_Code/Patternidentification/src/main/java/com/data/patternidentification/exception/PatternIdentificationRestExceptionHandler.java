package com.data.patternidentification.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.data.patternidentification.model.PatternIdentificationResponseModel;

@RestControllerAdvice
public class PatternIdentificationRestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(PatternIdentificationException.class)
	public ResponseEntity<Object> defaultExceptionHandler(Exception exception, WebRequest request) {
		String error = exception.getMessage();
		PatternIdentificationResponseModel errorResponseModel = new PatternIdentificationResponseModel();
		errorResponseModel.setErrorMessage(ExceptionUtils.getErrorMessageFromError(error));
		return handleExceptionInternal(exception, errorResponseModel, new HttpHeaders(),
				ExceptionUtils.getHTTPStatusCode(error), request);

	}

}
