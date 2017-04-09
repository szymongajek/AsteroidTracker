
package com.sz.ateroid.pojos;

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
public class RelativeVelocity {

    @JsonProperty("kilometers_per_second")
    private String kilometersPerSecond;
    @JsonProperty("kilometers_per_hour")
    private String kilometersPerHour;

    @JsonProperty("kilometers_per_second")
    public String getKilometersPerSecond() {
        return kilometersPerSecond;
    }

    @JsonProperty("kilometers_per_second")
    public void setKilometersPerSecond(String kilometersPerSecond) {
        this.kilometersPerSecond = kilometersPerSecond;
    }

    @JsonProperty("kilometers_per_hour")
    public String getKilometersPerHour() {
        return kilometersPerHour;
    }

    @JsonProperty("kilometers_per_hour")
    public void setKilometersPerHour(String kilometersPerHour) {
        this.kilometersPerHour = kilometersPerHour;
    }


}
