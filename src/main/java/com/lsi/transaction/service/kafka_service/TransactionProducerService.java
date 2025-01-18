package com.lsi.transaction.service.kafka_service;


import com.lsi.transaction.dto.request.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionProducerService {
  private final KafkaTemplate<String, NotificationRequest> kafkaTemplate;
  @Value("${var.TOPIC.notification-transaction:notification-requests}")
  private String TOPIC ;

  public void sendNotificationRequests(NotificationRequest request) {
    Message<NotificationRequest> message = MessageBuilder
      .withPayload(request)
      .setHeader(KafkaHeaders.TOPIC, TOPIC)
      .build();
    kafkaTemplate.send(message);
  }
}
