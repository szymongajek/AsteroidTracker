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
import com.sz.asteroid.pojos.NeoFeedSingleDateResult;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Asteroid tracker is a spring boot application, storing information about Near Earth Objects obtained using NASA REST service for 
 * https://api.nasa.gov/api.html#NeoWS
 * @author sz
 *
 */
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

	static NeoFeedSingleDateResult callNasaApiForNeoFeed() throws IOException, JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(NASA_FEED_WS_URL, String.class);

		return extractsNeosList(response);
	}

	/**
	 * parsowanie opdpowiedzi zeby dostac liste NEOs
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	static NeoFeedSingleDateResult extractsNeosList(ResponseEntity<String> response)
			throws IOException, JsonProcessingException {
		

		ObjectMapper jsonMapper = new ObjectMapper();
		// jackson-datatype-jsr310 dodany w dep. + rejestracja tutaj w celu
		// serializacji/deserializacji do java 8 date api
		jsonMapper.registerModule(new JavaTimeModule());
		
		JsonNode root = jsonMapper.readTree(response.getBody());
		ArrayList<JsonNode> tablesOfObj = new ArrayList<>();
//		tablica obeiktow dla roznych dat - w tym przypadku jest jedna data
		String todayDate = root.get(KEY_NEAR_EARTH_OBJECTS).fieldNames().next();
		
		ArrayNode arrnode = (ArrayNode) root.get(KEY_NEAR_EARTH_OBJECTS).get(todayDate);

		List<NEO> listOfNEOs = new ArrayList<>();
		arrnode.forEach(jsonArrEl -> {
			try {
				listOfNEOs.add(jsonMapper.treeToValue(jsonArrEl, NEO.class));
			} catch (JsonProcessingException e) {
				LOGGER.error("blad podczas tworzenia obiektu NEO:"+jsonArrEl,e);
			}
		});
		
		LocalDate feedDate = LocalDate.parse(todayDate, DateTimeFormatter.ISO_LOCAL_DATE);
		
		listOfNEOs.forEach(n -> LOGGER.info("NEOs: " + n.toString()));
		
		return new NeoFeedSingleDateResult(feedDate, listOfNEOs);
	}
}
