package com.javaspringrestapi.demo;

import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ReadJSON {
	
	//Read JSON file
	public Map<String, Map<String, Object>> read(String path) throws JsonMappingException, JsonProcessingException {                 
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
		String stringInput = streamToString(inputStream);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Map<String, Object>> jsonMap = objectMapper.readValue(stringInput, new TypeReference<Map<String, Map<String, Object>>>(){});
		return jsonMap;
	}
	
	//Parse input stream to a string
	public String streamToString(InputStream inputStream) {
		try (Scanner scanner = new Scanner(inputStream).useDelimiter("\\A")) {
			String result = scanner.hasNext() ? scanner.next() : "";
			scanner.close();
			return result;
		}
	}
	
	//Parse map to a string
	public String mapToString(LinkedHashMap<?, ?> jsonMap) {
	    String genreString = new Gson().toJson(jsonMap, Map.class);
	    return genreString;
	}
	
	//Parse a string to a string array
	public String[] stringToStringArray(String jsonString) {
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
		String[] genreArray = new String[jsonObject.size()];
		int index = 0;
		for (String key : jsonObject.keySet()) {
		    String value = jsonObject.get(key).getAsString();
		    genreArray[index++] = value;
		}
		return genreArray;
	}
	
	//Parse a string array to a string
	public String stringArrayToString(String[] stringArray) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(Arrays.asList(stringArray));
		return jsonString;
	}
	
}
