package com.data.chardatatype.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.chardatatype.model.DatasetStats;
import com.data.chardatatype.model.DimensionServices;
import com.data.chardatatype.model.Dimensions;
import com.data.chardatatype.repository.DatasetStatsInfoRepository;
import com.data.chardatatype.service.CharacterDataTypeService;
import com.data.chardatatype.service.DatasetStatsProcessingService;


@Service
@Transactional
public class DatasetStatsProcessingServiceImpl implements DatasetStatsProcessingService{

	@Autowired
	private DatasetStatsInfoRepository datasetStatsRepo;
	@Autowired
	private CharacterDataTypeService characterService;
	private List<DatasetStats> datasetStatsList;
	private DatasetStats datasetStats;
	private List<Dimensions> dimensionsList;
	@Override
	public List<Dimensions> getDimensionResults(String columnName) {
	
		DimensionServices dimensionServices = new DimensionServices();
		dimensionsList = new ArrayList<Dimensions>();
		datasetStatsList = datasetStatsRepo.findAll();
		datasetStats = getDatasetThroughColumnName(columnName,datasetStatsList);
		dimensionsList.add(characterService.NullCheck(datasetStats));
		dimensionsList.add(characterService.AccuracyCheck(datasetStats));
		dimensionsList.add(characterService.ConsistencyCheck(datasetStats));
		dimensionsList.add(characterService.ValidityCheck(datasetStats));
		dimensionServices.setDimensionsList(dimensionsList);
		updateDimensionList(datasetStats, dimensionsList);
		return dimensionsList;
	}
	
	private DatasetStats getDatasetThroughColumnName(String columnName, List<DatasetStats> datasetStatsList) {
		datasetStats = new DatasetStats();
		for(int i=0; i< datasetStatsList.size(); i++) {
			if(datasetStatsList.get(i).getColumnName().equals(columnName)) {
				datasetStats = datasetStatsList.get(i);
				break;
			}
		}
		return datasetStats;
	}
	
	private boolean updateDimensionList(DatasetStats updatdeDatasetStats, List<Dimensions> newDimensionsList) {
		DimensionServices dimensionServices = new DimensionServices();
		dimensionServices.setDimensionsList(newDimensionsList);
		updatdeDatasetStats.setDimensionServices(dimensionServices);
		datasetStatsRepo.save(updatdeDatasetStats);
		return false;
	}
	
	

}
