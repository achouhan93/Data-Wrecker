package com.data.wrecker.orchestrator.service;

import java.util.concurrent.CompletableFuture;

public interface CallDataTypeServicesParallel {

	
public CompletableFuture<String> callBooleanService(String fileName, int wreckingPercentage);
	
	public CompletableFuture<String> callCharacterService(String fileName,int wreckingPercentage);
	
	public CompletableFuture<String> callDateService(String fileName,int wreckingPercentage);
	
	public CompletableFuture<String> callStringService(String fileName,int wreckingPercentage);

	public CompletableFuture<String> callIntegerService(String fileName, int wreckingPercentage);

	public CompletableFuture<String> callDecimalService(String fileName, int wreckingPercentage);
	
}
