package com.demo.lixuan.mydemo.widgt.cludeCircleImage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.widgt.CircleImageView;


/**
 * 类 名: CircleView
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2017/7/17
 * author lixuan
 */

public class CircleTargetView extends RelativeLayout {
    private Paint mBgPaint = new Paint();

    PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG| Paint.FILTER_BITMAP_FLAG);
    private CircleImageView mHeadIcon;
    private TextView mText;
    private RequestManager mGlide;

    public CircleTargetView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public CircleTargetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initView(context);
        mBgPaint.setColor(Color.WHITE);
        mBgPaint.setAntiAlias(true);
    }

    public CircleTargetView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView(context);
        mBgPaint.setColor(Color.WHITE);
        mBgPaint.setAntiAlias(true);
    }



    private void initView(Context context) {
        mGlide = Glide.with(context);
        View view= View.inflate(context, R.layout.item_cirlce_target_view, this);
        mHeadIcon = (CircleImageView) view.findViewById(R.id.iv_user_head_icon);
        mText = (TextView) view.findViewById(R.id.tv_text);
    }

    public void setHeadIcon(String imagePath) {
        mGlide.load(imagePath).into(mHeadIcon);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int measuredWidth = getMeasuredWidth();
//        int measuredHeight = getMeasuredHeight();
//        int max = Math.max(measuredWidth, measuredHeight);
//                setMeasuredDimension(max, max);
////        int min = Math.min(measuredWidth, measuredHeight);
////        setMeasuredDimension(min, min);
    }

    @Override
    public void setBackgroundColor(int color) {
        // TODO Auto-generated method stub
        mBgPaint.setColor(color);
    }

    /**
     * 设置通知个数显示
     * @param text
     */
    public void setNotifiText(int text){
        //      if(text>99){
        //          String string = 99+"+";
        //          setText(string);
        //          return;
        //      }
        setText(text+"");
    }

    public  void  setCircleImageWithAndHeight(int with,int height){
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(with,height);
        mHeadIcon.setLayoutParams(params);
    }
    public void setText(String s) {
        mText.setText(s);
    }

    public void setTextColor(int textColor) {
        mText.setTextColor(textColor);
    }

    public void setSingleLine(boolean isSingle) {
        mText.setSingleLine(isSingle);
    }

    public CharSequence getText() {
        return mText.getText();
    }


    public Paint getPaint() {
        return mBgPaint;
    }



    @Override
    public void draw(Canvas canvas) {
        // TODO Auto-generated method stub
//        canvas.setDrawFilter(pfd);
//        canvas.drawCircle(getWidth()/2, getHeight()/2, Math.max(getWidth(), getHeight())/2, mBgPaint);
        super.draw(canvas);
    }


}
