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
		String url = "http://localhost:8082/dimension/booleanDatatypeDimensions?columnName="+fileName+"&wreckingPercentage="+wreckingPercentage;
		return  new RestTemplate().getForObject(url, String.class);
	}

	@Override
	public String callCharacterService(String fileName, int wreckingPercentage) {
		String url = "http://localhost:8081/dimension/characterDatatypeDimensions?fileName="+fileName+"&wreckingPercentage="+wreckingPercentage;
		return new RestTemplate().getForObject(url, String.class);
	}

	@Override
	public String callDateService(String fileName, int wreckingPercentage) {
		String url = "http://localhost:8083/dimension/dateDatatypeDimensions?fileName="+fileName+"&wreckingPercentage="+wreckingPercentage;
		return  new RestTemplate().getForObject(url, String.class);
	}

	@Override
	public String callStringService(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

}
