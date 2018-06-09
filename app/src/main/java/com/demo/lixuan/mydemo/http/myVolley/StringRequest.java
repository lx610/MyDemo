package com.demo.lixuan.mydemo.http.myVolley;


import com.demo.lixuan.mydemo.http.httpUrlConnect.HttpConnection;

import java.io.IOException;

/**
 * Created by Administrator on 2018/6/8.
 */

public class StringRequest {

    private HttpConnection mHttpConnection;

    public StringRequest(String UrlType, String Url, final Response.Listener response, final Response.ErrorListener errorListener) {
        try {
            mHttpConnection = new HttpConnection(Url) {
                @Override
                public void responseData(int responseCode, String data) {
                    response.onResponse(responseCode, data);
                }

                @Override
                public void responseError(int responseCode, String data) {
                    errorListener.onErrorRespons( responseCode, data);
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean doConnectTask(){
        boolean isConnect = false;
        try {
            isConnect= mHttpConnection.getConnect()[0];
        } catch (IOException e) {
            e.printStackTrace();
            isConnect=false;
        }
        return isConnect;
    }

   public interface Response {
        public interface Listener {
            void onResponse(int responseCode, String data);
        }

        public interface ErrorListener {
            void onErrorRespons(int responseCode, String data);

        }

    }
}
