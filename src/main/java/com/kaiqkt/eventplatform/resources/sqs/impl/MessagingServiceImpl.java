package com.kaiqkt.eventplatform.resources.sqs.impl;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiqkt.eventplatform.application.config.ObjectMapperConfig;
import com.kaiqkt.eventplatform.domain.gateways.MessagingService;
import com.kaiqkt.eventplatform.resources.exception.UnexpectedResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagingServiceImpl implements MessagingService {

    private final ObjectMapper mapper = ObjectMapperConfig.mapper();
    private final AmazonSQSAsync sqsClient;

    @Autowired
    public MessagingServiceImpl(AmazonSQSAsync sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Override
    public void send(Object object, String queueName) throws Exception {
        SendMessageRequest message = new SendMessageRequest();
        message.withQueueUrl(queueName);
        message.withMessageBody(mapper.writeValueAsString(object));

        try {
            sqsClient.sendMessage(message);
        }catch (Exception e){
            throw new UnexpectedResourceException(String.format("Fail to send message to queue %s, error: %s", queueName, e));
        }
    }
}
