package com.lgs.center.together;

import android.content.Context;


import com.lgs.center.together.Msg.Fcm;
import com.lgs.center.together.Msg.IMsgCallback;
import com.lgs.center.together.Msg.IMsgDriver;
import com.lgs.center.together.Msg.Mqtt;

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

