package com.data.datedatatype.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.datedatatype.model.DatasetStats;
import com.data.datedatatype.model.DimensionsInfoModel;
import com.data.datedatatype.model.Dimensions;
import com.data.datedatatype.repository.DatasetStatsInfoRepository;
import com.data.datedatatype.service.DatasetStatsProcessingService;
import com.data.datedatatype.service.DateDataTypeService;

@Service
@Transactional
public class DatasetStatsProcessingServiceImpl implements DatasetStatsProcessingService{

	@Autowired
	private DatasetStatsInfoRepository datasetStatsRepo;
	@Autowired
	private DateDataTypeService dateService;
	private List<DatasetStats> datasetStatsList;
	private DatasetStats datasetStats;
	private List<Dimensions> dimensionsList;
	
	@Override
	public List<Dimensions> getDimensionResults(String columnName) {
	
		DimensionsInfoModel dimensionServices = new DimensionsInfoModel();
		dimensionsList = new ArrayList<Dimensions>();
		datasetStatsList = datasetStatsRepo.findAll();
		datasetStats = getDatasetThroughColumnName(columnName,datasetStatsList);
		dimensionsList.add(dateService.NullCheck(datasetStats));
		dimensionsList.add(dateService.AccuracyCheck(datasetStats));
		dimensionsList.add(dateService.ConsistencyCheck(datasetStats));
		dimensionsList.add(dateService.ValidityCheck(datasetStats));
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
		DimensionsInfoModel dimensionServices = new DimensionsInfoModel();
		dimensionServices.setDimensionsList(newDimensionsList);
		updatdeDatasetStats.setDimensionInfo(dimensionServices);
		datasetStatsRepo.save(updatdeDatasetStats);
		return false;
	}
	
	

}
