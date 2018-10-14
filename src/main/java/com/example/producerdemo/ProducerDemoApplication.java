package com.example.producerdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableBinding(MyProcessor.class)
public class ProducerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerDemoApplication.class, args);
	}
}

@RestController
class ProducerController {
	
	@Autowired
	MyProcessor myProcessor;
	
	@RequestMapping(value = "sendmessage/{message}", method = RequestMethod.GET)
	public String sendMessage(@PathVariable("message") String message) {
		myProcessor.anOutput().send(MessageBuilder.withPayload(message).build());
		return "sent";	
	}

}

interface MyProcessor {
    String INPUT = "myInput";
 
    @Output("myOutput")
    MessageChannel anOutput();
}