package com.demo.lixuan.mydemo.widgt.videoAndImageBanner;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.demo.lixuan.mydemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 类 名: BannerViewAdapter
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/25
 * author lixuan
 */

public class BannerViewAdapter extends PagerAdapter {

    private Context context;
    private List<BannerModel> listBean;

    public BannerViewAdapter(Activity context, List<BannerModel> list) {
        this.context = context;
        if (list == null || list.size() == 0) {
            this.listBean = new ArrayList<>();
        } else {
            this.listBean = list;
        }
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        if (listBean.get(position).getUrlType() == 0) {//图片
            ImageView imageView = new ImageView(context);
            Glide.with(context).load(listBean.get(position).getBannerUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .skipMemoryCache(true)
                    .into(imageView);
            container.addView(imageView);

            return imageView;
        }else{//视频
            VideoView videoView = new VideoView(context);
            videoView.setVideoURI(Uri.parse(listBean.get(position).getBannerUrl()));
            //开始播放
            videoView.start();
            container.addView(videoView);
            return videoView;
        }

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return listBean.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }
}