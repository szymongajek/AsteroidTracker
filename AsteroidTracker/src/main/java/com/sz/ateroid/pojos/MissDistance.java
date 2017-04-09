
package com.sz.ateroid.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "astronomical",
    "lunar",
    "kilometers"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class MissDistance {

    @JsonProperty("astronomical")
    private Double astronomical;
    @JsonProperty("lunar")
    private Double lunar;
    @JsonProperty("kilometers")
    private Double kilometers;

    @JsonProperty("astronomical")
    public Double getAstronomical() {
        return astronomical;
    }

    @JsonProperty("astronomical")
    public void setAstronomical(Double astronomical) {
        this.astronomical = astronomical;
    }

    @JsonProperty("lunar")
    public Double getLunar() {
        return lunar;
    }

    @JsonProperty("lunar")
    public void setLunar(Double lunar) {
        this.lunar = lunar;
    }

    @JsonProperty("kilometers")
    public Double getKilometers() {
        return kilometers;
    }

    @JsonProperty("kilometers")
    public void setKilometers(Double kilometers) {
        this.kilometers = kilometers;
    }


}
