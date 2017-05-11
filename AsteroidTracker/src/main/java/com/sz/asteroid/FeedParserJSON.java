package com.sz.asteroid;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sz.asteroid.pojos.NEO;
import com.sz.asteroid.pojos.NeoFeedSingleDateResult;

public class FeedParserJSON {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedParserJSON.class);


	static NeoFeedSingleDateResult parseSingleDateNeosList(ObjectMapper jsonMapper, JsonNode root,
			String date) {
		ArrayNode arrnode = (ArrayNode) root.get(AsteroidTrackerApplication.KEY_NEAR_EARTH_OBJECTS).get(date);
	
		List<NEO> listOfNEOs = new ArrayList<>();
		arrnode.forEach(jsonArrEl -> {
			try {
				listOfNEOs.add(jsonMapper.treeToValue(jsonArrEl, NEO.class));
			} catch (JsonProcessingException e) {
				LOGGER.error("blad podczas tworzenia obiektu NEO:" + jsonArrEl, e);
			}
		});
	
		LocalDate feedDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
	
		listOfNEOs.forEach(n -> LOGGER.info("NEOs: " + n.toString()));
	
		NeoFeedSingleDateResult neoFeedSingleDateResult = new NeoFeedSingleDateResult(feedDate, listOfNEOs);
		return neoFeedSingleDateResult;
	}

	/**
	 * parsing response for NEOs list - single date
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	static NeoFeedSingleDateResult extractSingleDateNeosList(ResponseEntity<String> response)
			throws IOException, JsonProcessingException {
	
		ObjectMapper jsonMapper = new ObjectMapper();
		// jackson-datatype-jsr310 dodany w dep. + rejestracja tutaj w celu
		// serializacji/deserializacji do java 8 date api
		jsonMapper.registerModule(new JavaTimeModule());
	
		JsonNode root = jsonMapper.readTree(response.getBody());
		// tablica obeiktow dla roznych dat - w tym przypadku jest jedna data
		String todayDate = root.get(AsteroidTrackerApplication.KEY_NEAR_EARTH_OBJECTS).fieldNames().next();
	
		NeoFeedSingleDateResult neoFeedSingleDateResult = parseSingleDateNeosList(jsonMapper, root, todayDate);
		return neoFeedSingleDateResult;
	}

	/**
	 * parsing response for NEOs list - date range, max 7 days - api limit
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	static List<NeoFeedSingleDateResult> extractMultiDateNeosList(ResponseEntity<String> response)
			throws IOException, JsonProcessingException {
	
		ObjectMapper jsonMapper = new ObjectMapper();
		// jackson-datatype-jsr310 dodany w dep. + rejestracja tutaj w celu
		// serializacji/deserializacji do java 8 date api
		jsonMapper.registerModule(new JavaTimeModule());
	
		JsonNode root = jsonMapper.readTree(response.getBody());
		
		Iterable<String> iterable = () -> root.get(AsteroidTrackerApplication.KEY_NEAR_EARTH_OBJECTS).fieldNames(); // tablica dat
		
		List<NeoFeedSingleDateResult> resultList = StreamSupport.stream(iterable.spliterator(), false)
				.map(dateStr-> parseSingleDateNeosList(jsonMapper, root, dateStr) )
				.collect(Collectors.toList());
	
		return resultList;
	}


}