
package com.sz.asteroid.pojos;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "kilometers_per_second",
    "kilometers_per_hour"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
public class RelativeVelocity {

    @JsonProperty("kilometers_per_second")
    @Column(name="kilometers_per_second")
    private Double kilometersPerSecond;
    
    @JsonProperty("kilometers_per_hour")
    @Column(name="kilometers_per_hour")
    private Double kilometersPerHour;

    @JsonProperty("kilometers_per_second")
    public Double getKilometersPerSecond() {
        return kilometersPerSecond;
    }

    @JsonProperty("kilometers_per_second")
    public void setKilometersPerSecond(Double kilometersPerSecond) {
        this.kilometersPerSecond = kilometersPerSecond;
    }

    @JsonProperty("kilometers_per_hour")
    public Double getKilometersPerHour() {
        return kilometersPerHour;
    }

    @JsonProperty("kilometers_per_hour")
    public void setKilometersPerHour(Double kilometersPerHour) {
        this.kilometersPerHour = kilometersPerHour;
    }


}
