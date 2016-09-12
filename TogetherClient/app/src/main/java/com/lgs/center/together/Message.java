package com.lgs.center.together;

import android.content.Context;

public class Message {
    public Context context;

    public void SendMsg(String topic, String content) {
        Mqtt mqtt = new Mqtt();
        mqtt.context = this.context;
        mqtt.Publish(topic, content);
    }
    public void ResvMsg(String topic) {
        IMsgDriver mqtt = new Mqtt();
        mqtt.ListenMsg("",  new IMsgCallback() {
            @Override
            public void Callback(String MsgData) {
                System.out.println(MsgData);
            }
        });
    }
}

