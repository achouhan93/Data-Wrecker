package com.data.wrecker.orchestrator.serviceimpl;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.data.wrecker.orchestrator.service.CallDataTypeServicesParallel;


@Service
@Transactional
public class CallDatatypeServicesParallelImpl implements CallDataTypeServicesParallel{

	@Async
	@Override
	public CompletableFuture<String> callBooleanService(String fileName, int wreckingPercentage) {
		String url = "http://localhost:8086/dimension/booleanDatatypeDimensions?columnName="+fileName+"&wreckingPercentage="+wreckingPercentage;
		String response =   new RestTemplate().getForObject(url, String.class);
		return CompletableFuture.completedFuture(response);
	}

	@Async
	@Override
	public CompletableFuture<String> callCharacterService(String fileName, int wreckingPercentage) {
		String url = "http://localhost:8088/dimension/characterDatatypeDimensions?fileName="+fileName+"&wreckingPercentage="+wreckingPercentage;
		String response =   new RestTemplate().getForObject(url, String.class);
		return CompletableFuture.completedFuture(response);
	}

	@Async
	@Override
	public CompletableFuture<String> callDateService(String fileName, int wreckingPercentage) {
		String url = "http://localhost:8087/dimension/dateDatatypeDimensions?fileName="+fileName+"&wreckingPercentage="+wreckingPercentage;
		String response =   new RestTemplate().getForObject(url, String.class);
		return CompletableFuture.completedFuture(response);
	}

	@Async
	@Override
	public CompletableFuture<String> callStringService(String fileName, int wreckingPercentage) {
		String url = "http://localhost:8090/dimension/stringDatatypeDimensions/?fileName="+fileName+"&wreckingPercentage="+wreckingPercentage;
		String response =   new RestTemplate().getForObject(url, String.class);
		return CompletableFuture.completedFuture(response);
	}

	@Async
	@Override
	public CompletableFuture<String> callIntegerService(String fileName, int wreckingPercentage) {
		String url = "http://localhost:8089/integerDataType/integerDataTypeDecision?wreckingPercentage="+wreckingPercentage+"&collectionName="+fileName;
		String response =   new RestTemplate().getForObject(url, String.class);
		return CompletableFuture.completedFuture(response);
	}

	@Async
	@Override
	public CompletableFuture<String> callDecimalService(String fileName, int wreckingPercentage) {
		String url = "http://localhost:8097/decimalDataType/decimalDataTypeDecision?wreckingPercentage="+wreckingPercentage+"&collectionName="+fileName;
		String response =   new RestTemplate().getForObject(url, String.class);
		return CompletableFuture.completedFuture(response);
	}

}
