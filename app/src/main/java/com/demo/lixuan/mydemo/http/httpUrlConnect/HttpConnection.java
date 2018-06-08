package com.demo.lixuan.mydemo.http.httpUrlConnect;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/6/8.
 */

public abstract class HttpConnection  {

    private final HttpURLConnection mConnection;
    private final Handler mHandler;

    public HttpConnection(String urlString) throws IOException,MalformedURLException{
        mHandler = new Handler(){
            @Override
            public String getMessageName(Message message) {
                return super.getMessageName(message);
            }
        };

        URL url = null;
             url =new URL(urlString);
        mConnection = (HttpURLConnection) url.openConnection();
        mConnection.setConnectTimeout(15000);
        mConnection.setReadTimeout(15000);
        mConnection.setRequestMethod("POST");
        mConnection.setRequestProperty("Connection","Keep-Alive");
        mConnection.setDoInput(true);
        mConnection.setDoOutput(true);

    }

    public void getConnect() throws IOException {
        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mConnection.connect();
                    final int responsCode = mConnection.getResponseCode();
                    if (responsCode == HttpURLConnection.HTTP_OK||responsCode ==302){
                        //得到响应流
                        InputStream inputStream = mConnection.getInputStream();
                        Object content = mConnection.getContent();
                        Map<String, List<String>> heads = mConnection.getHeaderFields();
                        final StringBuffer buffer =new StringBuffer();
                        buffer.append("content - type ==" + mConnection.getContentType() + "\n");
                        Set<String> headKey = heads.keySet();
                        Iterator<String> headIterator = headKey.iterator();
                        buffer.append("================head==================================" + "\n");
                        while (headIterator.hasNext()){
                            String headKeyString = headIterator.next();
                            List headLsit = heads.get(headKeyString);
                            int size = headLsit.size();
                            for (int i = 0; i < size; i++) {
                                String headContent = (String) headLsit.get(i);
                                buffer.append(headKeyString + " : " + headContent + "\n");
                            }

                        }
                        buffer.append("================head==================================" + "\n");
                        //将响应流转换成字符串
                        String result = is2String(inputStream);//将流转换为字符串。
                        buffer.append(result);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                responseData(responsCode,buffer.toString());
                            }
                        });
                        Log.d("kwwl","result============="+buffer.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private String is2String(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String str = sb.toString();
        return str;
    }

    abstract public void responseData(int responseCode, String data);
}
