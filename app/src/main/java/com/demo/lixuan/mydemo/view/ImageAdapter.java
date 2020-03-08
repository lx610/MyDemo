package com.demo.lixuan.mydemo.view;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.lixuan.mydemo.R;

import java.util.List;

/**
 * 类 名: ImageAdapter
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/25
 * author lixuan
 */

public class ImageAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public ImageAdapter(@Nullable List<String> data) {
        super(R.layout.item_text, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }


}
