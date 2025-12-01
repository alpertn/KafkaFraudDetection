package com.spring.kafka.kafkaservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service // spring beani yapmak icin zorunlu
@RequiredArgsConstructor
public class KafkaLogging {

    private final KafkaTemplate kafkaTemplate;
    private String kafkaTopic = "websocket-logs";



    public void send(Map<String, String> payload, String clientIp, String headers, String userAgent, String url){

        ObjectMapper objectMapper = new ObjectMapper();

        try{
            String toKafka = objectMapper.writeValueAsString(Map.ofEntries(

                    Map.entry("ip", Objects.toString(clientIp, "unknown")),
                    Map.entry("userAgent", Objects.toString(userAgent, "unknown")),
                    Map.entry("url", Objects.toString(url, "unknown")),
                    Map.entry("headers", Objects.toString(headers, "unknown")),
                    Map.entry("timestamp", Instant.now().toString()), // localdatetimeden farki utc saatini almasi

                    // payload'dan gelen verileri aliyor ve kafkaobjectmapper e donusturuyor. mapper olarak atmaadigimiz icin onuda string turune donusturup atiyoruz.

                    Map.entry("userKey", Objects.toString(payload.get("userkey"), "unknown")),
                    Map.entry("companyName", Objects.toString(payload.get("companyName"), "unknown")),
                    Map.entry("companyType", Objects.toString(payload.get("companyType"), "unknown")),
                    Map.entry("companyDescription", Objects.toString(payload.get("companyDescription"), "unknown")),
                    Map.entry("taxId", Objects.toString(payload.get("taxId"), "unknown")),
                    Map.entry("locale", Objects.toString(payload.get("locale"), "unknown")),
                    Map.entry("price", Objects.toString(payload.get("price"), "unknown")),
                    Map.entry("paymentGroup", Objects.toString(payload.get("paymentGroup"), "unknown")),
                    Map.entry("cardHolderName", Objects.toString(payload.get("cardHolderName"), "unknown")),
                    Map.entry("cardNumber", Objects.toString(payload.get("cardNumber"), "unknown")),
                    Map.entry("expireMonth", Objects.toString(payload.get("expireMonth"), "unknown")),
                    Map.entry("expireYear", Objects.toString(payload.get("expireYear"), "unknown")),
                    Map.entry("cvc", Objects.toString(payload.get("cvc"), "unknown")),
                    Map.entry("buyerIp", Objects.toString(payload.get("buyerIp"), "unknown")),
                    Map.entry("buyerName", Objects.toString(payload.get("buyerName"), "unknown")),
                    Map.entry("buyerSurname", Objects.toString(payload.get("buyerSurname"), "unknown")),
                    Map.entry("buyerGsmNumber", Objects.toString(payload.get("buyerGsmNumber"), "unknown")),
                    Map.entry("buyerEmail", Objects.toString(payload.get("buyerEmail"), "unknown")),
                    Map.entry("buyerTckn", Objects.toString(payload.get("buyerTckn"), "unknown")),
                    Map.entry("buyerRegistrationAddress", Objects.toString(payload.get("buyerRegistrationAddress"), "unknown")),
                    Map.entry("buyerCity", Objects.toString(payload.get("buyerCity"), "unknown")),
                    Map.entry("buyerCountry", Objects.toString(payload.get("buyerCountry"), "unknown")),
                    Map.entry("buyerZipCode", Objects.toString(payload.get("buyerZipCode"), "unknown")),
                    Map.entry("itemName", Objects.toString(payload.get("itemName"), "unknown")),
                    Map.entry("itemCategory1", Objects.toString(payload.get("itemCategory1"), "unknown")),
                    Map.entry("itemDescription", Objects.toString(payload.get("itemDescription"), "unknown")),
                    Map.entry("itemType", Objects.toString(payload.get("itemType"), "unknown")),
                    Map.entry("itemPrice", Objects.toString(payload.get("itemPrice"), "unknown"))
            ));


            kafkaTemplate.send(kafkaTopic, clientIp , toKafka); // kafkada her zaman key String olmak zorunda. ilk key topic ondan sonra vaRsa string key ve payload
        }catch(Exception e){

            System.out.println("Kafkaya veri gonderme hatasi KAFKALOGGING ======= "+ e.getMessage());

        }


    }

}
// ustteki kopd yuerine bu kod eklenecek gereksiz uzun ustteki kod
//public void kafkalog(Map<String, String> payload,
//                     String clientIp,
//                     String userAgent,
//                     String url,
//                     String headers) {
//
//    try {
//        // 1. payload'u değiştirebilir hale getir (immutable olabilir diye)
//        Map<String, Object> logMap = new HashMap<>(payload); // tüm payload direkt içinde!
//
//        // 2. Sadece eksik/özel olanları ekle (hepsi String veya Object olabilir)
//        logMap.put("ip", Objects.toString(clientIp, "unknown"));
//        logMap.put("userAgent", Objects.toString(userAgent, "unknown"));
//        logMap.put("url", Objects.toString(url, "unknown"));
//        logMap.put("headers", Objects.toString(headers, "unknown"));
//        logMap.put("timestamp", Instant.now().toString());
//
//        // 3. JSON'a çevir ve gönder
//        String toKafka = objectMapper.writeValueAsString(logMap);
//        kafkaTemplate.send("websocket-logs", clientIp, toKafka);
//
//    } catch (Exception e) {
//        System.out.println("Kafka websocket-log gonderme hatasi: " + e.getMessage());
//        e.printStackTrace(); // daha iyi loglama için
//    }
//}