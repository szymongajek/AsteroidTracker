package com.sz.asteroid;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.sz.asteroid.models.dao.*;
import com.sz.asteroid.pojos.NEO;
import com.sz.asteroid.pojos.NeoFeedSingleDateResult;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class PersistenceTests {
	
	@Autowired
	NeoFeedDAO feedDAO;

	
	@Autowired
	NeoDAO neoDAO;
	
	@Before
	public void createNeoResult() throws IOException {

		String content = UtilsIO.readFileTestResources("neo_feed_json_response.json", Charset.defaultCharset());
		
		ResponseEntity<String> response = Mockito.mock(ResponseEntity.class);
		Mockito.when(response.getBody()).thenReturn(content);
		 
		NeoFeedSingleDateResult neoResult = FeedProcessor.extractsNeosList(response);

		feedDAO.save(neoResult);

	}
	
	@Test
	public void singleNeoCrudOps() throws IOException {
		
		assertThat(neoDAO.findByName("(2014 KB46)").getNeoReferenceId()).isEqualTo("3672670");
		assertThat(neoDAO.findOne("3672670").getNeoReferenceId()).isEqualTo("3672670");
		assertThat(neoDAO.findAll()).hasSize(7);
		
		NEO neo = neoDAO.findOne("3672670");
		String testName = "new test name";
		neo.setName(testName);
		assertThat(neoDAO.findOne("3672670").getName()).isEqualTo(testName);
		
		neoDAO.delete(neo);
		
		assertThat(neoDAO.findOne("3672670")).isNull();

	}
	
	@Test
	public void neoListOps() throws IOException {
		NeoFeedSingleDateResult feed = feedDAO.findByFeedDate(LocalDate.parse("2017-04-06"));

		assertThat(feed).isNotNull();
		assertThat(feed.getResultList()).hasSize(7);
		
		
		Integer id = feed.getFetchId();
		feed.setFeedDate(LocalDate.parse("3012-04-06"));
		
		feedDAO.save(feed);
		assertThat(feedDAO.findOne(id).getFeedDate()).isEqualTo(LocalDate.parse("3012-04-06"));
		feedDAO.delete(id);
		
		assertThat(feedDAO.findOne(id)).isNull();
		
		assertThat(neoDAO.findOne("3672670")).isNull();

	}



}
