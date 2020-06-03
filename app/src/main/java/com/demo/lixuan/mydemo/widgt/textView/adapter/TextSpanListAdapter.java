package com.demo.lixuan.mydemo.widgt.textView.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.http.webView.WebViewActivity;
import com.demo.lixuan.mydemo.widgt.textView.stringSpan.ImageTagSpan;
import com.demo.lixuan.mydemo.widgt.textView.textView.ExpandableTextView;
import com.demo.lixuan.mydemo.widgt.textView.textView.ReadMoreTextView;
import com.demo.lixuan.mydemo.widgt.textView.textView.mode.LinkType;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * className: textViewListAdapter
 * description:java类作用描述
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/5/14 14:13
 */
public class TextSpanListAdapter extends BaseQuickAdapter<TextSpanBean, BaseViewHolder> {

    public TextSpanListAdapter(@Nullable List<TextSpanBean> data) {

        super(R.layout.item_text_span, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TextSpanBean item) {

        StringBuilder contentBuilder = new StringBuilder();
        String content = item.getContentText();

        ExpandableTextView readMoreTextView = helper.getView(R.id.tv_text);


        Drawable image = mContext. getResources().getDrawable(R.drawable.shape_bg_round_blue_10dp);
        ImageTagSpan imageSpan = new ImageTagSpan(image,mContext,readMoreTextView,16,R.color.red);

        ReadMoreTextView.StringSpanBean spanBean = new ReadMoreTextView.StringSpanBean();
        spanBean.setSpan(imageSpan);
        spanBean.setStartIndex(0);
        spanBean.setEndIndex(2);



        readMoreTextView.setContent(content);

        readMoreTextView.setLinkClickListener(new ExpandableTextView.OnLinkClickListener() {
            @Override
            public void onLinkClickListener(LinkType type, String content, String selfContent) {
                switch (type) {
                    case LINK_TYPE:
                        Intent intent = new Intent(mContext, WebViewActivity.class);
                        intent.putExtra("html", content);
                        mContext.startActivity(intent);
                        break;

                    case PHONE_TYPE:

                        break;

                    case MENTION_TYPE:
//                    ToolToast.showToast(App.getContext(), "点击用户：" + content);
                        break;

                    case SELF:
//                    ToolToast.showToast(App.getContext(), "你点击了自定义规则 内容是：" + content + " " + selfContent);
                        break;

                    default:
                        break;
                }
            }
        });


        readMoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext,item.getClickToast(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
