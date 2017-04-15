
package com.sz.asteroid.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "links",
    "neo_reference_id",
    "name",
    "nasa_jpl_url",
    "absolute_magnitude_h",
    "estimated_diameter",
    "is_potentially_hazardous_asteroid",
    "close_approach_data"
})
@JsonIgnoreProperties(ignoreUnknown = true)

@Entity
@Table(name="NEO_Repository")
public class NEO {
	
    @JsonProperty("neo_reference_id")
	@Id
	@Column(name="neo_reference_id",unique=true, length = 30)
	@NotNull
    private String neoReferenceId;
    
    @JsonProperty("is_potentially_hazardous_asteroid")
    @Column(name = "is_hazardous")
	@NotNull
    private Boolean isPotentiallyHazardousAsteroid=false;
    
    @JsonProperty("name")
    @Column(length = 30)
   	@NotNull
    private String name;
    
    @JsonProperty("nasa_jpl_url")
    @Column(name = "nasa_jpl_url", length = 100)
    private String nasaJplUrl;
    
    @JsonProperty("absolute_magnitude_h")
    @Column(name = "absolute_magnitude_h")
    private Double absoluteMagnitudeH;
    
    @JsonProperty("links")
    @Embedded
    private Links links;
    
    @JsonProperty("estimated_diameter")
    @Embedded
    private EstimatedDiameter estimatedDiameter;
   
    @JsonProperty("close_approach_data")
    @ElementCollection
	@CollectionTable(name = "close_approach_data_elements", joinColumns = @JoinColumn(name="neo_reference_id"))
    private List<CloseApproachDatum> closeApproachData = null;

    @JsonProperty("links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(Links links) {
        this.links = links;
    }

    @JsonProperty("neo_reference_id")
    public String getNeoReferenceId() {
        return neoReferenceId;
    }

    @JsonProperty("neo_reference_id")
    public void setNeoReferenceId(String neoReferenceId) {
        this.neoReferenceId = neoReferenceId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("nasa_jpl_url")
    public String getNasaJplUrl() {
        return nasaJplUrl;
    }

    @JsonProperty("nasa_jpl_url")
    public void setNasaJplUrl(String nasaJplUrl) {
        this.nasaJplUrl = nasaJplUrl;
    }

    @JsonProperty("absolute_magnitude_h")
    public Double getAbsoluteMagnitudeH() {
        return absoluteMagnitudeH;
    }

    @JsonProperty("absolute_magnitude_h")
    public void setAbsoluteMagnitudeH(Double absoluteMagnitudeH) {
        this.absoluteMagnitudeH = absoluteMagnitudeH;
    }

    @JsonProperty("estimated_diameter")
    public EstimatedDiameter getEstimatedDiameter() {
        return estimatedDiameter;
    }

    @JsonProperty("estimated_diameter")
    public void setEstimatedDiameter(EstimatedDiameter estimatedDiameter) {
        this.estimatedDiameter = estimatedDiameter;
    }

    @JsonProperty("is_potentially_hazardous_asteroid")
    public Boolean getIsPotentiallyHazardousAsteroid() {
        return isPotentiallyHazardousAsteroid;
    }

    @JsonProperty("is_potentially_hazardous_asteroid")
    public void setIsPotentiallyHazardousAsteroid(Boolean isPotentiallyHazardousAsteroid) {
        this.isPotentiallyHazardousAsteroid = isPotentiallyHazardousAsteroid;
    }

    @JsonProperty("close_approach_data")
    public List<CloseApproachDatum> getCloseApproachData() {
        return closeApproachData;
    }

    @JsonProperty("close_approach_data")
    public void setCloseApproachData(List<CloseApproachDatum> closeApproachData) {
        this.closeApproachData = closeApproachData;
    }

}
