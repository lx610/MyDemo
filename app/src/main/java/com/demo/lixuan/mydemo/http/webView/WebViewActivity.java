package com.demo.lixuan.mydemo.http.webView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;

/**
 * Created by Administrator on 2018/6/21.
 */

public class WebViewActivity extends LinearActivity {

    String url = "http://192.168.0.232:8090/elk-wechat-officalaccount-develop/view/touristInviteFriend/myIncome.html";
    @Override
    public void initView(Bundle savedInstanceState) {
        final WebView webView =new WebView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);

        mLlContainer.addView(generateTextButton("1", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "window.location.href=http://192.168.0.232:8090/elk-wechat-officalaccount-develop/view/touristInviteFriend/myIncome.html";
                webView.loadDataWithBaseURL(null, "<html><script type=\"text/javascript\">" + content + "</script></html>", "text/html", "utf-8", null);
            }
        }));

        mLlContainer.addView(generateTextButton("1", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "window.location.href=http://192.168.0.232:8090/elk-wechat-officalaccount-develop/view/touristInviteFriend/myIncome1.html";
                webView.loadDataWithBaseURL(null, "<html><script type=\"text/javascript\">" + content + "</script></html>", "text/html", "utf-8", null);
            }
        }));


        mLlContainer.addView(generateTextButton("1", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "<p><img src=http://192.168.0.232:8089/group1/M00/00/D5\\/wKgA6FouS3-AULpvAAIhSMsMDN8677.bmp\\\" title=\\\"888.bmp\\\" alt=\\\"888.bmp\\\"\\/><\\/p>";
                webView.loadDataWithBaseURL(null, "<html><script type=\"text/javascript\">" + content + "</script></html>", "text/html", "utf-8", null);
            }
        }));

//        webView.loadUrl(url);

        mLlContainer.addView(webView);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
