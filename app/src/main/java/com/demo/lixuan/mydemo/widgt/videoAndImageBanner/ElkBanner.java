package com.demo.lixuan.mydemo.widgt.videoAndImageBanner;

import android.content.Context;
import android.util.AttributeSet;

import com.demo.lixuan.mydemo.widgt.LazyViewPager;

import java.util.List;

/**
 * 类 名: VideoAndImageBanner
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/25
 * author lixuan
 */

public class ElkBanner extends LazyViewPager {
    private Context context;
    private List<BannerModel> listBean;

    public ElkBanner(Context context, Context context1) {
        super(context);
    }

    public ElkBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
