package com.spring.kafka.data.service;

import com.spring.kafka.data.Entity.Data;
import com.spring.kafka.data.repository.RequestDataRepository;
import lombok.RequiredArgsConstructor;
import com.spring.kafka.kafkaservice.KafkaLogging;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataService {

    private final RequestDataRepository repository;
    private final KafkaLogging kafka;


    public void saveRequest(Map<String, String> payload, // payload map olarak gelıyor
                            String clientIp,
                            String userAgent,
                            String requestUrl,
                            String requestHeaders) {

        Data data = Data.builder()


                .userKey(payload.get("userKey"))
                .ip(clientIp)
                .headers(requestHeaders)
                .requesturl(requestUrl)



                .createdAt(LocalDateTime.now())

                .companyName(payload.get("companyName"))
                .companyType(payload.get("companyType"))
                .companyDescription(payload.get("companyDescription"))
                .taxId(payload.get("taxId"))

                .locale(payload.get("locale"))
                .price(payload.get("price"))
                .paymentGroup(payload.get("paymentGroup"))

                .cardHolderName(payload.get("cardHolderName"))
                .cardNumber(payload.get("cardNumber"))
                .expireMonth(payload.get("expireMonth"))
                .expireYear(payload.get("expireYear"))
                .cvc(payload.get("cvc"))

                .buyerIp(payload.get("buyerIp"))
                .buyerName(payload.get("buyerName"))
                .buyerSurname(payload.get("buyerSurname"))
                .buyerGsmNumber(payload.get("buyerGsmNumber"))
                .buyerEmail(payload.get("buyerEmail"))
                .buyerTckn(payload.get("buyerTckn"))
                .buyerRegistrationAddress(payload.get("buyerRegistrationAddress"))
                .buyerCity(payload.get("buyerCity"))
                .buyerCountry(payload.get("buyerCountry"))
                .buyerZipCode(payload.get("buyerZipCode"))

                .itemName(payload.get("itemName"))
                .itemCategory1(payload.get("itemCategory1"))
                .itemDescription(payload.get("itemDescription"))
                .itemType(payload.get("itemType"))
                .itemPrice(payload.get("itemPrice"))

                .build();
        kafka.send(payload,clientIp, userAgent, requestUrl, requestHeaders);
        repository.save(data);


    }
}