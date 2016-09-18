package com.lgs.center.together.Msg;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class TogetherHttp implements IMsgDriver {
    @Override
    public String SendMsg(String clientId, String MsgData) {
        try {
            URL url = new URL("http://www.51cto.com/index.jsp?par=123456");
            HttpURLConnection urlConn=(HttpURLConnection)url.openConnection();
            //设置输入和输出流
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);     //设置请求方式为POST
            urlConn.setRequestMethod("POST");  //POST请求不能使用缓存
            urlConn.setUseCaches(false);  //关闭连接
            urlConn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String ListenMsg(String listerId, IMsgCallback f) {
        return null;
    }
}
