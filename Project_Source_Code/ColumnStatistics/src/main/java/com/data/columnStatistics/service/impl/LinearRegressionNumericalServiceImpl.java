package com.data.columnStatistics.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LinearRegressionNumericalServiceImpl {

	   private ArrayList<Float> Xdata;
	    private ArrayList<Float> YData;
	    private Float result1;
	    private Float result2;
	        
	    public boolean linearRegessionNumericalEvaluator(ArrayList<Float> firstColumnData, ArrayList<Float> secoundColumnData) {
	    	int predictionMatchCount=0;
	    	LinearRegressionNumericalServiceImpl linearRegressionClassifier = new LinearRegressionNumericalServiceImpl( firstColumnData , secoundColumnData );
	    	for (int i=0; i<firstColumnData.size();i++)
	    	{
	    	Float predictionedValueBasedOnValueProvided = linearRegressionClassifier.predictValue( firstColumnData.get(i), i);
	    	float predictionedValueBasedOnValueProvidedRoundupTo2 = 0.0F;
	    	predictionedValueBasedOnValueProvidedRoundupTo2 = round(predictionedValueBasedOnValueProvided,2);
	    	//input and predicted value matched
	    	if(predictionedValueBasedOnValueProvidedRoundupTo2 == secoundColumnData.get(i))
	    	{
	    		predictionMatchCount++;
	    		
	    	}
	    	
	    	}
	    	if(predictionMatchCount > (2*firstColumnData.size()/3))
    		{
    		return true;
    		}
	    	return false;
		}

	    public LinearRegressionNumericalServiceImpl (ArrayList xdata, ArrayList YData) {
	        Xdata = xdata;
	        this.YData = YData;
	    }

	    public Float predictValue ( Float inputValue, int cnt ) {
	        Float X1 = Xdata.get( cnt ) ;
	        Float Y1 = YData.get( cnt ) ;
	        Float Xmean = getXMean( Xdata ) ;
	        Float Ymean = getYMean( YData ) ;
	        Float lineSlope = getLineSlope( Xmean , Ymean , X1 , Y1 ) ;
	        Float YIntercept = getYIntercept( Xmean , Ymean , lineSlope ) ;
	        Float prediction = ( lineSlope * inputValue ) + YIntercept ;
	        return prediction ;
	    }

	    public Float getLineSlope (Float Xmean, Float Ymean, Float X1, Float Y1) {
	        float num1 = X1 - Xmean;
	        float num2 = Y1 - Ymean;
	        float denom = (X1 - Xmean) * (X1 - Xmean);
	        return (num1 * num2) / denom;
	    }

	    public float getYIntercept (Float Xmean, Float Ymean, Float lineSlope) {
	        return Ymean - (lineSlope * Xmean);
	    }

	    public Float getXMean (ArrayList<Float> Xdata) {
	        result1 = 0.0f ;
	        for (Integer i = 0; i < Xdata.size(); i++) {
	            result1 = result1 + Xdata.get(i);
	        }
	        return result1;
	    }

	    public Float getYMean (ArrayList<Float> Ydata) {
	        result2 = 0.0f ;
	        for (Integer i = 0; i < Ydata.size(); i++) {
	            result2 = result2 + Ydata.get(i);
	        }
	        return result2;
	    }

	   
	    public static float round(float number, int decimalPlace) {
			BigDecimal bd = new BigDecimal(number);
			bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
			return bd.floatValue();
		}
	    
	

}
