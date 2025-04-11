package com.example.kafka.producer.service;

import com.example.kafka.producer.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;


@Service
@ConditionalOnProperty(prefix = "config.mock", name = "stress-test", havingValue = "true", matchIfMissing = false)
public class MessageServiceMockImpl implements MessageService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;

    public MessageServiceMockImpl(KafkaTemplate<String, MessageDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(MessageDTO message) {
        for (int i = 0; i < 1000; i++) {
            CompletableFuture<SendResult<String, MessageDTO>> future = kafkaTemplate.send("topic-messages", message.id(), message);
            future.thenAccept(result -> {
                LOGGER.info("Message sent with success to the topic: {}" , result.getProducerRecord().value().id());
            }).exceptionally(throwable -> {
                LOGGER.error(throwable.getMessage(), throwable);
                return null;
            });
        }
    }
}