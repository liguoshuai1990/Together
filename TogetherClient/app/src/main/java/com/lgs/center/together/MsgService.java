package com.lgs.center.together;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.app.Service;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.lgs.center.together.MsgDriver.*;

public class MsgService extends Service {
    Context context;
    private IMsgCallback iMsgCallback;
    IMsgDriver myMsgDriver;

    IMsgDriver GetMsgDriver() {
        IMsgDriver msg;
        switch (context.getString(R.string.msg_driver)) {
            case "Fcm":
                msg = new Fcm();
                break;
            case "Mqtt":
                msg = new Mqtt(context);
                break;
            default:
                msg = new Mqtt(context);
        }
        return msg;
    }

    void SendMsg(String content) {
        myMsgDriver.SendMsg(content);
    }
    void ListenMsg(IMsgCallback f) {
        myMsgDriver.ListenMsg();
        iMsgCallback = f;
        MsgReceiver msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.lgs.center.MSG_RECEIVER");
        registerReceiver(msgReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("Together", "MsgService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MsgBinder();
    }

    class MsgBinder extends Binder {
        MsgService getService() {
            return MsgService.this;
        }
    }

    public class MsgReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("Together", "MsgRecevier" + intent.getExtras().getString("messageData"));
            iMsgCallback.Callback(intent.getExtras().getString("messageData"));
        }
    }
}

