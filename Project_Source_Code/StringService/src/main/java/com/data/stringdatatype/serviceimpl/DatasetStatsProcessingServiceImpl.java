package com.data.stringdatatype.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.stringdatatype.model.DataProfilerInfo;
import com.data.stringdatatype.model.DatasetStats;
import com.data.stringdatatype.model.DimensionInfoModel;
import com.data.stringdatatype.model.Dimensions;
import com.data.stringdatatype.repository.DatasetStatsInfoRepository;
import com.data.stringdatatype.service.DatasetStatsProcessingService;
import com.data.stringdatatype.service.StringDataTypeService;

@Service
@Transactional
public class DatasetStatsProcessingServiceImpl implements DatasetStatsProcessingService{

	@Autowired
	private DatasetStatsInfoRepository datasetStatsRepo;
	@Autowired
	private StringDataTypeService dateService;
	private List<DatasetStats> datasetStatsList;
	private List<DataProfilerInfo> dataProfilerInfoList;
	private DataProfilerInfo dataProfilerInfo;
	private List<Dimensions> dimensionsList;
	
	@Override
	public String getDimensionResults(String fileName, int wreckingPercentage) {
		
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
		
		if(updateDimensionList(dataProfilerInfo)) {
			return "Success";
		}else {
			return "Fail";
		}
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
		
		int columnCount = datasetStatsList.size();
		for(int j =0; j< datasetStatsList.size(); j++) {
			if(datasetStatsList.get(j).getProfilingInfo().getColumnDataType().equals("String")) {
				dimensionsList = new ArrayList<Dimensions>();
				dimensionsList.add(dateService.NullCheck(datasetStatsList.get(j),wreckingPercentage, columnCount));
				dimensionsList.add(dateService.AccuracyCheck(datasetStatsList.get(j),wreckingPercentage, columnCount));
				dimensionsList.add(dateService.ConsistencyCheck(datasetStatsList.get(j),wreckingPercentage, columnCount));
				dimensionsList.add(dateService.ValidityCheck(datasetStatsList.get(j),wreckingPercentage, columnCount));
				dimensionsList.add(dateService.UniquenessCheck(datasetStatsList.get(j), wreckingPercentage, columnCount));
				dimensionServices = new DimensionInfoModel();
				dimensionServices.setDimensionsList(dimensionsList);
				datasetStatsList.get(j).setDimensionsList(dimensionsList);
			}
		}
		return datasetStatsList;
	}

}
