package com.javasqsfinalcountdown.javasqsaws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;

@RestController
public class SqsController {

    @Value("${cloud.aws.end-point.uri}")
    private String endPoint;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @GetMapping("/send/{message}/{groupId}/{deDupId}")
    public void send(@PathVariable(value = "message") String message,
            @PathVariable(value = "groupId") String groupId,
            @PathVariable(value = "deDupId") String deDupId) {
        Message<String> payload = MessageBuilder.withPayload(message)
                .setHeader("message-group-id", groupId)
                .setHeader("message-deduplication-id", deDupId)
                .build();
        queueMessagingTemplate.send(endPoint, payload);
        System.out.println("Message" + payload);

    }
}
