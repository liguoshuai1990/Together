package com.lgs.center.together;

import android.content.Context;

public class Message {
    public Context context;

    public void SendMsg(String topic, String content) {
        MqttMsg mqttMsg = new MqttMsg();
        mqttMsg.context = this.context;
        mqttMsg.Publish(topic, content);
    }
    public void ResvMsg(String topic) {
        MqttMsg mqttMsg = new MqttMsg();
        mqttMsg.context = this.context;
        mqttMsg.Subscribe(topic);
    }
}

