package com.sz.asteroid.models.dao;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sz.asteroid.pojos.NEO;
import com.sz.asteroid.pojos.NeoFeedSingleDateResult;

 
@Repository
@Transactional
public interface NeoFeedDAO extends CrudRepository<NeoFeedSingleDateResult, Integer>{
	
	public NeoFeedSingleDateResult findByFeedDate(LocalDate feedDate);
}