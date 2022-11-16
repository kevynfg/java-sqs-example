package com.javasqsfinalcountdown.javasqsaws;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.javasqsfinalcountdown.javasqsaws.config.AmazonSqsClient;

@SpringBootApplication
@Component
public class JavasqsawsApplication {

	@Autowired
	AmazonSqsClient amazonSqsClient;

	public static void main(String[] args) {
		SpringApplication.run(JavasqsawsApplication.class, args);
	}

	@PostConstruct
	public void listening() {
		amazonSqsClient.listenMessage();
	}
}
