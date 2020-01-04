package com.data.columnStatistics.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ClassicalRegressionBooleanServiceImpl {

	public boolean ClassicalRegressionBooleanEvaluator(ArrayList<String> booleanColumnData,
			ArrayList<String> evaluatingColumnData) {
		HashMap<String, Integer> trueValueArray = new HashMap<String, Integer>();
		HashMap<String, Integer> falseValueArray = new HashMap<String, Integer>();
		for (int i = 0; i < booleanColumnData.size(); i++) {
			String currentBoolValue = null;
			String currentCorespondingValue = null;
			currentBoolValue = booleanColumnData.get(i);
			currentCorespondingValue = evaluatingColumnData.get(i);
			Integer trueMatchedValueCount = 1;
			Integer falseMatchedValueCount = 1;
			int presentInBoth = 0 ;
			//System.out.println("currentBoolValue: "+currentBoolValue+" currentCorespondingValue: "+currentCorespondingValue);
			if (trueValueArray.containsKey(currentCorespondingValue) && currentBoolValue.equalsIgnoreCase("True")) {
				for (Map.Entry<String, Integer> entry : trueValueArray.entrySet()) {
					String key = entry.getKey();
					Integer value = entry.getValue();
					if (key.equals(currentCorespondingValue)) {
						trueMatchedValueCount = value + 1;
					}
				}
			}
			if (falseValueArray.containsKey(currentCorespondingValue) && currentBoolValue.equalsIgnoreCase("False")) {
				for (Map.Entry<String, Integer> entry : falseValueArray.entrySet()) {
					String key = entry.getKey();
					Integer value = entry.getValue();
					if (key.equals(currentCorespondingValue)) {
						falseMatchedValueCount = value + 1;
					}
				}
			}
			/*
			 * else if (falseValueArray.containsKey(currentCorespondingValue) &&
			 * trueValueArray.containsKey(currentCorespondingValue)) {
			 * trueValueArray.remove(currentCorespondingValue);
			 * falseValueArray.remove(currentCorespondingValue); presentInBoth++; }
			 */
			if (currentBoolValue.equalsIgnoreCase("True") && presentInBoth != 1) {
				trueValueArray.put(currentCorespondingValue, trueMatchedValueCount);
			} else if (currentBoolValue.equalsIgnoreCase("False") && presentInBoth != 1) {
				falseValueArray.put(currentCorespondingValue, falseMatchedValueCount);
			} 

		}
		
		
		for (int i =0; i<evaluatingColumnData.size(); i++)
		{
			if (falseValueArray.containsKey(evaluatingColumnData.get(i)) && trueValueArray.containsKey(evaluatingColumnData.get(i)) )
			{
				trueValueArray.remove(evaluatingColumnData.get(i));
				falseValueArray.remove(evaluatingColumnData.get(i));
			}
		}
	
		
		int totalTrueSum = 0;
		int totalFalseSum = 0;
		for (Map.Entry<String, Integer> entry : trueValueArray.entrySet()) {
			Integer trueValue = entry.getValue();
			totalTrueSum = totalTrueSum + trueValue;
		}
		for (Map.Entry<String, Integer> entry : trueValueArray.entrySet()) {
			Integer falseValue = entry.getValue();
			totalFalseSum = totalFalseSum + falseValue;
		}
		int totalMatchedRecordCount = totalTrueSum + totalFalseSum;
		if (totalMatchedRecordCount > booleanColumnData.size() / 2) {
			return true;
		}
		return false;
	}

}
