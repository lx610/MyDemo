package com.demo.lixuan.mydemo.widgt.textView.textView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.widgt.textView.stringSpan.ImageTagSpan;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;

@SuppressLint("AppCompatCustomView")
public class ReadMoreTextView extends TextView {

    private final String TAG = "ReadMoreTextView";

    private static final int TRIM_MODE_LINES = 0;
    private static final int TRIM_MODE_LENGTH = 1;
    private static final int DEFAULT_TRIM_LENGTH = 240;
    private static final int DEFAULT_TRIM_LINES = 2;
    private static final int INVALID_END_INDEX = -1;
    private static final boolean DEFAULT_SHOW_TRIM_EXPANDED_TEXT = true;
    private static final String ELLIPSIZE = "... ";

    private CharSequence text;
    private BufferType bufferType;

    //标识展开还是收起
    private boolean readMore = true;

    //展开
    private CharSequence trimCollapsedText;
    //收起
    private CharSequence trimExpandedText;

    //展开收起点击事件
    private ReadMoreClickableSpan viewMoreSpan;
    //展开收起颜色
    private int colorClickableText;

    //是否要显示收起按钮
    private boolean showTrimExpandedText;

    //是行数还是字数类型
    private int trimMode;

    private int lineEndIndex;
    //初始化行数
    private int trimLines;
    //初始化字数
    private int trimLength;

    private CustomLinkMovementMethod mMoveMentMethod;
    private List<StringSpanBean> mSpanableStringList;

    public ReadMoreTextView(Context context) {
        this(context, null);
    }

