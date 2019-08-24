package com.data.wrecker.orchestrator.serviceimpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.data.wrecker.orchestrator.entity.DatasetDetails;
import com.data.wrecker.orchestrator.service.GetProfilerInfoFromServices;

@Service
@Transactional
public class GetProfilerInfoFromServicesImpl implements GetProfilerInfoFromServices{

	

	@Override
	public String callColumnDatatypePredictionService(String filename) {
		return new RestTemplate().getForObject("http://localhost:8082/columnDataTypePrediction/getDataTypeOfAColumns?collectionName="+filename, String.class);
	}

	@Override
	public String callColumnStatisticsService(String filename) {
		String resp = new RestTemplate().getForObject("http://localhost:8083/columnStatistics/getColumnStats?fileName="+filename, String.class);
		return resp;
	}

	@Override
	public DatasetDetails callPatternIdentificationService(String filename) {
		
		return  new RestTemplate().getForObject("http://localhost:8081/patternIdentification/getPossiblePatternsForData?fileName="+filename, DatasetDetails.class);
	}
	
}
