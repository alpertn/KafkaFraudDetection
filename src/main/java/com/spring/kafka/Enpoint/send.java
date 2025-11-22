package com.spring.kafka.Enpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.kafka.Enpoint.dto.senddto;


@RestController
@RequestMapping("/api")
public class send {


    @PostMapping("/pos")
    public ResponseEntity<Void> receivePosData(@RequestBody senddto  data) {

        return ResponseEntity.ok().build();

    }
}