package com.javasqsfinalcountdown.javasqsaws.config;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SetQueueAttributesRequest;

@Service
public class AmazonSqsClient {

    private AmazonSQS client;

    @Value("${cloud.aws.credentials.access-key}")
    String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    String secretKey;
    
    @Value("${cloud.aws.end-point.uri}")
    String uri;

    @PostConstruct
    private void initializeAmazonSqsClient() {
        this.client = AmazonSQSClientBuilder.standard()
                .withCredentials(getAwsCredentialProvider())
                .withRegion(Region.getRegion(Regions.US_EAST_1).getName())
                .build();
    }

    private AWSCredentialsProvider getAwsCredentialProvider() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return new AWSStaticCredentialsProvider(awsCredentials);
    }

    public void listenMessage() {

        // CreateQueueRequest create_request = new CreateQueueRequest()
        //         .withQueueName(uri)
        //         .addAttributesEntry("ReceiveMessageWaitTimeSeconds", "20");

        // try {
        //     client.createQueue(create_request);
        // } catch (AmazonSQSException e) {
        //     if (!e.getErrorCode().equals("QueueAlreadyExists")) {
        //         throw e;
        //     }
        // }
        // SetQueueAttributesRequest set_attrs_request = new SetQueueAttributesRequest()
        //         .withQueueUrl(uri)
        //         .addAttributesEntry("ReceiveMessageWaitTimeSeconds", "20");
        // client.setQueueAttributes(set_attrs_request);
        
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(
            uri)
                .withWaitTimeSeconds(10)
                .withMaxNumberOfMessages(10);

        List<Message> sqsMessages = client.receiveMessage(receiveMessageRequest)
                .getMessages();

        for (Message mappedMessage : sqsMessages) {
            System.out.println("Iterator ======> " + mappedMessage.getBody());
        }
        // System.out.println(sqsMessages);
    }
}
