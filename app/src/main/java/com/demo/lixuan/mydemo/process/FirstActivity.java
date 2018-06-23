package com.demo.lixuan.mydemo.process;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.Utils.SystemUtil;
import com.demo.lixuan.mydemo.Utils.UiUtils;
import com.demo.lixuan.mydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类名： FirstActivity
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

public class FirstActivity extends BaseActivity {
    private static final String TAG = "FirstActivity";
    public static final int MSG_FROM_SERVICE =1;

    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;

   private ServiceConnection mServiceConnection=new ServiceConnection() {
       public Messenger mService;

       @Override
       public void onServiceConnected(ComponentName name, IBinder service) {
           mService =new Messenger(service);
           Message msg = Message.obtain(null,MessageService.MSG_FROM_CLIENT);
           Bundle data = new Bundle();
           data.putString("msg","hello,this is client");
           msg.setData(data);
           //获取服务端的回答
           msg.replyTo=mGetReplayMessager;
           try {
               mService.send(msg);
           } catch (RemoteException e) {
               e.printStackTrace();
           }



       }

       @Override
       public void onServiceDisconnected(ComponentName name) {

       }
   };



    @Override
    public int getLayoutResId() {
        return R.layout.activity_only_linearlayout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mLlContainer.addView( generateTextButton("sencondActivity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this,SecondActivity.class));
            }
        }));
        mLlContainer.addView( generateTextButton("ThirdActivity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this,ThirdActivity.class));
            }
        }));
        mLlContainer.addView( generateTextButton("send Msg", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this,MessageService.class);
                bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
            }
        }));

    }

    @Override
    public void initData() {
        String processName = SystemUtil.getProcessName(getApplication());
        Log.d(TAG, "processName: " + processName);


    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private Messenger mGetReplayMessager = new Messenger(new Messengerhandler());

    private static class Messengerhandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_FROM_SERVICE:
                    Log.d(TAG, "handleMessage: ==============" + msg.getData());
                    Bundle bundleService = msg.getData();
                   String reply= (String) bundleService.get("reply");
                    Log.d(TAG, "handleMessage: ================" + reply);
                    UiUtils.makeText(reply );
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
