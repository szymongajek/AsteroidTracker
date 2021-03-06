package com.sz.asteroid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.sz.asteroid.models.dao.NeoDAO;
import com.sz.asteroid.models.dao.NeoFeedDAO;
import com.sz.asteroid.pojos.NeoFeedSingleDateResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Asteroid tracker is a spring boot application, storing information about Near Earth Objects obtained using NASA REST service for 
 * https://api.nasa.gov/api.html#NeoWS
 * @author sz
 *
 */
@SpringBootApplication
@EnableScheduling
public class AsteroidTrackerApplication {
	static final Logger LOGGER = LoggerFactory.getLogger(AsteroidTrackerApplication.class);

	public static final String NASA_FEED_WS_URL = "https://api.nasa.gov/neo/rest/v1/feed/today?detailed=false&api_key=DEMO_KEY";
	public static final String NASA_FEED_WS_MULTIDATES_URL = "https://api.nasa.gov/neo/rest/v1/feed?start_date=___START_DATE___&end_date=___END_DATE___&detailed=true&api_key=DEMO_KEY";
	public static final String MULTIDATES_URL_KEY_START = "___START_DATE___";
	public static final String MULTIDATES_URL_KEY_END = "___END_DATE___";
	public static final String KEY_NEAR_EARTH_OBJECTS = "near_earth_objects";
	
	public static void main(String[] args) {
		LOGGER.info("before");
		SpringApplication.run(AsteroidTrackerApplication.class, args);
		LOGGER.info("after");
	}

}
