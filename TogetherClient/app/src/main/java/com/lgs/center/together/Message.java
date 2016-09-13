package com.lgs.center.together;

import android.content.Context;


import com.lgs.center.together.Msg.Fcm;
import com.lgs.center.together.Msg.IMsgCallback;
import com.lgs.center.together.Msg.IMsgDriver;
import com.lgs.center.together.Msg.Mqtt;

class Message {
    Context context;

    void SendMsg(String content) {
        IMsgDriver msg;
        switch (context.getString(R.string.msg_driver)) {
            case "Fcm":
                msg = new Fcm();
                break;
            case "Mqtt":
                msg = new Mqtt();
                break;
            default:
                msg = new Fcm();
        }
        msg.SendMsg(context.getString(R.string.senderid), content);
    }
    void ListenMsg(String topic, IMsgCallback f) {
        IMsgDriver msg;
        switch (context.getString(R.string.msg_driver)) {
            case "Fcm":
                msg = new Fcm();
                break;
            case "Mqtt":
                msg = new Mqtt();
                break;
            default:
                msg = new Fcm();
        }
        msg.ListenMsg(topic, f);
    }
}

