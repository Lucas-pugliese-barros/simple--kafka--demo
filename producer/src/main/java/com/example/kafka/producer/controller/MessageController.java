package com.example.kafka.producer.controller;

import com.example.kafka.producer.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController implements MessageAPI {

    @PostMapping
    public ResponseEntity<MessageDTO> newMessage(MessageDTO message) {
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