    public ReadMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ReadMoreTextView);

        int resourceIdTrimCollapsedText = typedArray.getResourceId(R.styleable.ReadMoreTextView_trimCollapsedText, R.string.read_more);
        int resourceIdTrimExpandedText = typedArray.getResourceId(R.styleable.ReadMoreTextView_trimExpandedText, R.string.read_less);

        this.trimCollapsedText = getResources().getString(resourceIdTrimCollapsedText);
        this.trimExpandedText = getResources().getString(resourceIdTrimExpandedText);
        this.colorClickableText = typedArray.getColor(R.styleable.ReadMoreTextView_colorClickableText, ContextCompat.getColor(context, R.color.blue_4a81fb));

        this.trimLength = typedArray.getInt(R.styleable.ReadMoreTextView_trimLength, DEFAULT_TRIM_LENGTH);
        this.trimLines = typedArray.getInt(R.styleable.ReadMoreTextView_trimLines, DEFAULT_TRIM_LINES);

        this.showTrimExpandedText = typedArray.getBoolean(R.styleable.ReadMoreTextView_showTrimExpandedText, DEFAULT_SHOW_TRIM_EXPANDED_TEXT);
        this.trimMode = typedArray.getInt(R.styleable.ReadMoreTextView_trimMode, TRIM_MODE_LINES);

        typedArray.recycle();

        viewMoreSpan = new ReadMoreClickableSpan();
        mSpanableStringList = new ArrayList<>();

        onGlobalLayoutLineEndIndex();
        setText();
    }

    private void setText() {
        super.setText(getDisplayableText(), bufferType);
        setMovementMethod(CustomLinkMovementMethod.getInstance());
        setHighlightColor(Color.TRANSPARENT);
    }

    private CharSequence getDisplayableText() {
        return getTrimmedText(text);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        this.text = text;
        bufferType = type;
        setText();
    }

    private CharSequence getTrimmedText(CharSequence text) {
        if (trimMode == TRIM_MODE_LENGTH) {
            if (text != null && text.length() > trimLength) {
                if (readMore) {
                    return updateCollapsedText();
                } else {
                    return updateExpandedText();
                }
            }
        }

        if (trimMode == TRIM_MODE_LINES) {
            if (text != null && lineEndIndex > 0) {
                if (readMore) {
                    if (getLayout() != null && getLayout().getLineCount() > trimLines) {
                        Log.d(TAG, "readMore = " + readMore + "getLineCount = " + getLayout().getLineCount()
                         + "trimLines = " + trimLines);
                        return updateCollapsedText();
                    } else {
                        if (getLayout() != null)
                            Log.d(TAG, "readMore = " + readMore + "getLineCount = " + getLayout().getLineCount()
                                + "trimLines = " + trimLines);
                        else
                            Log.d(TAG, "readMore = " + readMore + "trimLines = " + trimLines);
                    }
                } else {
                    return updateExpandedText();
                }
            }
        }
        return text;
    }

    private CharSequence updateCollapsedText() {
        int trimEndIndex = text.length();
        Log.e(TAG, "text = " + text);
        switch (trimMode) {
            case TRIM_MODE_LINES:
                trimEndIndex = lineEndIndex - (ELLIPSIZE.length() + trimCollapsedText.length() + 1);
                Log.e(TAG, "lineEndIndex = " + lineEndIndex + ",trimEndIndex = " + trimEndIndex);
                if (trimEndIndex < 0) {
                    trimEndIndex = text.length();
                }
                break;
            case TRIM_MODE_LENGTH:
                trimEndIndex = trimLength + 1;
                break;
        }
        SpannableStringBuilder s = new SpannableStringBuilder(text, 0, trimEndIndex)
                .append(ELLIPSIZE)
                .append(trimCollapsedText);
        return addClickableSpan(s, trimCollapsedText);
    }

    private CharSequence updateExpandedText() {
        if (showTrimExpandedText) {
            SpannableStringBuilder s = new SpannableStringBuilder(text, 0, text.length()).append("  " +trimExpandedText);
            return addClickableSpan(s, trimExpandedText);
        }
        return text;
    }

    private CharSequence addClickableSpan(SpannableStringBuilder s, CharSequence trimText) {

        for (int i = 0; i < mSpanableStringList.size(); i++) {
            StringSpanBean spanBean = mSpanableStringList.get(i);
            s.setSpan(spanBean.getSpan(), spanBean.getStartIndex(),spanBean.getEndIndex(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        s.setSpan(viewMoreSpan, s.length() - trimText.length(), s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return s;
    }


    public void setTrimLength(int trimLength) {
        this.trimLength = trimLength;
        setText();
    }

    public void setColorClickableText(int colorClickableText) {
        this.colorClickableText = colorClickableText;
    }

    public void setTrimCollapsedText(CharSequence trimCollapsedText) {
        this.trimCollapsedText = trimCollapsedText;
    }

    public void setTrimExpandedText(CharSequence trimExpandedText) {
        this.trimExpandedText = trimExpandedText;
    }

    public void setTrimMode(int trimMode) {
        this.trimMode = trimMode;
    }

    public void setTrimLines(int trimLines) {
        this.trimLines = trimLines;
    }

    public void addSpan(SpannableString imageSpan) {


    }

    public void clearSpanList(){
        mSpanableStringList.clear();
    }

    public void addSpannableString(StringSpanBean imageSpan) {
        mSpanableStringList.add(imageSpan);
    }


    private class ReadMoreClickableSpan extends ClickableSpan {

        @Override
        public void onClick(View widget) {
            readMore = !readMore;
            if (mOnShowButtonCliclListener != null){
                mOnShowButtonCliclListener.onClick(widget, readMore);
            }
            setText();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(colorClickableText);
        }
    }

    public void onGlobalLayoutLineEndIndex() {
        if (trimMode == TRIM_MODE_LINES) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ViewTreeObserver obs = getViewTreeObserver();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        obs.removeOnGlobalLayoutListener(this);
                    } else {
                        obs.removeGlobalOnLayoutListener(this);
                    }
                    refreshLineEndIndex();
                    setText();
                }
            });
        }
    }

    private void refreshLineEndIndex() {
        try {
            if (trimLines == 0) {
                lineEndIndex = getLayout().getLineEnd(0);
            } else if (trimLines > 0 && getLineCount() >= trimLines) {
                lineEndIndex = getLayout().getLineEnd(trimLines - 1);
            } else {
                lineEndIndex = INVALID_END_INDEX;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public boolean isReadMore() {

        return readMore;
    }

    public void setReadMore(boolean readMore) {

        this.readMore = readMore;
    }

    OnShowButtonCliclListener mOnShowButtonCliclListener;

    public void setOnShowButtonCliclListener(OnShowButtonCliclListener onShowButtonCliclListener) {

        mOnShowButtonCliclListener = onShowButtonCliclListener;
    }

    public interface OnShowButtonCliclListener{
        void onClick(View view, boolean readMore);
    }

    public static class StringSpanBean{
        Object span;
        int startIndex;
        int endIndex;

        public Object getSpan() {

            return span;
        }

        public void setSpan(Object span) {

            this.span = span;
        }

        public int getStartIndex() {

            return startIndex;
        }

        public void setStartIndex(int startIndex) {

            this.startIndex = startIndex;
        }

        public int getEndIndex() {

            return endIndex;
        }

        public void setEndIndex(int endIndex) {

            this.endIndex = endIndex;
        }
    }
}
