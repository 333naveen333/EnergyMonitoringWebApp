package com.energyms.energyms.model;

public class DeviceDataKafkaMessage {
private String topic;
private long payload;
public String getTopic() {
	return topic;
}
public void setTopic(String topic) {
	this.topic = topic;
}
public long getPayload() {
	return payload;
}
public void setPayload(long payload) {
	this.payload = payload;
}
public DeviceDataKafkaMessage(String topic, long payload) {
	super();
	this.topic = topic;
	this.payload = payload;
}
public DeviceDataKafkaMessage() {
	super();
}

}
