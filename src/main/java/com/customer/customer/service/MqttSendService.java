package com.customer.customer.service;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.customer.model.ResultVM;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MqttSendService implements MqttCallback{
	
	@Autowired
	AnalyzeService analyzeService;

	@Autowired
	ObjectMapper objectMapper;
	
	MqttClient client;
	
	@PostConstruct
	public void init() throws MqttException {
		client=new MqttClient("tcp://m10.cloudmqtt.com:17556", MqttClient.generateClientId());
		client.setCallback( this );
		
		MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(true);
		options.setCleanSession(true);
		options.setConnectionTimeout(10);
		options.setUserName("ighxhtbj");
		options.setPassword("1mXwSYhS0G-y".toCharArray());
		client.connect(options);
		
		//client.subscribe("test");
	}
	
	@Override
	public void connectionLost(Throwable cause) {
		  System.out.println("Connection to MQTT broker lost!");
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		 System.out.println("Message received:"+ new String(message.getPayload()) );
		 analyzeService.findAgeGender(new String(message.getPayload()));
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}
	
	public void send(ResultVM resultResultVM) throws JsonProcessingException, MqttPersistenceException, MqttException {
		MqttMessage message = new MqttMessage();
		message.setPayload(objectMapper.writeValueAsBytes(resultResultVM));;		
		client.publish("test", message);
	}

}
