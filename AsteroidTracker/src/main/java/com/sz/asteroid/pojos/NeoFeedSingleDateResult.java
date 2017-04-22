package com.sz.asteroid.pojos;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

@Entity
@Table(name = "NEO_Fetch_Date")
public class NeoFeedSingleDateResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "fetch_id", unique = true, nullable = false)
	private Integer fetchId;

	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonProperty("feed_date")
	@Column(name = "close_approach_date")
	LocalDate feedDate;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "fetch_date_NEO", joinColumns = {
			@JoinColumn(name = "fn_fetch_id", referencedColumnName = "fetch_id") }, inverseJoinColumns = {
					@JoinColumn(name = "fn_neo_reference_id", referencedColumnName = "neo_reference_id") })
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
