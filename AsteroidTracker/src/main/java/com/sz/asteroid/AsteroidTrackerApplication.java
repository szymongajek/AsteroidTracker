package com.sz.asteroid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sz.asteroid.pojos.NEO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class AsteroidTrackerApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(AsteroidTrackerApplication.class);

	private static final String NASA_FEED_WS_URL = "https://api.nasa.gov/neo/rest/v1/feed/today?detailed=false&api_key=DEMO_KEY";;
	private static final String KEY_NEAR_EARTH_OBJECTS = "near_earth_objects";

	
	public static void main(String[] args) {

		LOGGER.info("before");
		SpringApplication.run(AsteroidTrackerApplication.class, args);
		LOGGER.info("after");
	}

	static List<NEO> callNasaApiForNeoFeed() throws IOException, JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(NASA_FEED_WS_URL, String.class);

		List<NEO> listOfNEOs = extractsNeosList(response);
		
		return listOfNEOs;
	}

	/**
	 * parsowanie opdpowiedzi zeby dostac liste NEOs
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	static List<NEO> extractsNeosList(ResponseEntity<String> response)
			throws IOException, JsonProcessingException {
		

		ObjectMapper jsonMapper = new ObjectMapper();
		// jackson-datatype-jsr310 dodany w dep. + rejestracja tutaj w celu
		// serializacji/deserializacji do java 8 date api
		jsonMapper.registerModule(new JavaTimeModule());
		
		JsonNode root = jsonMapper.readTree(response.getBody());
		ArrayList<JsonNode> tablesOfObj = new ArrayList<>();
//		tablica obeiktow dla roznych dat - w tym przypadku jest jedna data
		root.get(KEY_NEAR_EARTH_OBJECTS).forEach(n -> tablesOfObj.add(n));
		ArrayNode arrnode = (ArrayNode) tablesOfObj.get(0);

		List<NEO> listOfNEOs = new ArrayList<>();
		arrnode.forEach(jsonArrEl -> {
			try {
				listOfNEOs.add(jsonMapper.treeToValue(jsonArrEl, NEO.class));
			} catch (JsonProcessingException e) {
				LOGGER.error("blad podczas tworzenia obiektu NEO:"+jsonArrEl,e);
			}
		});
		
		listOfNEOs.forEach(n -> LOGGER.info("NEOs: " + n.toString()));
		return listOfNEOs;
	}
}
