package com.sz.asteroid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sz.asteroid.models.dao.NeoDAO;
import com.sz.asteroid.models.dao.NeoFeedDAO;
import com.sz.asteroid.pojos.NEO;
import com.sz.asteroid.pojos.NeoFeedSingleDateResult;

@RestController
@RequestMapping(value = "/restNEO")
class FeedService {

	@Autowired
	NeoDAO neoDao;
	
	@Autowired
	NeoFeedDAO feedDao;
	
	private static Logger LOGGER = LogManager.getLogger(FeedService.class);
	
	@RequestMapping(value = "/feed")
	public String fetchFeedToday() throws JsonProcessingException, IOException {
		LOGGER.info("calling GET for /feed");
		
		NeoFeedSingleDateResult feedResult = AsteroidTrackerApplication.callNasaApiForNeoFeed();
		StringBuilder sb = new StringBuilder();
		feedResult.getResultList().forEach(el->sb.append(el.getName()+", "));
		
		LOGGER.info("saving feed for"+feedResult.getFeedDate());
		LOGGER.info("Saving following elements to DB:"+sb.toString());
//		neoDao.save(feedResult.getResultList());
		
		feedDao.save(feedResult);
		return sb.toString();

	}

	
}