package com.sz.asteroid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sz.ateroid.pojos.NEO;

@RestController
@RequestMapping(value = "/restNEO")
class FeedService {

	private static final String KEY_NEAR_EARTH_OBJECTS = "near_earth_objects";
	private static Logger LOGGER = LogManager.getLogger(FeedService.class);
	private String NASA_FEED_WS_URL = "https://api.nasa.gov/neo/rest/v1/feed/today?detailed=false&api_key=DEMO_KEY";;

	@RequestMapping(value = "/feed")
	public String fetchFeedToday() throws JsonProcessingException, IOException {
		LOGGER.info("calling GET for /feed");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(NASA_FEED_WS_URL, String.class);

		ObjectMapper jsonMapper = new ObjectMapper();
		// jackson-datatype-jsr310 dodany w dep. + rejestracja tutaj w celu
		// serializacji/deserializacji do java 8 date api
		jsonMapper.registerModule(new JavaTimeModule());

		//parsowanie opdpowiedzi zeby dostac liste NEOs
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

		return "OK";

	}
}