package com.example.kafka.producer.controller;

import com.example.kafka.producer.dto.MessageDTO;
import com.example.kafka.producer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController implements MessageAPI {

    private final MessageService messageService;

    @Autowired
    public MessageController (MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageDTO> newMessage(@RequestBody MessageDTO message) {
        messageService.sendMessage(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
