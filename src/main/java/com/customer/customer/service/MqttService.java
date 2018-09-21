package com.customer.customer.service;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.customer.model.ResultVM;

@Service
public class MqttService implements MqttCallback{
	
	@Autowired
	AnalyzeService analyzeService;
	
	@Autowired
	MqttSendService mqttSendService;

	@PostConstruct
	public void init() throws MqttException {
		MqttClient client=new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
		client.setCallback( this );
		
		MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(true);
		options.setCleanSession(true);
		options.setConnectionTimeout(10);
		client.connect(options);
		
		client.subscribe("test");
	}
	
	@Override
	public void connectionLost(Throwable cause) {
		  System.out.println("Connection to MQTT broker lost!");
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		 System.out.println("Message received:"+ new String(message.getPayload()) );
		 ResultVM resultVM =analyzeService.findAgeGender(new String(message.getPayload()));
		 mqttSendService.send(resultVM);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}

}
