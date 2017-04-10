
package com.sz.asteroid.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "estimated_diameter_min",
    "estimated_diameter_max"
})
public class MinMaxDiameter {

	@JsonProperty("estimated_diameter_min")
    private Double minDiameter;
    @JsonProperty("estimated_diameter_max")
    private Double maxDiameter;

    @JsonProperty("estimated_diameter_min")
    public Double getMinDiameter() {
		return minDiameter;
	}
    @JsonProperty("estimated_diameter_min")
	public void setMinDiameter(Double minDiameter) {
		this.minDiameter = minDiameter;
	}
	
	@JsonProperty("estimated_diameter_max")
	public Double getMaxDiameter() {
		return maxDiameter;
	}
	
	@JsonProperty("estimated_diameter_max")
	public void setMaxDiameter(Double maxDiameter) {
		this.maxDiameter = maxDiameter;
	}
}
