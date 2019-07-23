package com.data.datedatatype.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.datedatatype.model.DataProfilerInfo;
import com.data.datedatatype.model.DatasetStats;
import com.data.datedatatype.model.DimensionInfoModel;
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
	private List<DataProfilerInfo> dataProfilerInfoList;
	private DataProfilerInfo dataProfilerInfo;
	private List<Dimensions> dimensionsList;
	
	@Override
	public List<Dimensions> getDimensionResults(String fileName,int wreckingPercentage) {
	
		
		dimensionsList = new ArrayList<Dimensions>();
		dataProfilerInfoList = datasetStatsRepo.findAll();
		for(int i =0; i < dataProfilerInfoList.size(); i++) {
			if(dataProfilerInfoList.get(i).getFileName().equals(fileName)) {
				dataProfilerInfo = new DataProfilerInfo();
				dataProfilerInfo = dataProfilerInfoList.get(i);
				break;				
			}
		}
		
		datasetStatsList =getDimensionResults(dataProfilerInfo.getDatasetStats(),wreckingPercentage);
		dataProfilerInfo.setDatasetStats(datasetStatsList);
		
		
		updateDimensionList(dataProfilerInfo);
		return null;
	}

	
	private boolean updateDimensionList(DataProfilerInfo dataProfilerInfo) {
		if(datasetStatsRepo.save(dataProfilerInfo) != null) {
			return true;	
		}else {
			return false;
		}
		
		
	}
	
	private List<DatasetStats> getDimensionResults(List<DatasetStats> datasetStatsList, int wreckingPercentage) {
		DimensionInfoModel dimensionServices = new DimensionInfoModel();
		for(int j =0; j< datasetStatsList.size(); j++) {
			if(datasetStatsList.get(j).getProfilingInfo().getColumnDataType().equals("Date")) {
				dimensionsList.add(dateService.NullCheck(datasetStatsList.get(j),wreckingPercentage));
				dimensionsList.add(dateService.AccuracyCheck(datasetStatsList.get(j),wreckingPercentage));
				dimensionsList.add(dateService.ConsistencyCheck(datasetStatsList.get(j),wreckingPercentage));
				dimensionsList.add(dateService.ValidityCheck(datasetStatsList.get(j),wreckingPercentage));
				dimensionServices = new DimensionInfoModel();
				dimensionServices.setDimensionsList(dimensionsList);
				datasetStatsList.get(j).setDimentionList(dimensionServices);
			}
		}
		return datasetStatsList;
	}
	
	

}
