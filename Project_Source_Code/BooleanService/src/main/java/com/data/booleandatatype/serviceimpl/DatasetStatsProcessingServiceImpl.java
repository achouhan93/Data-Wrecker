package com.data.booleandatatype.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.booleandatatype.model.DataProfilerInfo;
import com.data.booleandatatype.model.DatasetStats;
import com.data.booleandatatype.model.Dimensions;
import com.data.booleandatatype.repository.DatasetStatsInfoRepository;
import com.data.booleandatatype.service.BooleanDataTypeService;
import com.data.booleandatatype.service.DatasetStatsProcessingService;
@Service
@Transactional
public class DatasetStatsProcessingServiceImpl implements DatasetStatsProcessingService{

	@Autowired
	private DatasetStatsInfoRepository datasetStatsRepo;
	@Autowired
	private BooleanDataTypeService booleanService;
	private List<DatasetStats> datasetStatsList;
	private List<Dimensions> dimensionsList;
	private List<DataProfilerInfo> dataProfilerInfoList;
	private DataProfilerInfo dataProfilerInfo;


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
			if(datasetStatsList.get(j).getProfilingInfo().getColumnDataType().equals("Boolean")) {
				dimensionsList = new ArrayList<Dimensions>();
				dimensionsList.add(booleanService.NullCheck(datasetStatsList.get(j),avgWreckingCount));
				dimensionsList.add(booleanService.AccuracyCheck(datasetStatsList.get(j),avgWreckingCount));
				dimensionsList.add(booleanService.ConsistencyCheck(datasetStatsList.get(j),avgWreckingCount));
				dimensionsList.add(booleanService.ValidityCheck(datasetStatsList.get(j),avgWreckingCount));
				dimensionsList.add(booleanService.UniquenessCheck(datasetStatsList.get(j), avgWreckingCount));
				datasetStatsList.get(j).setDimensionsList(dimensionsList);
			}
		}
		return datasetStatsList;
	}



}
