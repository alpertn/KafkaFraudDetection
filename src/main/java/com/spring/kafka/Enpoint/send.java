package com.spring.kafka.Enpoint;


import com.spring.kafka.data.service.DataService;
import com.spring.kafka.kafkaservice.KafkaLogging;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class send {

    private final DataService service;
    private final KafkaLogging kafka;

    @PostMapping("/pos")
    public ResponseEntity<String> processPayment(
            @RequestBody Map<String, String> payload,
            HttpServletRequest request) {

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String url = request.getRequestURI();
        String headers = request.getHeaderNames().toString();

        kafka.send(payload, ip, userAgent, url, headers);

        service.saveRequest(payload, ip, userAgent, url, headers);

        return ResponseEntity.ok("Successfully");

    }
}
