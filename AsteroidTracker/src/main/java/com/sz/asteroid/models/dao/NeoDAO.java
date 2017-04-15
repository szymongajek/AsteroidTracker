package com.sz.asteroid.models.dao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.sz.asteroid.pojos.NEO;

import org.springframework.data.repository.CrudRepository;

@Repository
@Transactional
public interface NeoDAO extends CrudRepository<NEO, String>{
	
	public NEO findByName(String name);
}
