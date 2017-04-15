
package com.sz.asteroid.pojos;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "self"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
public class Links {

    @JsonProperty("self")
    @Column(name = "selfLink", length = 100)
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
