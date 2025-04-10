package com.example.kafka.producer.service;

import com.example.kafka.producer.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MessageServiceImpl implements MessageService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;

    public MessageServiceImpl(KafkaTemplate<String, MessageDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(MessageDTO message) {
        CompletableFuture<SendResult<String, MessageDTO>> future = kafkaTemplate.send("topic-messages", message.id(), message);
        future.thenAccept(result -> {
            LOGGER.info("Message sent with success to the topic: {}" , message.id());
        }).exceptionally(throwable -> {
            LOGGER.error(throwable.getMessage(), throwable);
            return null;
        });
    }
}
