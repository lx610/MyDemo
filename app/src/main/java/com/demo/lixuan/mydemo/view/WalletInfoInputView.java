package com.demo.lixuan.mydemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


/**
 * Created by lx on 2018/8/7.
 */

public class WalletInfoInputView extends FrameLayout {


    private int mEtInputType;
    private TextView mTvInfoTitle;
    private EditText mEtInput;
    private FrameLayout mFlRightContainer;
    private String mTitletext;
    private String mInputText;
    private String mInputHint;
    private boolean mIsEnableInput;
    private int mRightButtonType;
    TextView mRightTextWithIcon;

//===========================设置输入类型========================
    public static final int ET_INPUT_TYPE_TEXT = 0;
    public static final int ET_INPUT_TYPE_PHONE = 1;
    public static final int ET_INPUT_TYPE_BANK_CARD = 2;
    public static final int ET_INPUT_TYPE_ID_CARD = 3;
    private int maxLength = 50;
    private String digits;
    private int measureHight;
    private View mRoot;
    //===========================设置输入类型========================

    public WalletInfoInputView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public WalletInfoInputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.wallet_info_input_view);
        if (typedArray != null) {
            mTitletext = typedArray.getString(R.styleable.wallet_info_input_view_title_text);
            mInputText = typedArray.getString(R.styleable.wallet_info_input_view_input_text);
            mInputHint = typedArray.getString(R.styleable.wallet_info_input_view_input_hint);
            mIsEnableInput = typedArray.getBoolean(R.styleable.wallet_info_input_view_input_enable, false);
            mRightButtonType = typedArray.getInt(R.styleable.wallet_info_input_view_right_button, 0);
            mEtInputType = typedArray.getInt(R.styleable.wallet_info_input_view_input_type, 0);
            Log.d(TAG, "WalletInfoInputView: getHeight()" + getHeight());
            int heigh = typedArray.getInt(R.styleable.ConstraintSet_android_layout_height, 0);
            int defaultHeigh = typedArray.getInt(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_default, 0);
            Log.d(TAG, "WalletInfoInputView: android_layout_height" + heigh);
            Log.d(TAG, "WalletInfoInputView: defaultHeigh" + defaultHeigh);

            typedArray.recycle();
        }
        init(context);
//        initType();
        initData(context);
    }

    private void init(Context context) {
        mRoot = View.inflate(context, R.layout.view_wallet_info_input,this);
        mTvInfoTitle = (TextView) mRoot.findViewById(R.id.tv_info_title);
        mEtInput = (EditText ) mRoot.findViewById(R.id.et_info_input);
        mFlRightContainer = (FrameLayout) mRoot.findViewById(R.id.fl_right_button_container);
        mRoot = (FrameLayout) mRoot.findViewById(R.id.fl_root);
    }

    private void initData(Context context) {
        mTvInfoTitle.setText(mTitletext);
        mEtInput.setText(mInputText);
        mEtInput.setHint(mInputHint);
        mEtInput.setEnabled(mIsEnableInput);
        mFlRightContainer.removeAllViews();

//        switch (mRightButtonType){
//            case 0://null
//                break;
//            case 1://右边箭头
//                ImageView imageView = new ImageView(context);
//                imageView.setImageResource(R.drawable.icon_right_arrow_wallet);
//                mFlRightContainer.addView(imageView);
//                break;
//            case 2://相机图标
//                ImageView imageView2 = new ImageView(context);
//                imageView2.setImageResource(R.drawable.icon_camera_scan_wallet);
//                mFlRightContainer.addView(imageView2);
//                break;
//            case 3://右边箭头加文字提示
//                mRightTextWithIcon = new TextView(context);
//                Drawable drawable = context.getResources().getDrawable(R.drawable.icon_right_arrow_wallet);
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), (int) (drawable.getMinimumHeight()));
//                mRightTextWithIcon.setCompoundDrawables(null,null,drawable,null);
//                mRightTextWithIcon.setCompoundDrawablePadding(Utils.dp2px(context,7));
//                mFlRightContainer.addView(mFlRightContainer);
//                break;
//        }
    }

//    private void initType(){
//        mEtInput.setContentType(mEtInputType);
//    }



    public TextView getRightTextWithIcon() {
        return mRightTextWithIcon;
    }

    public TextView getTvInfoTitle() {
        return mTvInfoTitle;
    }

//    public ContentWithSpaceEditText getEtInput() {
//        return mEtInput;
//    }

    public FrameLayout getFlRightContainer() {
        return mFlRightContainer;
    }

    public void setEtInputType(int etInputType) {
        mEtInputType = etInputType;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
       measureHight = getMeasuredHeight();
        FrameLayout.LayoutParams params =new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,measureHight);
        mRoot.setLayoutParams(params);
    }
}
