package com.data.wrecker.orchestrator.serviceimpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.data.wrecker.orchestrator.entity.DataProfilerInfo;
import com.data.wrecker.orchestrator.service.GetProfilerInfoFromServices;

@Service
@Transactional
public class GetProfilerInfoFromServicesImpl implements GetProfilerInfoFromServices{

	@Override
	public DataProfilerInfo callPatternIdentificationService(String filename) {
		return  new RestTemplate().getForObject("http://localhost:8086/patternIdentification/getPossiblePatternsForData?fileName="+filename, DataProfilerInfo.class);
	}

	@Override
	public DataProfilerInfo callColumnDatatypePredictionService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String callColumnStatisticsService(String filename) {
		// TODO Auto-generated method stub
		String resp = new RestTemplate().getForObject("http://localhost:8080/columnStatistics/getColumnStats?fileName="+filename, String.class);
		return resp;
	}
	
	

}
