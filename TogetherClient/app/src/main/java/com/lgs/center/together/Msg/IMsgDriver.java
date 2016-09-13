package com.lgs.center.together.Msg;

public interface IMsgDriver {
    String SendMsg(String clientId, String MsgData);
    String ListenMsg(String listerId, IMsgCallback f);
}
