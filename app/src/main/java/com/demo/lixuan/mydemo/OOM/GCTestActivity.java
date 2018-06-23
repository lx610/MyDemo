package com.demo.lixuan.mydemo.OOM;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/6/4.
 */

public class GCTestActivity extends LinearActivity {
    private static final String TAG = "GCTestActivity";
    Runtime mRuntime = Runtime.getRuntime();

    Object object1 = new Object();
    Object object2 = new Object();
    Object object3 = new Object();
    Object object4 = new Object();
    Object object5 =null;
    SoftReference softRef = new SoftReference(object1);
    WeakReference mWeakReference = new WeakReference(object2);
    PhantomReference mPhantomReference = new PhantomReference(object3,new ReferenceQueue());


    @Override
    public void initView(Bundle savedInstanceState) {
        final TextView logText= (TextView) generateTextButton("", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        TextView PrintHashCodeText= (TextView) generateTextButton("print hashCode", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick:object1.hashCode() =======================" + object1.hashCode());
                Log.d(TAG, "onClick:object2.hashCode() =======================" + object2.hashCode());
                Log.d(TAG, "onClick: object3.hashCode()=======================" + object3.hashCode());
                Log.d(TAG, "onClick:object4.hashCode() =======================" + object4.hashCode());

                logText.append(TAG + "onClick:object1.hashCode() =======================" + object1.hashCode() + "\n");
                logText.append(TAG + "onClick:object2.hashCode() =======================" + softRef.hashCode() + "\n");
                logText.append(TAG + "onClick:object3.hashCode() =======================" + mWeakReference.hashCode() + "\n");
                logText.append(TAG + "onClick:object4.hashCode() =======================" + mPhantomReference.hashCode() + "\n");
                logText.append(TAG + " ==============================================================="  + "\n");
            }
        });

        TextView CGCallText= (TextView) generateTextButton("CG Call", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.gc();
            }
        });


        mLlContainer.addView(PrintHashCodeText);
        mLlContainer.addView(CGCallText);
        mLlContainer.addView(logText);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
