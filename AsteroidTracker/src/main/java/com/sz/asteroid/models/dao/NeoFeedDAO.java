package com.sz.asteroid.models.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sz.asteroid.pojos.NeoFeedSingleDateResult;

 
@Repository
@Transactional
public interface NeoFeedDAO extends CrudRepository<NeoFeedSingleDateResult, String>{
	
}