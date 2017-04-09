
package com.sz.ateroid.pojos;

import java.time.LocalDate;
import java.util.Date;

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
public class CloseApproachDatum {


	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("close_approach_date")
	/*
	 * indicate to this field date deserializer
	 */
    private LocalDate closeApproachDate;
    @JsonProperty("epoch_date_close_approach")
    private Long epochDateCloseApproach;
    @JsonProperty("relative_velocity")
    private RelativeVelocity relativeVelocity;
    @JsonProperty("miss_distance")
    private MissDistance missDistance;
    @JsonProperty("orbiting_body")
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
