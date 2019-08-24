package com.data.wrecker.orchestrator.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.wrecker.orchestrator.entity.DataProfilerInfo;
import com.data.wrecker.orchestrator.entity.DatasetDetails;
import com.data.wrecker.orchestrator.entity.DatasetStats;
import com.data.wrecker.orchestrator.entity.Dimensions;
import com.data.wrecker.orchestrator.repository.DataProfilerInfoRepository;
import com.data.wrecker.orchestrator.service.CallAllMicroservices;
import com.data.wrecker.orchestrator.service.CallDataTypeServices;
import com.data.wrecker.orchestrator.service.GetProfilerInfoFromServices;

@Service
@Transactional
public class CallAllMicroservicesImpl implements CallAllMicroservices{

	@Autowired
	private GetProfilerInfoFromServices getProfilingServices;
	@Autowired
	private CallDataTypeServices callDatatypeService;
	@Autowired
	private DataProfilerInfoRepository dataProfilerInfoRepo;

	private static final Logger LOGGER = LogManager.getLogger();
	private static final String RESULT = "Success";
	
	@Override
	public String callDataprofilingServices(String fileName) {
		DatasetDetails datasetDetails;
		String collectionName = fileName;
		String result = "";
		LOGGER.info("Calling Pattern Identification service on collection "+fileName);
		datasetDetails = new DatasetDetails();

		datasetDetails = getProfilingServices.callPatternIdentificationService(collectionName);
		
		if(datasetDetails.getResult().equals(RESULT)) {
			
			LOGGER.info("Patterns are successfully identified Collection created name "+datasetDetails.getCollectionName());
			LOGGER.info("Calling Datatype Prediction Service");
			datasetDetails = new DatasetDetails();
			result = getProfilingServices.callColumnDatatypePredictionService(collectionName);
			LOGGER.info("Result after  Datatype Prediction Service "+result);
			if(result.equals(RESULT)) {
				
				LOGGER.info("Calling ColumnStatistics  Service"); 
				result = getProfilingServices.callColumnStatisticsService(collectionName);
				LOGGER.info("Result after ColumnStatistics Service "+result);
				if(result.equals(RESULT)) {
					
					LOGGER.info("ColumnStatistics Service Successful");
					LOGGER.info("Data profiling is Completed \n Now calling Datatype services ");
					result = callDatatypeServices(collectionName);
					LOGGER.info("Result after datatype services " + result);
					
				}else {
					
					LOGGER.info("ColumnStatistics Servvice has failed! ");
					result = "Failure in ColumnStatistics";
					
				}
				
			}else {
				
				LOGGER.info("Datatype Prediction Servvice has failed! ");
				result = "Failure in Datatype Prediction";
				
			}
			
		}else {
			
			LOGGER.info("PatternsIdentification Service has failed! ");
			result = "Failure in PatternsIdentification";
		}
		
		return result;
	}
	
	
	private String callDatatypeServices(String fileName) {
		
		int wreckPercentage = 20;
		String result = "";
		
		result = callDatatypeService.callBooleanService(fileName, wreckPercentage);
		
		if(result.equals(RESULT)) {
			LOGGER.info("Boolean service is Successful ");
			result = callDatatypeService.callDateService(fileName, wreckPercentage);
			
			if(result.equals(RESULT)) {
				
				LOGGER.info("Date service is Successful ");
				result = callDatatypeService.callCharacterService(fileName, wreckPercentage);
				if(result.equals(RESULT)) {
					
					LOGGER.info("Character service is Successful ");
					result = callDatatypeService.callIntegerService(fileName, wreckPercentage);
					if(result.equals(RESULT)) {
						
						LOGGER.info("Integer service is Successful ");
						
						/*result = callDatatypeService.callStringService(fileName);
						if(result.equals(RESULT)) {
							
						}else {
							result = "Failure";
						}*/
						
					} else {
						
						LOGGER.info("Integer service is UnSuccessful ");
						result = "Failure";
					}
					
				} else {
					
					LOGGER.info("Character service is UnSuccessful ");
					result = "Failure";
				}
			
			} else {
				
				LOGGER.info("Date service is UnSuccessful ");
				result = "Failure";
			}
			
			
		} else {
			LOGGER.info("Boolean service is UnSuccessful ");
			result = "Failure";
		}
		return result;
	}
	
	@Override
	public String callDimensionServices(String collectionName) {
		List<String> columnHeaders = new ArrayList<String>();
		DataProfilerInfo dataprofilerInfo = getDataprofileInfo(collectionName);
		for(int i =0; i < dataprofilerInfo.getDatasetStats().size(); i++ ) {
			columnHeaders.add(dataprofilerInfo.getDatasetStats().get(i).getColumnName());
		}
		List<DatasetStats> datasetStatsList = dataprofilerInfo.getDatasetStats();
		String result = "";
		for(int j =0; j< datasetStatsList.size(); j++) {
			if(datasetStatsList.get(j).getColumnName().equals(columnHeaders.get(j))) {
				List<Dimensions> dimensionList = new ArrayList<Dimensions>();
				dimensionList = getDimensionResults(datasetStatsList.get(j));
				result = callDimensionServicesForWrecking(dimensionList, columnHeaders.get(j));
				
			}			
		}
		
		return result;
	}
	
	private List<Dimensions> getDimensionResults(DatasetStats datasetStats) {	
		List<Dimensions> dimensionList = new ArrayList<Dimensions>();
		dimensionList = datasetStats.getDimensionList().getDimensionsList();
		return dimensionList;
	}
	
	private String callDimensionServicesForWrecking(List<Dimensions> dimensionList, String columnName) {
		String res="";
		for(int i=0; i< dimensionList.size(); i++) {
			if(dimensionList.get(i).isStatus()) {
				res = callDimension(dimensionList.get(i).getDimensionName(), columnName);
			}		
		}
		return res;
		
	}
	
	private String callDimension(String dimensionName, String columnName ) {
		String result = "";
		switch (dimensionName) {
		
		case "Completeness":
			LOGGER.info("Completeness Called");
			result = "Completeness";
			break;
			
		case "Uniqueness":
			LOGGER.info("Uniqueness Called");
			result = "Uniqueness";
			break;
		
		case "Consistency":
			LOGGER.info("Consistency Called");
			result = "Consistency";
			break;
		
		case "Accuracy":
			LOGGER.info("Accuracy Called");
			result = "Accuracy";
			break;	
		
		case "Validity":
			LOGGER.info("Validity Called");
			result = "Validity";
			break;	
			
		}
		
		return result;
	}
	private DataProfilerInfo getDataprofileInfo(String collectionName) {
		List<DataProfilerInfo> dataprofileInfoList = new ArrayList<DataProfilerInfo>();
		dataprofileInfoList = dataProfilerInfoRepo.findAll();
		DataProfilerInfo dataProfilerInfo = null;
		for(int i =0; i < dataprofileInfoList.size(); i++) {
			dataProfilerInfo = new DataProfilerInfo();
			if(dataprofileInfoList.get(i).getFileName().equals(collectionName)) {
				dataProfilerInfo = new DataProfilerInfo();
				dataProfilerInfo = dataprofileInfoList.get(i);
				break;
			}
	}
		return dataProfilerInfo;
}
	
	
	
	
}
