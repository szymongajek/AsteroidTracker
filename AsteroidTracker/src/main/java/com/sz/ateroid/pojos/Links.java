
package com.sz.ateroid.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "self"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {

    @JsonProperty("self")
    private String selfLink;

    @JsonProperty("self")
    public String getSelf() {
        return selfLink;
    }

    @JsonProperty("self")
    public void setSelf(String self) {
        this.selfLink = self;
    }

}
