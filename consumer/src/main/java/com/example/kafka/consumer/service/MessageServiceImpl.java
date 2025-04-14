package com.example.kafka.consumer.service;

import com.example.kafka.consumer.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class.getSimpleName());

    @KafkaListener(topics = "topic-messages", groupId = "default-group")
    @Override
    public void readMessages(MessageDTO messageDTO) {
        LOGGER.info("Message: {}", messageDTO );
    }
}
