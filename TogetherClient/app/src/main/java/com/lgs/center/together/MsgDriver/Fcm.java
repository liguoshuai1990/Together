package com.lgs.center.together.MsgDriver;

import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.lgs.center.together.R;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * Firebase Cloud Messaging
 */
public class Fcm implements IMsgDriver {

    Context context;

    @Override
    public String SendMsg(String MsgData) {
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        AtomicInteger msgId = new AtomicInteger();
        fm.send(new RemoteMessage.Builder(context.getString(R.string.senderid) + "@gcm.googleapis.com")
                .setMessageId(Integer.toString(msgId.incrementAndGet()))
                .addData("my_message", MsgData)
                .addData("my_action","SAY_HELLO")
                .build());
        Log.v("Together", "FCM Send to " + context.getString(R.string.senderid) + " " + MsgData + " over.");
        return null;
    }

    @Override
    public String ListenMsg() {
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        fm.subscribeToTopic("Together/Group");
        return null;
    }
}
