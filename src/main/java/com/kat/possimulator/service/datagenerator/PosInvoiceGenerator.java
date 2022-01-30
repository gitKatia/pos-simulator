package com.kat.possimulator.service.datagenerator;

import com.kat.possimulator.model.DeliveryAddress;
import com.kat.possimulator.model.Item;
import com.kat.possimulator.model.PosInvoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class PosInvoiceGenerator {

    private static final List<String> PAYMENT_METHODS = Arrays.asList("card", "cash");
    private static final List<String> CUSTOMER_TYPES = Arrays.asList("prime", "standard");
    private static final List<String> DELIVERY_TYPES = Arrays.asList("delivery", "in store");

    private final DeliveryAddressGenerator deliveryAddressGenerator;
    private final ItemGenerator itemGenerator;

    public PosInvoice generatePosInvoice() {
        Random random = new Random();
        String customerType = CUSTOMER_TYPES.get(random.nextInt(CUSTOMER_TYPES.size()));
        String deliveryType = DELIVERY_TYPES.get(random.nextInt(DELIVERY_TYPES.size()));
        String paymentMethod = PAYMENT_METHODS.get(random.nextInt(PAYMENT_METHODS.size()));
        DeliveryAddress deliveryAddress = "delivery".equals(deliveryType) ?
                deliveryAddressGenerator.getRandomDeliveryAddress() : null;
        List<Item> invoiceItems = new ArrayList<>();
        Integer totalNumberOfItems = random.nextInt(10);
        IntStream.range(0, totalNumberOfItems)
                .forEach(i -> {
                    invoiceItems.add(itemGenerator.getRandomItem());
                });
        Double totalAmount = invoiceItems.stream()
                .map(it -> it.getTotalValue())
                .reduce(Double::sum)
                .orElse(0.00);
        return PosInvoice.builder()
                .createdAt(System.currentTimeMillis())
                .deliveryAddress(deliveryAddress)
                .customerCardNo(UUID.randomUUID().toString())
                .posId(UUID.randomUUID().toString())
                .storeId(UUID.randomUUID().toString())
                .customerType(customerType)
                .deliveryType(deliveryType)
                .paymentMethod(paymentMethod)
                .taxableAmount(totalAmount)
                .invoiceNo(UUID.randomUUID().toString())
                .itemQuantity(totalNumberOfItems)
                .invoiceItems(invoiceItems)
                .totalAmount(totalAmount)
                .build();
    }
}
