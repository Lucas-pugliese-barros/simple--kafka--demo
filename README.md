# Simple Kafka Messaging Project

## Overview

This project demonstrates a basic implementation of **Apache Kafka** using **Java** and **Spring Boot**. It consists of two separate applications:

1. **Kafka Producer**: A RESTful application that accepts JSON messages via an API endpoint and publishes them to a Kafka topic called `topic-messages`.
2. **Kafka Consumer**: An application that listens to the `topic-messages` topic and logs the received messages to the console.

The project is intentionally simple to showcase foundational Kafka concepts—producing and consuming messages—while leveraging Spring Boot for ease of development.

- **kafka-producer**: Sends messages to the `topic-messages` Kafka topic.
- **kafka-consumer**: Reads and logs messages from the `topic-messages` topic.

---

## Prerequisites

- **Java 21** or later
- **Maven**
- **Apache Kafka** (via Docker)
- **Docker**

---

## Setup Instructions

### 1. Start Kafka

Run Kafka using Docker:
```bash
docker run -d -p 9092:9092 --name kafka confluentinc/cp-kafka:latest
```

### 2. Build and Run the Producer:
```bash
cd kafka-producer
mvn clean install
mvn spring-boot:run
```

### 3. Build and Run the Consumer:
```bash
cd kafka-consumer
mvn clean install
mvn spring-boot:run
```

### 4. Test the Application
```bash
curl -X POST http://localhost:8081/api/messages \
     -H "Content-Type: application/json" \
     -d '{"id":"msg1","content":"Hello Kafka!"}'
```

--- 

### 5. Check topic
```bash
kafka-consumer-groups --bootstrap-server localhost:9092 --group temp-offset-check --topic topic-messages --reset-offsets --to-latest --dry-run
```

## Benefits of Using Kafka
Apache Kafka is a distributed streaming platform with several key advantages, making it ideal for this type of project:

- **High Throughput**: Kafka can handle millions of messages per second, making it suitable for large-scale data processing.
- **Scalability**: It supports multiple brokers and partitions, allowing horizontal scaling as data volume grows.
- **Durability**: Messages are persisted on disk, ensuring data isn’t lost even if consumers are offline temporarily.
- **Decoupling**: Producers and consumers operate independently, improving system resilience and flexibility.
- **Real-Time Processing**: Enables real-time data streaming and processing, critical for modern applications.
- **In this project**: Kafka provides a simple yet powerful way to decouple the producer and consumer, allowing asynchronous message passing with minimal setup.

---

## Purpose
This project is designed to demonstrate basic knowledge of Kafka and its integration with Spring Boot. It focuses on:

- Producing messages to a Kafka topic using a REST API.
- Consuming messages from the topic and processing them (logging in this case).
- Configuring Kafka with Spring Boot for seamless serialization/deserialization.
- It’s a starting point for learning Kafka and can be extended with features like:
```
Multiple partitions for parallelism.
A Dead Letter Queue (DLQ) for error handling.
Complex processing logic (e.g., aggregation, filtering).
```

---

## Limitations

- Single partition and broker for simplicity (not production-ready).
- Minimal error handling and processing logic.
- Local Kafka setup (no cluster configuration).

---

## Future Enhancements

- Add multiple brokers and partitions for scalability.
- Implement a DLQ for failed messages.
- Store consumed messages in a database.
- Simulate higher data volumes (e.g., millions of messages).

