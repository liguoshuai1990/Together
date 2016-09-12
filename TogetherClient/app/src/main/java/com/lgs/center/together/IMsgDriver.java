package com.lgs.center.together;

interface IMsgCallback {
    void Callback(String MsgData);
}

public interface IMsgDriver {
    String SendMsg(String clientId, String MsgData);
    String ListenMsg(String listerId, IMsgCallback f);
}
