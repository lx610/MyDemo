package com.demo.lixuan.mydemo.widgt.pupwindow.keybordWindow;

import android.app.Activity;
import android.graphics.Rect;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.Utils.DeviceUtil;
import com.demo.lixuan.mydemo.Utils.SystemUtil;
import com.demo.lixuan.mydemo.Utils.UiUtils;
import com.demo.lixuan.mydemo.base.BasePopupWindow;


/**
 * className: VoiceInputWindow
 * description:语音输入弹窗，绑定editText，实现了避免光标被遮罩
 * author：lix
 * email：lixuan_1@163.com
 * date: 2019/8/22 11:23
 */
public class VoiceInputWindow extends BasePopupWindow {
    private static final String TAG = "VoiceInputWindow";
    private final View mRootView;

    private int mWindowTop;
    private View windowView;

    private VoiceKeyboard mVoiceKeyboard;
    private EditText editText;
    private int tempEditTop;
    private View mFlCover;
    private View mFlKeyboardContainer;

    public VoiceInputWindow(Activity activity) {
        super(activity);
        mRootView = activity.getWindow().getDecorView();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.window_voice_input;
    }

    @Override
    protected void preSetContentView(Activity currentActivity, View rootView) {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        isEnbleOutsideColse = false;
        isNeedDarkScreen = false;
    }

    @Override
    protected void initView(View contentView) {
        windowView = contentView;
        mVoiceKeyboard = contentView.findViewById(R.id.voice_keyboard);
        mFlKeyboardContainer = contentView.findViewById(R.id.fl_keyboard_container);
        mFlCover = contentView.findViewById(R.id.fl_cover);
        mFlCover.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    protected void initListener(View contentView) {
        mVoiceKeyboard.setOnnCloseClickListener(new VoiceKeyboard.OnCloseClickListener() {
            @Override
            public void onCloseClcik() {
                dismiss();
            }
        });

        mVoiceKeyboard.setOnFiishSpeakOnclcikListener(new VoiceKeyboard.OnFiishSpeakOnclcikListener() {
            @Override
            public void onClick() {
                dismiss();
            }
        });
    }

    /**绑定editText 实现了避免光标被遮罩
     * @param view
     */
    public void bingEditText(EditText view){
        editText = view;
        editText.setShowSoftInputOnFocus(false);
        if (editText!=null){
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (isShowing()){
                        scrollRootView(s,false);
                    }
                }
            });
        }

        mVoiceKeyboard.setOnVoiceToTextListener(new VoiceKeyboard.OnVoiceToTextListener() {
            String afterContent;
            String preContent;
            int index = 0;
            String oldContent = "";
            @Override
            public void onBeginOfSpeech() {

                setActivityTouchable(false);
                if (editText!=null) {
                    index = editText.getSelectionStart();//获取光标所在位置
                    oldContent = editText.getText().toString();
                    preContent = oldContent.substring(0,index);
                    afterContent = oldContent.substring(index,oldContent.length());
                }
            }

            @Override
            public void textResult(StringBuffer s) {
                if (editText!=null) {
                    String text = s.toString();
                    Editable edit = editText.getEditableText();//获取EditText的文字
                    String newString = preContent + text + afterContent;
                    edit.clear();
                    edit.append(newString);

                    //不能使用editText.setText()，会造成上滑滚动失效.
                    editText.setSelection(index + text.length());

                }
            }

            @Override
            public void onErrorOfSpeech() {
                setActivityTouchable(true);
            }

            @Override
            public void onEndOfSpeech() {
                setActivityTouchable(true);
            }

            @Override
            public void onStopOfSpeech() {
                setActivityTouchable(true);
            }
        });
    }

    /**
     * @param activityTouchable true 允许activity 点击 false 不允许
     */
    private void setActivityTouchable(boolean activityTouchable) {
//        没能生效，寻求其他解决办法
//        if (activityTouchable){
//            this.setFocusable(false);
//            this.setBackgroundDrawable(null);
//            mFlCover.setVisibility(View.GONE);
//            update();
//        }else {
//            this.setFocusable(true);
//            this.setBackgroundDrawable(null);
//          mFlCover.setVisibility(View.VISIBLE);
//          update();
//        }

    }

    /**根据文字的增多，把整个页面上移，露出输入框
     * @param s
     * @param isShowingUp
     */
    private void scrollRootView(Editable s,boolean isShowingUp) {
        int screenHight = DeviceUtil.getScreenHeight(mActivity);
        //语音输入框距离屏幕顶部的距离
        mWindowTop =screenHight - mFlKeyboardContainer.getHeight();

        //       参考了 ArrowKeyMovementMethod.getInstance()
        //文本第一行顶部距离EiteText顶部
        Spannable buffer = s;
        Layout layout = editText.getLayout();
        int lineTop = layout.getLineTop(layout.getLineForOffset(Selection.getSelectionEnd(buffer)));
        Log.d(TAG, "onCreate:lineTop: " +lineTop );

        //输入框的高度
        Rect rect = new Rect();
        int pageHight = editText.getGlobalVisibleRect(rect) ? rect.height() : 0;
        Log.d(TAG, "onCreate:pageHight: " + pageHight);

        //editText距离界面顶部的距离
        //随着界面向上推移，editTop会有抖动
        int editTop = rect.top;
         if (editTop>tempEditTop){
             tempEditTop = editTop;
         } else {
             editTop = tempEditTop;
         }
        Log.d(TAG, "onCreate:editTop: " + editTop);//输入框的高度

        //有文字区域的高度，文字越多，高度越高
        Rect lineRect = new Rect();
        editText.getLineBounds(0,lineRect);
        Log.d(TAG, "onCreate:foucRect: " + lineRect.top);
        int lineBottom = lineRect.top;

        if (isShowing()){//窗口正在显示时
            float distence;
            if (lineTop <pageHight){//当文本还没有填满editText的时候
                if (mWindowTop<(editTop + lineBottom + lineTop + UiUtils.dip2px( (int) editText.getTextSize()))){//文字末尾被遮盖的时候
                    distence = editTop - mWindowTop + lineBottom  + lineTop +  UiUtils.dip2px((int) editText.getTextSize()) ;
                    mRootView.scrollTo(0, (int) distence);
                }
            }else if (isShowingUp){//当窗口弹起时
                distence = editTop - mWindowTop + lineBottom  + pageHight + UiUtils.dip2px( (int) editText.getTextSize());
                mRootView.scrollTo(0, (int) distence);
            }
        }
    }

    @Override
    public void onWindowShowUp() {
        super.onWindowShowUp();
        tempEditTop = 0;
        Log.d(TAG, "onWindowShowUp: windowTop; " + mWindowTop);
        Log.d(TAG, "onWindowShowUp: getHeight(); " +  getHeight());
        if (editText!=null){
            editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scrollRootView(editText.getText(),true);

                }
            },300);
        }

    }

    @Override
    public void doOnDismiss() {
        super.doOnDismiss();
        mRootView.scrollTo(0,  0);
        if (editText!=null){
            editText.setShowSoftInputOnFocus(true);
        }
    }

    public void destorySpeaker(){
        if (mVoiceKeyboard!=null){

        }
    }

}
