package com.demo.lixuan.mydemo.widgt.pupwindow.keybordWindow;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demo.lixuan.mydemo.R;


/**
 * className: VoiceKeyboard
 * description:语音输入键盘
 * author：lix
 * email：lixuan_1@163.com
 * date: 2019/8/14 14:50
 */
public class VoiceKeyboard extends LinearLayout {
    private static final String TAG = "VoiceKeyboard";
    private View mRootView;
    private Context mContext;


    public VoiceKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);

    }

    public VoiceKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public void init(Context context, AttributeSet attrs){
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.view_voice_keyboard, VoiceKeyboard.this,true);

    }


    OnCloseClickListener mOnnCloseClickListener;

    public void setOnnCloseClickListener(OnCloseClickListener onnCloseClickListener) {
        mOnnCloseClickListener = onnCloseClickListener;
    }

    public interface OnCloseClickListener {
        void onCloseClcik();
    }
    OnVoiceToTextListener mOnVoiceToTextListener;

    public void setOnVoiceToTextListener(OnVoiceToTextListener onVoiceToTextListener) {
        mOnVoiceToTextListener = onVoiceToTextListener;
    }

    public interface OnVoiceToTextListener{
        void onBeginOfSpeech();
        void textResult(StringBuffer s);

        void onErrorOfSpeech();

        void onEndOfSpeech();

        void onStopOfSpeech();
    }

    OnFiishSpeakOnclcikListener mOnFiishSpeakOnclcikListener;
    public interface OnFiishSpeakOnclcikListener {
        void onClick();
    }

    public void setOnFiishSpeakOnclcikListener(OnFiishSpeakOnclcikListener onFiishSpeakOnclcikListener) {
        mOnFiishSpeakOnclcikListener = onFiishSpeakOnclcikListener;
    }
}
