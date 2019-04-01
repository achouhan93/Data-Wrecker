package com.data.chardatatype.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CharacterServiceController {
	String jsonData = "";
	JSONObject jsonObject = new JSONObject(jsonData);
	int avgWrecking = getAvgWrecking(20);
	
	
}
