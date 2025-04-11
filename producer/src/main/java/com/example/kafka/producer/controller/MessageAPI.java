package com.example.kafka.producer.controller;

import com.example.kafka.producer.dto.MessageDTO;
import org.springframework.http.ResponseEntity;

public interface MessageAPI {

    ResponseEntity<MessageDTO> newMessage(MessageDTO message);
}
