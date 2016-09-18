package com.lgs.center.together;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.app.Service;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.lgs.center.together.Msg.*;

public class MsgService extends Service {
    Context context;
    private IMsgCallback iMsgCallback;


    private IMsgDriver getMsgDriver() {
        IMsgDriver msg;
        switch (context.getString(R.string.msg_driver)) {
            case "Fcm":
                msg = new Fcm();
                break;
            case "Mqtt":
                msg = new Mqtt(context);
                break;
            case "Sns":
                msg = new Sns(context);
                break;
            default:
                msg = new Sns(context);
        }
        return msg;
    }

    void SendMsg(String content) {
        IMsgDriver msg = getMsgDriver();
        msg.SendMsg(context.getString(R.string.senderid), content);
    }
    void ListenMsg(String topic, IMsgCallback f) {
        IMsgDriver msg = getMsgDriver();
        msg.ListenMsg(topic, f);

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
        /**
         * 获取当前Service的实例
         * @return
         */
        public MsgService getService() {
            return MsgService.this;
        }
    }


    /**
     * 广播接收器
     * @author len
     *
     */
    public class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //拿到进度，更新UI
            Log.v("Together", "MsgRecevier" + intent.getExtras().getString("messageData"));
            iMsgCallback.Callback(intent.getExtras().getString("messageData"));
        }

    }

}

