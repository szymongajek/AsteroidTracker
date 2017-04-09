package com.sz.asteroid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class AsteroidTrackerApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(AsteroidTrackerApplication.class);

	public static void main(String[] args) {

		LOGGER.info("before");
		SpringApplication.run(AsteroidTrackerApplication.class, args);
		LOGGER.info("after");
		getNEOsFeed();
	}

	public static void getNEOsFeed() {
		String URL = "https://api.nasa.gov/neo/rest/v1/feed/today?detailed=false&api_key=DEMO_KEY";

		RestTemplate template = new RestTemplate();

		// ResponseEntity<List<Rate>> rateResponse =
		// restTemplate.exchange("https://bitpay.com/api/rates",
		// HttpMethod.GET, null, new ParameterizedTypeReference<List<Rate>>() {
		// });
		// List<Rate> rates = rateResponse.getBody();

	}
}
