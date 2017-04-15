
package com.sz.asteroid.pojos;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
public class EstimatedDiameter {

    @JsonProperty("meters")
    @Embedded
    private MinMaxDiameter meters;

    @JsonProperty("meters")
    public MinMaxDiameter getMeters() {
        return meters;
    }

    @JsonProperty("meters")
    public void setMeters(MinMaxDiameter meters) {
        this.meters = meters;
    }

}
