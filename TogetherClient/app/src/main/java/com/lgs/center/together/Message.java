package com.lgs.center.together;

import android.content.Context;

class Message {
    Context context;

    void SendMsg(String content) {
        IMsgDriver mqtt = new Fcm();
        mqtt.SendMsg(context.getString(R.string.senderid), content);
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

