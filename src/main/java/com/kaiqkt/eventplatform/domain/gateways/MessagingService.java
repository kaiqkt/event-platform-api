package com.kaiqkt.eventplatform.domain.gateways;

public interface MessagingService {
    void send(Object object, String queueName) throws Exception;
}
