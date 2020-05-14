package com.demo.lixuan.mydemo.widgt.textView.stringSpan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import android.text.style.ImageSpan;
import android.widget.TextView;


/**
 * className: ImageBackgroundSpan
 * description:可以设置背景图片的span
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/5/12 11:49
 */
public class ImageTagSpan extends ImageSpan {

    double scanSize = 0.91;

    private int resTextColor;
    Context mContext;
    private int textSize = 20;//默认
    private int color = Color.GRAY;
    float textboundhight;
    float textY;
    TextView orgTextView;



    /**
     *
     * @param d 接收图片
     * @param
     */
    public ImageTagSpan(Drawable d, Context context, TextView orginTextSetting, float textSizeSp, int resIdTextColor) {
        super(d);
        mContext = context;
        float density = context.getResources().getDisplayMetrics().density;
        textSize =  (int) (density * textSizeSp + 0.5f);
        orgTextView = orginTextSetting;
        resTextColor = resIdTextColor;
    }


    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y,
                     int bottom, Paint paint) {
        //获取需要设置样式的字符串
        String str = text.subSequence(start, end).toString();
        //得到宽高
        Rect bounds = new Rect();
        //先根据TextView 属性 设置字体大小,计算容器宽高
        paint.setTextSize(orgTextView.getTextSize());
        //行间距
        float lineSpace = orgTextView.getLineSpacingMultiplier();

        //获得字符串所占空间大小
        paint.getTextBounds(str, 0, str.length(), bounds);
        //得到宽高
        int textViewHeight = bounds.height();
        int textWidth = bounds.width();

        //设置背景绘制宽高 根据字符串大小扩大一定比例 否则会紧贴字符
        getDrawable().setBounds(0, (int) (top ), (int) (bounds.width()), (int)(textViewHeight));


        //调用父类draw绘制背景
        super.draw(canvas, str, start, end, x, top, y, bottom, paint);
        //绘制文本
        //文本颜色
        paint.setColor(mContext.getResources().getColor(resTextColor));
        //文本字体
        paint.setTypeface(Typeface.create("normal", Typeface.NORMAL));

        //得到之前设置的背景图的大小
        Rect bounds1 = getDrawable().getBounds();

        //重新设置字体大小
        paint.setTextSize(textSize);
        //得到文字宽高
        Rect textbound = new Rect();
        //获得字符串所占空间大小
        paint.getTextBounds(str, 0, str.length(), textbound);

        //根据背景图算出 字符串居中绘制的位置
        float textX = x + (bounds1.width() -textbound.width())/ 2;
        if (textboundhight == 0) {
            textboundhight = textbound.height();

            float density = mContext.getResources().getDisplayMetrics().density;
            int bottomPadding = 1;
            int bottomDestence = (int) (density * bottomPadding + 0.5f);
            //   Y =   总高度 - 外框到底部的高度 - 文字基线到外框的高度 - 底部位移
            textY = (float) ((bottom - top) - ((bottom - top) -bounds.height())/2 - (bounds.height() - textboundhight)/2 ) - bottomDestence;

        }

        //绘制字符串
        canvas.drawText(str, textX, textY, paint);

    }
}
