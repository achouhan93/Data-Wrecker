package com.data.wrecker.orchestrator.serviceimpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.data.wrecker.orchestrator.service.CallDataTypeServices;

@Service
@Transactional
public class CallDatatypeServiceImpl implements CallDataTypeServices{

	@Override
	public String callBooleanService(String fileName, int wreckingPercentage) {
		String url = "http://localhost:8086/dimension/booleanDatatypeDimensions?columnName="+fileName+"&wreckingPercentage="+wreckingPercentage;
		return  new RestTemplate().getForObject(url, String.class);
	}

	@Override
	public String callCharacterService(String fileName, int wreckingPercentage) {
		String url = "http://localhost:8088/dimension/characterDatatypeDimensions?fileName="+fileName+"&wreckingPercentage="+wreckingPercentage;
		return new RestTemplate().getForObject(url, String.class);
	}

	@Override
	public String callDateService(String fileName, int wreckingPercentage) {
		String url = "http://localhost:8087/dimension/dateDatatypeDimensions?fileName="+fileName+"&wreckingPercentage="+wreckingPercentage;
		return  new RestTemplate().getForObject(url, String.class);
	}

	@Override
	public String callStringService(String fileName, int wreckingPercentage) {
		String url = "http://localhost:8090/dimension/stringDatatypeDimensions/?fileName="+fileName+"&wreckingPercentage="+wreckingPercentage;
		return  new RestTemplate().getForObject(url, String.class);
	}

	@Override
	public String callIntegerService(String fileName, int wreckingPercentage) {
		// http://localhost:8089/integerDataType/integerDataTypeDecision?wreakingDataRecordPosition=25&collectionName=NationalNames_1
		String url = "http://localhost:8089/integerDataType/integerDataTypeDecision?wreakingDataRecordPosition="+wreckingPercentage+"&collectionName="+fileName;
		return  new RestTemplate().getForObject(url, String.class);
	}

	@Override
	public String callDecimalService(String fileName, int wreckingPercentage) {
		String url = "localhost:8097/decimalDataType/decimalDataTypeDecision?wreakingDataRecordPosition="+wreckingPercentage+"&collectionName="+fileName;
		return  new RestTemplate().getForObject(url, String.class);
	}

}
