package com.example.mainorchestrator.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.mainorchestrator.entity.DatasetDetails;
import com.example.mainorchestrator.service.LoadFileIntoMongo;

@Service
@Transactional
public class LoadFileIntoMongoImpl implements LoadFileIntoMongo{

	@Override
	public DatasetDetails loadFileIntoMongo() {
		return  new RestTemplate().getForObject("http://localhost:8080/dataWreakerInterface/dataPopulation", DatasetDetails.class);
	}

	
}
