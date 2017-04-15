package com.sz.asteroid.pojos;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;


public class NeoFeedSingleDateResult {

	LocalDate feedDate;
	
	List<NEO> resultList;
	
	
	public NeoFeedSingleDateResult(LocalDate feedDate, List<NEO> resultList) {
		this.feedDate = feedDate;
		this.resultList = resultList;
	}

	
	public LocalDate getFeedDate() {
		return feedDate;
	}

	public void setFeedDate(LocalDate feedDate) {
		this.feedDate = feedDate;
	}

	public List<NEO> getResultList() {
		return resultList;
	}

	public void setResultList(List<NEO> resultList) {
		this.resultList = resultList;
	}

	

}
