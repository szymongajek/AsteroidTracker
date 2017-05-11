package com.sz.asteroid;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.sz.asteroid.models.dao.*;
import com.sz.asteroid.pojos.NEO;
import com.sz.asteroid.pojos.NeoFeedSingleDateResult;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Matchers.*;


@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest

@Transactional
@AutoConfigureTestDatabase
public class PersistenceTests {
	
	@Autowired
	NeoFeedDAO feedDAO;

	@Autowired
	NeoDAO neoDAO;
	
	@Autowired
	FeedProcessor feedProc;
	
	@Before
	public void createNeoResult() throws IOException {

		String content = UtilsIO.readFileTestResources("neo_feed_json_response_multi_date.json", Charset.defaultCharset());
		
		ResponseEntity<String> response = Mockito.mock(ResponseEntity.class);
		Mockito.when(response.getBody()).thenReturn(content);
		
		RestTemplate template = Mockito.mock(RestTemplate.class);
		Mockito.when(template.getForEntity(any(String.class),
				any(Class.class))).thenReturn(response);

		feedProc.updateMissingFeeds(LocalDate.of(2016, 1, 1), template);

	}
	@Test
	public void neoListSaved() throws IOException {
		
		assertThat(neoDAO.findByName("(2004 VA1)").getNeoReferenceId()).isEqualTo("3261402");
		assertThat(neoDAO.findAll()).hasSize(36);
		
		NeoFeedSingleDateResult feed = feedDAO.findByFeedDate(LocalDate.of(2015, 4, 29));

		assertThat(feed).isNotNull();
		assertThat(feed.getResultList()).hasSize(13);
		
		NeoFeedSingleDateResult feed2 = feedDAO.findByFeedDate(LocalDate.of(2015, 5, 1));

		assertThat(feed2).isNotNull();
		assertThat(feed2.getResultList()).hasSize(12);

		
	}

	
	@Test
	public void neoListOps() throws IOException {
		
	 

	}



}
