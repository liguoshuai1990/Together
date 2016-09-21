package com.lgs.center.together.MsgDriver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lgs.center.together.R;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;

public class Mqtt implements IMsgDriver {
    private Context context;

    private MqttAndroidClient publishClient;
    private MqttAndroidClient subscribeClient;
    private String subscriptionTopic;

    public Mqtt(Context c) {
        context = c;
        publishClient = publishClient();
        subscribeClient = subscribeClient();
    }

    private MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);
        return mqttConnectOptions;
    }

    private MqttAndroidClient mqttClient(String clientId) {
        return new MqttAndroidClient(context, context.getString(R.string.mqtt_serverUrl), clientId);
    }

    private MqttAndroidClient publishClient(){
        MqttAndroidClient mqttAndroidClient = this.mqttClient("TogetherPublishClient");
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    Log.v("TogetherApp", "publish Mqtt Client reconnect. ");
                } else {
                    Log.v("TogetherApp", "publish Mqtt Client Connected to: " + serverURI);
                }
            }
            @Override
            public void connectionLost(Throwable cause) {
                Log.v("TogetherApp", "publish Mqtt Client Connection was lost.");
            }
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.v("TogetherApp", "publish Mqtt Client Incoming message: " + new String(message.getPayload()));
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
        return mqttAndroidClient;
    }

    private MqttAndroidClient subscribeClient() {
        MqttAndroidClient mqttAndroidClient = this.mqttClient("TogetherSubscribeClient");
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    Log.v("TogetherApp", "subscribe Mqtt Client reconnect. ");
                    // Because Clean Session is true, we need to re-subscribe
                    subscribeToTopic();
                } else {
                    Log.v("TogetherApp", "subscribe Mqtt Client Connected to: " + serverURI);
                }
            }
            @Override
            public void connectionLost(Throwable cause) {
                Log.v("TogetherApp", "subscribe Mqtt Client Connection was lost.");
            }
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.v("TogetherApp", "subscribe Mqtt Client Incoming message: " + new String(message.getPayload()));
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
        return mqttAndroidClient;
    }
    private void publishConnect(final String publishTopic, final String publishMessage) {
        try {
            Log.v("TogetherApp", "publish Connect start connect");
            publishClient.connect(this.mqttConnectOptions(), null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.v("TogetherApp", "Mqtt Client Connected to Mqtt Server Success.");
                    publishMessage(publishClient, publishTopic, publishMessage);
                    try {
                        Log.v("TogetherApp", "publish over Mqtt start DisConnected to Mqtt Server.");
                        publishClient.disconnect();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.v("TogetherApp", "Mqtt Client publish Connected to Mqtt Server Failed.");
                    Log.v("TogetherApp", exception.toString());
                }
            });
        } catch (MqttException ex){
            Log.v("TogetherApp", "Mqtt Client Connected to Mqtt Server Error.");
            ex.printStackTrace();
        }
    }

    private void subscribeConnect() {
        try {
            Log.v("TogetherApp", "subscribeConnect start connect");
            subscribeClient.connect(this.mqttConnectOptions(), null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.v("TogetherApp", "subscribeConnect onSuccess");
                    subscribeToTopic();
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

    private void subscribeToTopic(){
        try {
            Log.v("TogetherApp", "subscribeToTopic " + subscriptionTopic);
            subscribeClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.v("TogetherApp", "Mqtt Client Subscribed!");
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.v("TogetherApp", "Mqtt Client Failed to subscribe");
                }
            });
            subscribeClient.subscribe(subscriptionTopic, 0, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    // message Arrived!
                    Intent intent = new Intent("com.lgs.center.MSG_RECEIVER");
                    intent.putExtra("messageData", new String(message.getPayload()));
                    context.sendBroadcast(intent);
                }
            });
        } catch (MqttException ex){
            Log.v("TogetherApp", "Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    private void publishMessage(MqttAndroidClient mqttAndroidClient, String publishTopic, String publishMessage){
        try {
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

    @Override
    public String SendMsg(String MsgData) {
        Log.v("TogetherApp", " Mqtt Start SendMsg " + MsgData);
        publishConnect("Together/with", MsgData);
        return "";
    }

    @Override
    public String ListenMsg() {
        Log.v("TogetherApp", " Mqtt Start ListenMsg " + "Together/Group");
        this.subscriptionTopic = "Together/Group";
        subscribeConnect();
        return "";
    }
}
