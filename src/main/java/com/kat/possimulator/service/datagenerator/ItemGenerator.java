package com.kat.possimulator.service.datagenerator;

import com.kat.possimulator.model.Item;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class ItemGenerator {

    public Item getRandomItem() {
        Random random = new Random();
        Double itemPrice = random.nextDouble() * 50;
        Integer itemQuantity = random.nextInt(5);
        Double totalValue = itemPrice * itemQuantity;
        return Item.builder()
                .itemCode(UUID.randomUUID().toString())
                .itemDescription(UUID.randomUUID().toString())
                .itemPrice(itemPrice)
                .itemQuantity(itemQuantity)
                .totalValue(totalValue)
                .build();
    }
}
