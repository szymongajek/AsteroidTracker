package com.sz.asteroid;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.internal.DoubleArrays;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sz.asteroid.models.dao.NeoDAO;
import com.sz.asteroid.models.dao.NeoFeedDAO;
import com.sz.asteroid.pojos.NEO;
import com.sz.asteroid.pojos.NeoFeedSingleDateResult;

@Component 
public class FeedProcessor   {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedProcessor.class);

	@Autowired
	NeoDAO neoDao;

	@Autowired
	public NeoFeedDAO feedDao;

	/**
	 * scheduled task for feed downloading. 
	 * everyday at 10:30
	 */
	@Scheduled(cron = "0 30 10 * * *")
	public void processFeed_scheduled() {
		LOGGER.info("Starting scheduled job");
		updateMissingFeeds(getLastFeedDate(),LocalDate.now(),new RestTemplate());
		processFeed(new RestTemplate());
		LOGGER.info("scheduled job done...");
	}

	private  LocalDate getLastFeedDate( ) { 
		LOGGER.info("getLastFeedDate"+feedDao.getLastFeedDate());
		return feedDao.getLastFeedDate();
		
		
//		 String sqlQuery="select e from Employee e inner join e.addList";
//		 EntityManager em = new JPAUtil().getEntityManager();
//		 Session session = em.unwrap(Session.class);
//
//		    Session session=HibernateUtil.getSessionFactory().openSession();
//
//		    Query query=session.createQuery(sqlQuery);
//
//		    List<Employee> list=query.list();
//
//		     list.stream().forEach((p)->{System.out.println(p.getEmpName());});     
//		    session.close();
	}
	
	public String updateMissingFeeds(LocalDate fromDate,LocalDate toDate, RestTemplate restTemplate) {
		
		long missingDays = java.time.temporal.ChronoUnit.DAYS.between(fromDate, toDate);
		
		if(missingDays>1){
			LocalDate requestEnd = LocalDate.now().minusDays(1);
			LocalDate requestStart = calcStartDateForMissing(LocalDate.now(), fromDate);
			LOGGER.info("Processing missing feeds for range:"+requestStart+">>"+requestEnd);
			
			List<NeoFeedSingleDateResult> resultList;
			try {
				String url = prepareMultiDatesURL(requestStart, requestEnd);
				resultList = FeedParserJSON.extractMultiDateNeosList( restTemplate.getForEntity(url,String.class));;
			}catch (IOException e) {
				LOGGER.error("feed processing error",e);
				return null;
			}
			
			StringBuilder sb = new StringBuilder();
			resultList.forEach(el -> sb.append(el.getFeedDate() + ": "+el.getResultList().size()+ ", "));

			LOGGER.info("Saving elements for following dates to DB:" + sb.toString());
			feedDao.save(resultList);
			
			return sb.toString();
			
		}else{
			LOGGER.info("No missing feeds detected");
			return null;
		}
		
	}

	static LocalDate calcStartDateForMissing(LocalDate currentDate, LocalDate lastFeed) {
		long missingDays = java.time.temporal.ChronoUnit.DAYS.between(lastFeed, currentDate);
		return (missingDays<=8)? lastFeed.plusDays(1) : currentDate.minusDays(7);
	}
	
	public String processFeed( RestTemplate restTemplate) {
		LOGGER.info("Processing today feed");
		
		NeoFeedSingleDateResult feedResult;
		try {
			feedResult =FeedParserJSON.extractSingleDateNeosList(restTemplate.getForEntity(AsteroidTrackerApplication.NASA_FEED_WS_URL,
					String.class));
		}catch (IOException e) {
			LOGGER.error("feed processing error",e);
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		feedResult.getResultList().forEach(el -> sb.append(el.getName() + ", "));

		LOGGER.info("saving feed for" + feedResult.getFeedDate());
		LOGGER.info("Saving following elements to DB:" + sb.toString());

		feedDao.save(feedResult);
		return sb.toString();
	}

	
	static String prepareMultiDatesURL( LocalDate startDate, LocalDate endDate) {

		return AsteroidTrackerApplication.NASA_FEED_WS_MULTIDATES_URL
				.replaceFirst(AsteroidTrackerApplication.MULTIDATES_URL_KEY_START, DateTimeFormatter.ISO_LOCAL_DATE.format(startDate)  )
				.replaceFirst(AsteroidTrackerApplication.MULTIDATES_URL_KEY_END, DateTimeFormatter.ISO_LOCAL_DATE.format(endDate)  );
	}
}
