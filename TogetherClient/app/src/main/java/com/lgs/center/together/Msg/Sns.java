package com.lgs.center.together.Msg;

import android.content.Context;
import com.lgs.center.together.R;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class Sns implements IMsgDriver {

    private Context context;

    public Sns(Context c) {
        context = c;
    }


    private AWSCredentials awsCredentials = new AWSCredentials() {
        @Override
        public String getAWSAccessKeyId() {
            return context.getString(R.string.AWSAccessKeyId);
        }

        @Override
        public String getAWSSecretKey() {
            return context.getString(R.string.AWSSecretKey);
        }
    };

    private AmazonSNS client = new AmazonSNSClient(awsCredentials);

    @Override
    public String SendMsg(String clientId, String MsgData) {

        PublishRequest request = new PublishRequest("topic", "message");
//        request.setMessageStructure("json");
//        request.setMessageAttributes();
//        request.setTargetArn();
//        request.setMessage();


        client.publish(request);

        return null;
    }

    @Override
    public String ListenMsg(String listerId, IMsgCallback f) {

        client.setEndpoint("https://sns.us-west-2.amazonaws.com");
        SubscribeRequest request = new SubscribeRequest("topic", "protocol", "endport");

        //subscribe to an SNS topic
        SubscribeRequest subRequest = new SubscribeRequest(context.getString(R.string.TopicARN),
                "application",
                "name@example.com");
        client.subscribe(subRequest);
        //get request id for SubscribeRequest from SNS metadata
        System.out.println("SubscribeRequest - " + client.getCachedResponseMetadata(subRequest));
        System.out.println("Check your email and confirm subscription.");

        return null;
    }

}
