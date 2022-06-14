package com.kat.possimulator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item {

    @JsonProperty("item_code")
    private String itemCode;
    @JsonProperty("item_description")
    private String itemDescription;
    @JsonProperty("item_price")
    private Double itemPrice;
    @JsonProperty("item_quantity")
    private Integer itemQuantity;
    @JsonProperty("total_value")
    private Double totalValue;
}
