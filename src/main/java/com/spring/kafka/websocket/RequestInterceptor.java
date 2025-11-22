package com.spring.kafka.websocket;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.kafka.data.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import com.spring.kafka.kafkaservice.KafkaLogging;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RequestInterceptor implements ChannelInterceptor {

    private final DataService service;
    private final KafkaLogging kafka;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = (StompHeaderAccessor) SimpMessageHeaderAccessor.wrap(message);

        Map<String, Object> attrs = accessor.getSessionAttributes();
        if (attrs == null) return message;

        if (accessor.getCommand() == null || accessor.getCommand() == StompCommand.CONNECTED) {
            return message; // veya ne yapacaksan
        }



        String ip = (String) attrs.get("Ip");
        String userAgent = (String) attrs.get("User-Agent");
        if (ip == null) return message;

        String body = message.getPayload().toString();
        String headers = message.getHeaders().toString();
        String url = accessor.getDestination();

        if (userAgent == null)  userAgent = "unknown";
        if (body == null)  body = "unknown";





        try{

            Map<String,String> payloadmap = mapper.readValue(body, new TypeReference<Map<String, String>>(){}); // uzun payloadi map'e donustuyor.
            kafka.send(payloadmap,ip, userAgent, url, headers);
            service.saveRequest(payloadmap, ip, userAgent ,url, message.getHeaders().toString());

        }catch (JsonProcessingException e) {
            System.out.println("RequestInterceptor'de donusturme hatasi veya repository hatasi! --- " + e.getMessage());
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