package com.data.wrecker.accuracyDimension.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.wrecker.accuracyDimension.model.DatasetStats;
import com.data.wrecker.accuracyDimension.model.PatternModel;
import com.data.wrecker.accuracyDimension.service.TypesOfAccuracyToBeEffected;

@Service
@Transactional
public class TypesOfAccuracyServiceImpl implements TypesOfAccuracyToBeEffected {

	private static final Logger LOGGER = LogManager.getLogger();
	private Random rand = new Random();

	@Override
	public JSONObject interChangedValues(JSONObject jsonObj, String columnName) {
		LOGGER.info("Interchanging values " + jsonObj.toString());

		Iterator columnNames = jsonObj.keys();
		List<String> columnHeaders = new ArrayList<String>();

		while (columnNames.hasNext()) {
			String key = (String) columnNames.next();
			columnHeaders.add(key);
		}
		for (int i = 0; i < columnHeaders.size(); i++) {
			if (columnHeaders.get(i).isEmpty() || columnHeaders.get(i).equals("_id")
					|| columnHeaders.get(i).equals(columnName)) {
				columnHeaders.remove(i);
			}
		}

		rand = new Random();
		int randomArrayIndex = rand.nextInt(columnHeaders.size());
		String randomColName = columnHeaders.get(randomArrayIndex);
		String randomColValue;

		try {
			String colValue = jsonObj.getString(columnName);
			randomColValue = jsonObj.getString(randomColName);
			jsonObj.put(columnName, randomColValue);
			jsonObj.put(randomColName, colValue);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObj;
	}

	@Override
	public String typosForValues(String colValue) {
		LOGGER.info("colValue " + colValue);
		if (!colValue.isEmpty()) {
			rand = new Random();
			int count = 0;
			if (colValue.length() > 1) {
				count = rand.nextInt(colValue.length() / 2) + 1;
			} else {
				count = 4;
			}

			String strings = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+{}:>?/.,;'[]";
			char[] chars = colValue.toCharArray();
			char[] stringsArray = strings.toCharArray();
			while (count > 0) {
				int num = rand.nextInt(stringsArray.length);
				int index = rand.nextInt(chars.length);
				chars[index] = stringsArray[num];
				count--;
			}
			return new String(chars);
		}
		return null;
	}

	@Override
	public String generateJunkValues(String colValue) {
		LOGGER.info("Generate Junk Values ");
		LOGGER.info("colValue " + colValue);
		if (!colValue.isEmpty()) {
			rand = new Random();
			String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+{}:>?/.,;'[] ";
			char[] characters = str.toCharArray();
			int randomIndex = rand.nextInt(characters.length);
			int subStringLength = rand.nextInt(colValue.length());
			return colValue.substring(0, subStringLength) + characters[randomIndex]
					+ colValue.substring(subStringLength);
		}
		LOGGER.info("colvalue is null");
		return "null";
	}

	@Override
	public String generateDates(String colValue) {
		LOGGER.info("Generating Dates");
		return null;
	}

	@Override
	public String shuffleString(String colValue) {
		LOGGER.info("Shuffle Strings");
		List<Character> characters = new ArrayList<Character>();
		for (char c : colValue.toCharArray()) {
			characters.add(c);
		}
		StringBuilder output = new StringBuilder(colValue.length());
		while (characters.size() != 0) {
			int randPicker = (int) (Math.random() * characters.size());
			output.append(characters.remove(randPicker));
		}
		return output.toString();
	}

	@Override
	public int convertIntToOppositeSign(int colValue) {
		return -colValue;
	}

	@Override
	public String addYearsToDate(DatasetStats datasetStats, String date) {
		String dateFormatPattern = "";
		rand = new Random();
		int yearsTobeAdded = rand.nextInt(1000) + 100;

		List<PatternModel> patternsIdentified = datasetStats.getProfilingInfo().getPatternsIdentified();
		for (int i = 0; i < patternsIdentified.size(); i++) {
			if (Pattern.matches(date, patternsIdentified.get(i).getPattern())) {
				dateFormatPattern = patternsIdentified.get(i).getPattern();
				SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern);
				Calendar c = Calendar.getInstance();
				try {
					c.setTime(sdf.parse(date));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.add(Calendar.YEAR, yearsTobeAdded);
				date = sdf.format(c.getTime());
			}
		}
		return date;
	}

}
