package com.data.datedatatype.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.datedatatype.serviceimpl.DateDataTypeImpl;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;


@RestController
public class DateDataTypeController {
	
	
	DateDataTypeImpl dateService;
	
	
	
	
	  @GetMapping("/dateDimension") 
	  public String getDimensions() { 
	JSONObject jsonObject = new JSONObject();
	  
		dateService = new DateDataTypeImpl();
	  jsonObject.append("Accuracy", dateService.AccuracyCheck());
	  jsonObject.append("Completeness", dateService.NullCheck());
	  jsonObject.append("Validity", dateService.ValidityCheck());
	  jsonObject.append("Consostency", dateService.ConsistencyCheck());
	  
	  return "jdhdbkjd"+jsonObject.toString();
	  
	  }
	 
	
	
	
	@RequestMapping("/getAll")
	public String data() {
		String data = "{\r\n" + 
				"\"profilierinfo\":{\r\n" + 
				"	\"totalrowcount\":100,\r\n" + 
				"	\"nullcount\":20,\r\n" + 
				"	\"distinctount\": 4,\r\n" + 
				"	\"uniquecount\": 0,\r\n" + 
				"	\"duplicate\": 50,\r\n" + 
				"	\"distinctvaluelist\":[\r\n" + 
				"	{\r\n" + 
				"	\"value\": \"1\",\r\n" + 
				"	\"valuecount\":45\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"	\"value\":\"0\",\r\n" + 
				"	\"valuecount\":40\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"	\"value\":\"trr\",\r\n" + 
				"	\"valuecount\":10\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"	\"value\":\"fll\",\r\n" + 
				"	\"valuecount\":5\r\n" + 
				"	}\r\n" + 
				"	],\r\n" + 
				"	\"minlength\":1,\r\n" + 
				"	\"maxlength\":3,\r\n" + 
				"	\"avglength\":1,\r\n" + 
				"	\"minvalue\":0,\r\n" + 
				"	\"maxvalue\":1,\r\n" + 
				"	\"avgvalue\":1,\r\n" + 
				"	\"truecount\":1,\r\n" + 
				"	\"falsecount\":0	\r\n" + 
				"	},\r\n" + 
				"	\"regexinfo\":[\r\n" + 
				"	{\r\n" + 
				"	\"regexpattren\":\"xxyydd\",\r\n" + 
				"	\"regexcount\":3\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"	\"regexpattren\":\"ffbbhh\",\r\n" + 
				"	\"regexcount\":6\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"	\"regexpattren\":\"hojj\",\r\n" + 
				"	\"regexcount\":6\r\n" + 
				"	}\r\n" + 
				"	]\r\n" + 
				"}";
		
		JSONObject jsonObject = new JSONObject(data);
		
		return jsonObject.toString(); 
	}	
}
