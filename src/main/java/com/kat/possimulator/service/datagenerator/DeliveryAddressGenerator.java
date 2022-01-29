package com.kat.possimulator.service.datagenerator;

import com.kat.possimulator.model.DeliveryAddress;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class DeliveryAddressGenerator {

    private static final List<String> CITIES = Arrays.asList("London", "Manchester", "Liverpool");

    public DeliveryAddress getRandomDeliveryAddress() {
        Random random = new Random();
        return DeliveryAddress.builder()
                .addressLine(UUID.randomUUID().toString())
                .city(CITIES.get(random.nextInt(CITIES.size())))
                .country("UK")
                .postCode(UUID.randomUUID().toString())
                .contactNumber(UUID.randomUUID().toString())
                .build();
    }
}
