package com.example.kafka.producer.service;

import com.example.kafka.producer.dto.MessageDTO;

public interface MessageService {
    public void sendMessage(MessageDTO message);
}
