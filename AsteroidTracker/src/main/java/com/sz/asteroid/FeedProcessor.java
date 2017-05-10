package com.sz.asteroid;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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

@Component
public class FeedProcessor {
	static final Logger LOGGER = LoggerFactory.getLogger(FeedProcessor.class);

	@Autowired
	NeoDAO neoDao;

	@Autowired
	NeoFeedDAO feedDao;
	
	@Scheduled(cron = "0 30 10 * * *")
	public void processFeed_scheduled() {
		LOGGER.info("Starting scheduled job");
//		processFeed();
		LOGGER.info("scheduled job done...");
	}
	
	public String processFeed() {
		NeoFeedSingleDateResult feedResult;
		try {
			feedResult = FeedProcessor.callNasaApiForNeoFeed();
		}catch (IOException e) {
			LOGGER.error("feed processing error",e);
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		feedResult.getResultList().forEach(el -> sb.append(el.getName() + ", "));

		LOGGER.info("saving feed for" + feedResult.getFeedDate());
		LOGGER.info("Saving following elements to DB:" + sb.toString());
		// neoDao.save(feedResult.getResultList());

		feedDao.save(feedResult);
		return sb.toString();
	}

	static NeoFeedSingleDateResult callNasaApiForNeoFeed() throws IOException, JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(AsteroidTrackerApplication.NASA_FEED_WS_URL,
				String.class);

		return FeedProcessor.extractsNeosList(response);
	}

	/**
	 * parsowanie opdpowiedzi zeby dostac liste NEOs
	 * 
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
		// tablica obeiktow dla roznych dat - w tym przypadku jest jedna data
		String todayDate = root.get(AsteroidTrackerApplication.KEY_NEAR_EARTH_OBJECTS).fieldNames().next();

		ArrayNode arrnode = (ArrayNode) root.get(AsteroidTrackerApplication.KEY_NEAR_EARTH_OBJECTS).get(todayDate);

		List<NEO> listOfNEOs = new ArrayList<>();
		arrnode.forEach(jsonArrEl -> {
			try {
				listOfNEOs.add(jsonMapper.treeToValue(jsonArrEl, NEO.class));
			} catch (JsonProcessingException e) {
				AsteroidTrackerApplication.LOGGER.error("blad podczas tworzenia obiektu NEO:" + jsonArrEl, e);
			}
		});

		LocalDate feedDate = LocalDate.parse(todayDate, DateTimeFormatter.ISO_LOCAL_DATE);

		listOfNEOs.forEach(n -> AsteroidTrackerApplication.LOGGER.info("NEOs: " + n.toString()));

		return new NeoFeedSingleDateResult(feedDate, listOfNEOs);
	}
}
