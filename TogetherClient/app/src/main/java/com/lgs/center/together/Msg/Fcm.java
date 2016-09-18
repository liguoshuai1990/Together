package com.lgs.center.together.Msg;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * Firebase Cloud Messaging
 */
public class Fcm implements IMsgDriver {

    @Override
    public String SendMsg(String clientId, String MsgData) {
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        AtomicInteger msgId = new AtomicInteger();
        fm.send(new RemoteMessage.Builder(clientId + "@gcm.googleapis.com")
                .setMessageId(Integer.toString(msgId.incrementAndGet()))
                .addData("my_message", MsgData)
                .addData("my_action","SAY_HELLO")
                .build());
        Log.v("Together", "FCM Send to " + clientId + " " + MsgData + " over.");
        return null;
    }

    @Override
    public String ListenMsg(String listerId, IMsgCallback f) {
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        fm.subscribeToTopic("" + listerId);

        

        return null;
    }
}
