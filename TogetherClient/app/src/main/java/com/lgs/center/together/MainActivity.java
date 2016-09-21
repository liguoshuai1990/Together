package com.lgs.center.together;

import android.content.*;
import android.os.IBinder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.lgs.center.together.MsgDriver.IMsgCallback;

public class MainActivity extends AppCompatActivity {

    private TextView topicGroupText;
    private TextView topicText;
    private TextView togetherButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("Together", "MainActivity Start onCreate.");

        setContentView(R.layout.activity_main);
        topicGroupText = (TextView)findViewById(R.id.topicGroupTextView);
        topicText = (EditText)findViewById(R.id.topicEditText);
        togetherButton = (Button)findViewById(R.id.togetherButton);
        context = this.getApplicationContext();

        //绑定Service
        Intent msgIntent = new Intent(this, MsgService.class);
        bindService(msgIntent, MsgServerconnection, Context.BIND_AUTO_CREATE);

        Log.v("Together", "MainActivity onCreate.");

    }

    private ServiceConnection MsgServerconnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.v("Together", "MsgServerconnection onServiceConnected.");

            //返回一个MsgService对象
            final MsgService msgService = ((MsgService.MsgBinder)service).getService();
            msgService.context = context;
            msgService.myMsgDriver = msgService.GetMsgDriver();
            msgService.ListenMsg(new IMsgCallback() {
                @Override
                public void Callback(String MsgData) {
                    System.out.println(MsgData);
                    topicGroupText.setText(MsgData);
                }
            });

            togetherButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                /* 发送消息 */
                    msgService.SendMsg(topicText.getText().toString());
                    Log.v("Together", "over on click button.");
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        unbindService(MsgServerconnection);
        super.onDestroy();
    }
}
