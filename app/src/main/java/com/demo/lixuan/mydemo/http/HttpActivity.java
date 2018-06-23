package com.demo.lixuan.mydemo.http;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;
import com.demo.lixuan.mydemo.http.httpUrlConnect.HttpConnection;
import com.demo.lixuan.mydemo.http.myVolley.MyVolley;
import com.demo.lixuan.mydemo.http.myVolley.StringRequest;
import com.demo.lixuan.mydemo.http.okhttp.OkhttpActivity;
import com.demo.lixuan.mydemo.http.webView.WebViewActivity;

import java.io.IOException;

/**
 * Created by Administrator on 2018/6/8.
 */

public class HttpActivity extends LinearActivity {

    private MyVolley.VollyRequestQueue mQueue;

    @Override
    public void initView(Bundle savedInstanceState) {
        mLlContainer.addView(generateTextButton("okhttpActivty", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityFromAct(OkhttpActivity.class);
            }
        }));

        mLlContainer.addView(generateTextButton("WebViewActivity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityFromAct(WebViewActivity.class);
            }
        }));


        final TextView textView = (TextView) generateTextButton("",null);
        mLlContainer.addView(generateTextButton("httpUrlConnect - taobao", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HttpConnection connection = new HttpConnection(          UrlConst.TAOBAO) {
                        @Override
                        public void responseData(int responseCode, String data) {
                            textView.setText("");
                            textView.setText(data);
                        }

                        @Override
                        public void responseError(int responseCode, String data) {

                        }
                    };
                    connection.getConnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
        mLlContainer.addView(generateTextButton("httpUrlConnect - baidu", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HttpConnection connection = new HttpConnection(UrlConst.BAIDU) {
                        @Override
                        public void responseData(int responseCode, String data) {
                            textView.setText("");
                            textView.setText(data);
                        }

                        @Override
                        public void responseError(int responseCode, String data) {

                        }
                    };
                    connection.getConnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
        mLlContainer.addView(generateTextButton("myVolly - baidu", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQueue = MyVolley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest =new StringRequest(MyVolley.VollyRequestQueue.GET, UrlConst.BAIDU, new StringRequest.Response.Listener() {
                    @Override
                    public void onResponse(int responseCode, String data) {
                        textView.append("==============   BAIDU  =============");
                        textView.append(data);
                    }
                }, new StringRequest.Response.ErrorListener() {
                    @Override
                    public void onErrorRespons(int responseCode, String data) {
                        textView.append("==============   BAIDU  =============");
                        textView.append(data);
                    }
                });
                mQueue.add(stringRequest);
            }
        }));
        mLlContainer.addView(generateTextButton("myVolly - taobao", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQueue = MyVolley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest =new StringRequest(MyVolley.VollyRequestQueue.GET, UrlConst.TAOBAO, new StringRequest.Response.Listener() {
                    @Override
                    public void onResponse(int responseCode, String data) {
                        textView.append("==============   TAOBAO  =============");
                        textView.append(data);
                    }
                }, new StringRequest.Response.ErrorListener() {
                    @Override
                    public void onErrorRespons(int responseCode, String data) {
                        textView.append("==============   TAOBAO  =============");
                        textView.append(data);
                    }
                });
                mQueue.add(stringRequest);
            }
        }));
        mLlContainer.addView(generateTextButton("start myVolley", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mQueue.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));




        ScrollView scrollView =new ScrollView(getApplicationContext());
        scrollView.addView(textView);
        mLlContainer.addView(scrollView);


    }



    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
