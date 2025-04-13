package com.example.kafka.consumer.service;

import com.example.kafka.consumer.dto.MessageDTO;

public interface MessageService {

    void readMessages(MessageDTO messageDTO);
}
