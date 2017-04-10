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

	
	private static Logger LOGGER = LogManager.getLogger(FeedService.class);
	
	@RequestMapping(value = "/feed")
	public String fetchFeedToday() throws JsonProcessingException, IOException {
		LOGGER.info("calling GET for /feed");
		
		StringBuilder sb = new StringBuilder();
		AsteroidTrackerApplication.callNasaApiForNeoFeed().forEach(el->sb.append(el.getName()+", "));

		return sb.toString();

	}

	
}