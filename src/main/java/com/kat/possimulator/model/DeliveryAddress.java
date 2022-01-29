package com.kat.possimulator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryAddress {

    @JsonProperty("address_line")
    private String addressLine;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("post_code")
    private String postCode;
    @JsonProperty("contact_number")
    private String contactNumber;
}
