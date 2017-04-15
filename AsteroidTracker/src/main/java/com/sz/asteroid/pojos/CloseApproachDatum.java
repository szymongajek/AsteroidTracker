
package com.sz.asteroid.pojos;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "close_approach_date",
    "epoch_date_close_approach",
    "relative_velocity",
    "miss_distance",
    "orbiting_body"
})
@Embeddable
public class CloseApproachDatum {


	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("close_approach_date")
	/*
	 * indicate to this field date deserializer
	 */
	@Column(name = "close_approach_date")
    private LocalDate closeApproachDate;
	
    @JsonProperty("epoch_date_close_approach")
    @Column(name = "epoch_date_close_approach")
    private Long epochDateCloseApproach;
    
    @JsonProperty("relative_velocity")
    @Embedded
    private RelativeVelocity relativeVelocity;
    
    @JsonProperty("miss_distance")
    @Embedded
    private MissDistance missDistance;
    
    @JsonProperty("orbiting_body")
    @Column(name = "orbiting_body", length=50)
    private String orbitingBody;

    @JsonProperty("close_approach_date")
    public LocalDate getCloseApproachDate() {
        return closeApproachDate;
    }

    @JsonProperty("close_approach_date")
    public void setCloseApproachDate(LocalDate closeApproachDate) {
        this.closeApproachDate = closeApproachDate;
    }

    @JsonProperty("epoch_date_close_approach")
    public Long getEpochDateCloseApproach() {
        return epochDateCloseApproach;
    }

    @JsonProperty("epoch_date_close_approach")
    public void setEpochDateCloseApproach(Long epochDateCloseApproach) {
        this.epochDateCloseApproach = epochDateCloseApproach;
    }

    @JsonProperty("relative_velocity")
    public RelativeVelocity getRelativeVelocity() {
        return relativeVelocity;
    }

    @JsonProperty("relative_velocity")
    public void setRelativeVelocity(RelativeVelocity relativeVelocity) {
        this.relativeVelocity = relativeVelocity;
    }

    @JsonProperty("miss_distance")
    public MissDistance getMissDistance() {
        return missDistance;
    }

    @JsonProperty("miss_distance")
    public void setMissDistance(MissDistance missDistance) {
        this.missDistance = missDistance;
    }

    @JsonProperty("orbiting_body")
    public String getOrbitingBody() {
        return orbitingBody;
    }

    @JsonProperty("orbiting_body")
    public void setOrbitingBody(String orbitingBody) {
        this.orbitingBody = orbitingBody;
    }

}
