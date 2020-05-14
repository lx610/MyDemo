package com.demo.lixuan.mydemo.widgt.textView.textView;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * className: CustomLinkMovementMethod
 * description:用于解决ReadMoreTextView 和 自己的clickSpan点击事件冲突的问题，需要ReadMoreText 调用setMovementMethod（）方法
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/5/13 10:54
 */
public class CustomLinkMovementMethod  extends LinkMovementMethod {
    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
//        boolean b = super.onTouchEvent(widget,buffer,event);
//        //解决点击事件冲突问题
//        if(!b && event.getAction() == MotionEvent.ACTION_UP){
//            ViewParent parent = widget.getParent();//处理widget的父控件点击事件
//            if (parent instanceof ViewGroup) {
//                return ((ViewGroup) parent).performClick();
//            }
//        }
//        return b;
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {

            int x = (int) event.getX();
            int y = (int) event.getY();
            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();
            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
            if (link.length > 0) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(widget);
                } else {
                    Selection.setSelection(buffer, buffer.getSpanStart(link[0]),
                            buffer.getSpanEnd(link[0]));
                }
                return true;
            } else {
                Selection.removeSelection(buffer);
            }
        }

        return false;

    }

    public static CustomLinkMovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new CustomLinkMovementMethod();

        return sInstance;
    }

    @Override
    public void initialize(TextView widget, Spannable text) {
        Selection.removeSelection(text);
    }

    private static CustomLinkMovementMethod sInstance;
}
