package com.sz.asteroid;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sz.asteroid.pojos.NeoFeedSingleDateResult;


/**
 * 
 * input neo_feed_json_response_multi_date.json for request
 * https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-04-29&end_date=2015-05-01&detailed=true&api_key=DEMO_KEY
 * @author sz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest

@Transactional
@AutoConfigureTestDatabase
public class FeedExtraction_MultiDateTests {


	@Test
	public void contextLoads() {
	}
	
	
	@Test
	public void testNeosMultiDateExtrSizeAndRange() throws JsonProcessingException, IOException {
		
		String content = UtilsIO.readFileTestResources("neo_feed_json_response_multi_date.json", Charset.defaultCharset());
		
		ResponseEntity<String> response = Mockito.mock(ResponseEntity.class);
		Mockito.when(response.getBody()).thenReturn(content);
		 
		List<NeoFeedSingleDateResult> datesList = FeedParserJSON.extractMultiDateNeosList(response);

		assertThat(datesList).hasSize(3);
		
		assertThat(datesList.stream().filter(sdr->sdr.getFeedDate().isEqual((LocalDate.of(2015, 4, 29)))).findAny().isPresent());
		assertThat(datesList.stream().filter(sdr->sdr.getFeedDate().isEqual((LocalDate.of(2015, 4, 30)))).findAny().isPresent());
		assertThat(datesList.stream().filter(sdr->sdr.getFeedDate().isEqual((LocalDate.of(2015, 5, 1)))).findAny().isPresent());
		
	} 
	
	@Test
	public void testNeosListFeedDate() throws JsonProcessingException, IOException {
		
		String content = UtilsIO.readFileTestResources("neo_feed_json_response_multi_date.json", Charset.defaultCharset());
		
		ResponseEntity<String> response = Mockito.mock(ResponseEntity.class);
		Mockito.when(response.getBody()).thenReturn(content);
		 
		List<NeoFeedSingleDateResult> datesList = FeedParserJSON.extractMultiDateNeosList(response);

		assertThat(datesList.stream().filter(sdr->sdr.getFeedDate().isEqual((LocalDate.of(2015, 4, 29)))).findAny().get().getResultList())
		.hasSize(13);
		assertThat(datesList.stream().filter(sdr->sdr.getFeedDate().isEqual((LocalDate.of(2015, 5, 1)))).findAny().get().getResultList())
		.hasSize(12);
		assertThat(datesList.stream().filter(sdr->sdr.getFeedDate().isEqual((LocalDate.of(2015, 4, 30)))).findAny().get().getResultList())
		.hasSize(11);
		
	} 
	
}
