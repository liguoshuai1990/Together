package com.lgs.center.together;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;

public class MqttMsg {
    public Context context;

    private MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);
        return mqttConnectOptions;
    }

    private MqttAndroidClient mqttClient() {
        return new MqttAndroidClient(context, "tcp://iot.eclipse.org:1883", "TogetherClient");
    }

    private MqttAndroidClient publishClient(){
        MqttAndroidClient mqttAndroidClient = this.mqttClient();
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    Log.v("TogetherApp", "Mqtt Client reconnect. ");
                } else {
                    Log.v("TogetherApp", "Mqtt Client Connected to: " + serverURI);
                }
            }
            @Override
            public void connectionLost(Throwable cause) {
                Log.v("TogetherApp", "Mqtt Client Connection was lost.");
            }
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.v("TogetherApp", "Mqtt Client Incoming message: " + new String(message.getPayload()));
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
        return mqttAndroidClient;
    }

    private MqttAndroidClient subscribeClient(final String subscriptionTopic) {
        final MqttAndroidClient mqttAndroidClient = this.mqttClient();
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    Log.v("TogetherApp", "Mqtt Client reconnect. ");
                    // Because Clean Session is true, we need to re-subscribe
                    subscribeToTopic(mqttAndroidClient, subscriptionTopic);
                } else {
                    Log.v("TogetherApp", "Mqtt Client Connected to: " + serverURI);
                }
            }
            @Override
            public void connectionLost(Throwable cause) {
                Log.v("TogetherApp", "Mqtt Client Connection was lost.");
            }
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.v("TogetherApp", "Mqtt Client Incoming message: " + new String(message.getPayload()));
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
        return mqttAndroidClient;
    }
    private void publishConnect(final MqttAndroidClient mqttAndroidClient, MqttConnectOptions mqttConnectOptions) {
        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.v("TogetherApp", "Mqtt Client Connected to Mqtt Server Success.");
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.v("TogetherApp", "Mqtt Client publish Connected to Mqtt Server Failed.");
                }
            });
        } catch (MqttException ex){
            Log.v("TogetherApp", "Mqtt Client Connected to Mqtt Server Error.");
            ex.printStackTrace();
        }
    }

    private void subscribeConnect(final MqttAndroidClient mqttAndroidClient, MqttConnectOptions mqttConnectOptions, final String subscriptionTopic) {
        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    subscribeToTopic(mqttAndroidClient, subscriptionTopic);
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.v("TogetherApp", "Mqtt Client subscribe Connected to Mqtt Server Failed.");
                }
            });
        } catch (MqttException ex){
            Log.v("TogetherApp", "Mqtt Client Connected to Mqtt Server Error.");
            ex.printStackTrace();
        }
    }

    private void subscribeToTopic(MqttAndroidClient mqttAndroidClient, String subscriptionTopic){
        try {
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.v("TogetherApp", "Mqtt Client Subscribed!");
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.v("TogetherApp", "Mqtt Client Failed to subscribe");
                }
            });
            // THIS DOES NOT WORK!
//            mqttAndroidClient.subscribe(subscriptionTopic, 0, new IMqttMessageListener() {
//                @Override
//                public void messageArrived(String topic, MqttMessage message) throws Exception {
//                    // message Arrived!
//                    System.out.println("Message: " + topic + " : " + new String(message.getPayload()));
//                }
//            });
        } catch (MqttException ex){
            Log.v("TogetherApp", "Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    private void publishMessage(MqttAndroidClient mqttAndroidClient, String publishTopic, String publishMessage){
        try {
            if(!mqttAndroidClient.isConnected()){
                Log.e("TogetherApp", " Message Published Error.");
                return;
            }
            MqttMessage message = new MqttMessage();
            message.setPayload(publishMessage.getBytes());
            message.setQos(0);
            message.setRetained(true);
            mqttAndroidClient.publish(publishTopic, message);
        } catch (MqttException e) {
            Log.e("TogetherApp", "Error Publishing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void Subscribe(String subscriptionTopic){
        MqttAndroidClient mqttAndroidClient = this.subscribeClient(subscriptionTopic);
        this.subscribeConnect(mqttAndroidClient, this.mqttConnectOptions(), subscriptionTopic);
    }

    public void Publish(String publishTopic, String publishMessage){
        MqttAndroidClient mqttAndroidClient = this.publishClient();
        this.publishConnect(mqttAndroidClient, this.mqttConnectOptions());
        this.publishMessage(mqttAndroidClient, publishTopic, publishMessage);
    }
}
