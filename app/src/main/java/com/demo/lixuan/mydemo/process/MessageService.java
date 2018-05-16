package com.demo.lixuan.mydemo.process;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.demo.lixuan.mydemo.Utils.UiUtils;

/**
 * 类名： MessageService
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:   Copyright  2018
 * 公       司:   深圳市旅联网络科技有限公司
 * version   2.0
 * date   2018/5/15
 * author lixuan
 * Created by elk-lx on 2018/5/15.
 */

public class MessageService extends Service {
    public final static int MSG_FROM_CLIENT=0;
    private static final String TAG = "MessageService";


    Messenger mMessenger =new Messenger(new MessageHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }


    public static class MessageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_FROM_CLIENT:
                    Log.d(TAG, "handleMessage: MessageService:=======================" + msg.getData());
                    Bundle clientbundle=msg.getData();
                    String message= (String) clientbundle.get("msg");
                    UiUtils.makeText("MessageService :===" + message +"");

                    Messenger client =msg.replyTo;
                    Message msgReply = Message.obtain(null,FirstActivity.MSG_FROM_SERVICE);
                    Bundle bundleReply = new Bundle();
                    bundleReply.putString("reply","消息 已经收到，稍后回复你");
                    msgReply.setData(bundleReply);
                    try {
                        client.send(msgReply);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }


        }
    }
}
