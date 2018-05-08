package com.demo.lixuan.mydemo.widgt.ImageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 类名： MyRoundImageView
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:   Copyright  2018
 * 公       司:   深圳市旅联网络科技有限公司
 * version   2.0
 * date   2018/4/28
 * author lixuan
 * Created by elk-lx on 2018/4/28.
 */

public class MyRoundImageView extends ImageView {

    private int Radius=0;
    private Paint imagePaint;
    private Paint fillPaint;

    public MyRoundImageView(Context context) {
        super(context);
        init(context);
    }

    public MyRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }


    private void init(Context context) {
        imagePaint = new Paint();
        imagePaint.setAntiAlias(true);


        fillPaint = new Paint();
        fillPaint.setAntiAlias(true);
        fillPaint.setColor(Color.WHITE);
        fillPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mesureWidth = getMeasuredWidth();
        int mesureHeight = getMeasuredHeight();
        if (mesureWidth>=mesureHeight){
            Radius=mesureHeight/2;
        }else {
            Radius=mesureWidth/2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap src = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_4444);
        super.onDraw(new Canvas(src));


//        canvas.drawBitmap(src,0,0,imagePaint);
        Drawable drawable = getDrawable();
        if (drawable == null)
        {
            return;
        }

        Bitmap bmp = drawableToBitamp(drawable);
        imagePaint.setShader(new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        drawCircle(canvas);
//        canvas.drawBitmap(src,0,0,imagePaint);
        canvas.drawCircle(getWidth()/2,getHeight()/2-60,Radius,imagePaint);
    }

    private void drawCircle(Canvas canvas) {

        canvas.drawCircle(getWidth()/2,getHeight()/2,Radius,fillPaint);

    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitamp(Drawable drawable)
    {
        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
