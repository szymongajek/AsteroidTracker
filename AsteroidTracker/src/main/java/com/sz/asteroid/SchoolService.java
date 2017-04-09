package com.sz.asteroid;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.sz.ateroid.pojos.NEO;

@RestController
@RequestMapping(value = "/restNEO")
class SchoolService {

	private static Logger LOGGER = LogManager.getLogger();

	@RequestMapping(value = "/feed")
	public String getStudent() throws JsonProcessingException, IOException {
		LOGGER.info("calling GET for /feed");
		String URL = "https://api.nasa.gov/neo/rest/v1/feed/today?detailed=false&api_key=DEMO_KEY";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);

		ObjectMapper mapper = new ObjectMapper();
		//jackson-datatype-jsr310 dodany w dep. + rejestracja tutaj w celu serializacji/deserializacji do java 8 date api  
		 mapper.registerModule( new JavaTimeModule());
			
		JsonNode root = mapper.readTree(response.getBody());
		ArrayList<JsonNode> tablesOfObj = new ArrayList<>();
		 root.get("near_earth_objects").forEach(n->tablesOfObj.add(n));
		 ArrayNode arrnode = (ArrayNode)tablesOfObj.get(0);
		 arrnode.forEach(n->LOGGER.info("NEOs "+n.toString()));
		 
		 ObjectMapper jsonObjectMapper = new ObjectMapper();
		 List<NEO> listOfNEOs = new ArrayList<>();
		 arrnode.forEach(jsonArrEl->{
			try {
				listOfNEOs.add(
						 jsonObjectMapper.treeToValue(jsonArrEl, NEO.class)
						 );
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		 listOfNEOs.forEach(n->LOGGER.info("NEOs "+n.toString()));

		 
		return "OK";

	}
}