package com.example.kafka.producer.controller;

import com.example.kafka.producer.dto.MessageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(partitions = 1, topics = {"topic-messages"})
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"
})
class MessageControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private KafkaTemplate<String, MessageDTO> kafkaTemplate;

    private CountDownLatch latch = new CountDownLatch(1);
    private MessageDTO receivedMessage;

    @Test
    void testSendMessage_PublishesToKafka() throws InterruptedException {
        MessageDTO message = new MessageDTO("id", "content");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<MessageDTO> request = new HttpEntity<>(message, headers);

        ResponseEntity<MessageDTO> response = restTemplate.exchange(
                "/api/messages",
                HttpMethod.POST,
                request,
                MessageDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        assertThat(latch.await(3, TimeUnit.SECONDS)).isTrue();

        assertThat(receivedMessage).isNotNull();
        assertThat(receivedMessage.id()).isEqualTo(message.id());
        assertThat(receivedMessage.content()).isEqualTo(message.content());
    }

    @KafkaListener(topics = "topic-messages", groupId = "test-group")
    public void listen(MessageDTO message) {
        this.receivedMessage = message;
        latch.countDown();
    }
}
