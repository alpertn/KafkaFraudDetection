package com.spring.kafka.websocket;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.kafka.data.service.DataService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import com.spring.kafka.kafkaservice.KafkaLogging;

import javax.management.monitor.StringMonitor;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RequestInterceptor implements ChannelInterceptor{


    private final DataService dataService;
    private final KafkaLogging kafkaLogging;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Message<?> preSend ( Message<?> message, MessageChannel channel){

        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(message);
        if (stompHeaderAccessor.getCommand() == null || stompHeaderAccessor.getCommand() == StompCommand.CONNECTED) return message;

        Map<String,Object> sessionAttrs = stompHeaderAccessor.getSessionAttributes();
        if (sessionAttrs == null) return message;

        String ip = (String) sessionAttrs.get("Ip");
        if (ip == null) return message;

        String userAgent = (String) sessionAttrs.get("User-Agent");
        String body = message.getPayload().toString();
        String headers = message.getHeaders().toString();
        String url = stompHeaderAccessor.getDestination();

        if (userAgent == null)  userAgent = "unknown";
        if (body == null)  body = "unknown";


        try{

            Map<String,String> payloadMap = objectMapper.readValue(body, new TypeReference<Map<String,String>>(){}); // TypeReference Jsondan gelen verileri cevirmemiz icin ise yariyor <Map<String,String>>(){} kullanarak gelen String veriyi Map e donusturuyoruz. Jsondan alinan veriyi Map e donustuuyor.
            kafkaLogging.send(payloadMap , ip, userAgent, url, headers);
            dataService.saveRequest(payloadMap, ip, userAgent ,url, message.getHeaders().toString());

        }catch(JsonProcessingException e){
            System.out.println("JsonProcessingException in RequestInterceptor ======> " + e.getMessage());
        }

        return message;
    }


}

// STOMP HEADER KATMANI

//{
//        "command": "SEND",
//        "destination": "/app/chat",
//        "subscriptionId": "sub-0",
//        "nativeHeaders": {
//        "content-type": ["application/json"],
//        "token": ["abc123"]
//        },
//        "contentLength": 48
//        }
//

//SimpMessageHeaderAccessor
//{
//  "sessionId": "xyz-123",
//  "user": {
//    "name": "ahmet",
//    "roles": ["USER"]
//  },
//  "sessionAttributes": {
//    "userId": 17,
//    "username": "ahmet",
//    "roomId": 42,
//    "ip": "192.168.1.10"
//  },
//  "simpDestination": "/app/chat",
//  "simpMessageType": "MESSAGE"
//}

//message.getPayload()
// {
//  "message": "Selam arkadaşlar!",
//  "roomId": 42
//}

//@Override
//public Message<?> preSend(Message<?> message, MessageChannel channel) {
//    var h = StompHeaderAccessor.wrap(message);
//    if (h.getCommand() == null) return message;
//
//    String ip = (String) h.getSessionAttributes().get("REMOTE_ADDR");
//
//    if (BANNED.contains(ip)) {
//        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "IP yasaklandı: " + ip);
//    }
//
//    repo.findByIpAddress(ip).ifPresentOrElse(
//            s -> { s.setLastSeen(LocalDateTime.now()); s.setEndpointRealtime(s.getEndpointRealtime() + 1); repo.save(s); },
//            () -> repo.save(IpStats.builder()
//                    .ipAddress(ip)
//                    .firstSeen(LocalDateTime.now())
//                    .lastSeen(LocalDateTime.now())
//                    .endpointRealtime(1)
//                    .build())
//    );
//
//    return message;
//}