package com.data.patternidentification.dao.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.data.patternidentification.dao.PatternIdentificationDao;


@Repository
public class PatternIdentificationDaoImpl implements PatternIdentificationDao {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	public String getPatternidentificationData()
	{
		return null;
		
	}

}
