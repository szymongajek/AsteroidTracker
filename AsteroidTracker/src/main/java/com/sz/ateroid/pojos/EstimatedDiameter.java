
package com.sz.ateroid.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EstimatedDiameter {

    @JsonProperty("meters")
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
