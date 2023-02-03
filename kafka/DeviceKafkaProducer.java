package com.energyms.energyms.kafka;



import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


import com.energyms.energyms.model.DeviceDataKafkaMessage;
//import com.energyms.energyms.model.User;

@Service
public class DeviceKafkaProducer {

	private KafkaTemplate<String,DeviceDataKafkaMessage> kafkaTemplate;

	public DeviceKafkaProducer(KafkaTemplate<String, DeviceDataKafkaMessage> kafkaTemplate) {
		
		this.kafkaTemplate = kafkaTemplate;
	}
	public void sendMessage(DeviceDataKafkaMessage deviceDataKafkaMessage) //entity msg
	{
		Message<DeviceDataKafkaMessage> message=MessageBuilder.withPayload(deviceDataKafkaMessage).setHeader(KafkaHeaders.TOPIC, "devicedata_topic").build();
		kafkaTemplate.send(message);
	}//*
}

