package com.lgs.center.together;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lgs.center.together.Msg.IMsgCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView topicGroupText = (TextView)findViewById(R.id.topicGroupTextView);
        final EditText topicText = (EditText)findViewById(R.id.topicEditText);
        Button togetherButton = (Button)findViewById(R.id.togetherButton);
        final Message mqttMessage = new Message();
        mqttMessage.context = this.getApplicationContext();;
        mqttMessage.ListenMsg("Together.ResvGroupMsg", new IMsgCallback() {
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
                mqttMessage.SendMsg(topicText.getText().toString());
            }
        });

    }
}
