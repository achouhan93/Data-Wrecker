package com.data.datedatatype.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.datedatatype.model.DataProfilerInfo;
import com.data.datedatatype.model.DatasetStats;
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
	public String getDimensionResults(String fileName,int wreckingPercentage) {

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

		int totalRowCount = datasetStatsList.get(0).getProfilingInfo().getColumnStats().getRowCount();
		int avgWreckingCount = (totalRowCount * wreckingPercentage) / (100 * 4 * datasetStatsList.size());

		for(int j =0; j< datasetStatsList.size(); j++) {
			dimensionsList = new ArrayList<Dimensions>();
			if(datasetStatsList.get(j).getProfilingInfo().getColumnDataType().equals("Date")) {
				//dimensionsList.add(dateService.NullCheck(datasetStatsList.get(j),avgWreckingCount));
				dimensionsList.add(dateService.AccuracyCheck(datasetStatsList.get(j),avgWreckingCount));
				dimensionsList.add(dateService.ConsistencyCheck(datasetStatsList.get(j),avgWreckingCount));
				dimensionsList.add(dateService.ValidityCheck(datasetStatsList.get(j),avgWreckingCount));
				dimensionsList.add(dateService.UniquenessCheck(datasetStatsList.get(j), avgWreckingCount));
				datasetStatsList.get(j).setDimensionsList(dimensionsList);
			}
		}
		return datasetStatsList;
	}



}
