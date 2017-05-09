package com.sz.asteroid;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sz.asteroid.pojos.NEO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsteroidTrackerApplicationTests {


	@Test
	public void contextLoads() {
	}
	
	
	@Test
	public void testNeosListExtraction() throws JsonProcessingException, IOException {
		
		String content = UtilsIO.readFileTestResources("neo_feed_json_response.json", Charset.defaultCharset());
		
		ResponseEntity<String> response = Mockito.mock(ResponseEntity.class);
		Mockito.when(response.getBody()).thenReturn(content);
		 
		List<NEO> neosList = FeedProcessor.extractsNeosList(response).getResultList();
		assertThat(neosList).hasSize(7);
		
	} 
	
	@Test
	public void testNeosListFeedDate() throws JsonProcessingException, IOException {
		
		String content = UtilsIO.readFileTestResources("neo_feed_json_response.json", Charset.defaultCharset());
		
		ResponseEntity<String> response = Mockito.mock(ResponseEntity.class);
		Mockito.when(response.getBody()).thenReturn(content);
		 
		LocalDate date = FeedProcessor.extractsNeosList(response).getFeedDate();
		assertThat(date).isEqualTo(LocalDate.parse("2017-04-06"));
		
	} 
	
	@Test
	public void testNeosFieldsExtraction() throws JsonProcessingException, IOException {
		
		String content = UtilsIO.readFileTestResources("neo_feed_json_response.json", Charset.defaultCharset());
		
		ResponseEntity<String> response = Mockito.mock(ResponseEntity.class);
		Mockito.when(response.getBody()).thenReturn(content);
		 
		List<NEO> neosList = FeedProcessor.extractsNeosList(response).getResultList();
		NEO tested = neosList.stream().filter(neo->neo.getName().equals("(2014 KB46)")).findAny().get();
		
		assertThat(tested.getName()).isEqualTo("(2014 KB46)");
		assertThat(tested.getNasaJplUrl()).isEqualTo("http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=3672670");
		assertThat(tested.getAbsoluteMagnitudeH()).isEqualTo(24.8d);
		assertThat(tested.getIsPotentiallyHazardousAsteroid()).isEqualTo(false);
		
	} 
	
	@Test
	public void testNeosSecondaryFieldsExtraction() throws JsonProcessingException, IOException {
		
		String content = UtilsIO.readFileTestResources("neo_feed_json_response.json", Charset.defaultCharset());
		
		ResponseEntity<String> response = Mockito.mock(ResponseEntity.class);
		Mockito.when(response.getBody()).thenReturn(content);
		 
		List<NEO> neosList = FeedProcessor.extractsNeosList(response).getResultList();
		NEO tested = neosList.stream().filter(neo->neo.getName().equals("(2014 KB46)")).findAny().get();
		
		assertThat(tested.getEstimatedDiameter().getMeters().getMinDiameter()).isEqualTo(29.1443904535d);
		assertThat(tested.getEstimatedDiameter().getMeters().getMaxDiameter()).isEqualTo(65.1688382168d);
	
		assertThat(tested.getCloseApproachData().get(0).getOrbitingBody()).isEqualTo("Earth");
		assertThat(tested.getCloseApproachData().get(0).getRelativeVelocity().getKilometersPerHour()).isEqualTo(65823.0827751533d);
		assertThat(tested.getCloseApproachData().get(0).getRelativeVelocity().getKilometersPerSecond()).isEqualTo(18.2841896598d);
		
		assertThat(tested.getCloseApproachData().get(0).getMissDistance().getKilometers()).isEqualTo(63809884d);
		assertThat(tested.getCloseApproachData().get(0).getMissDistance().getAstronomical()).isEqualTo(0.4265427421d);
		assertThat(tested.getCloseApproachData().get(0).getCloseApproachDate()).isEqualTo(LocalDate.of(2017, 4, 6));
		
	} 



}
