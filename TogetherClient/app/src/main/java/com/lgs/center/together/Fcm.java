package com.lgs.center.together;

/*
 * Firebase Cloud Messaging
 */
public class Fcm implements IMsgDriver {
    @Override
    public String SendMsg(String clientId, String MsgData) {
        return null;
    }

    @Override
    public String ListenMsg(String listerId, IMsgCallback f) {
        return null;
    }
}
