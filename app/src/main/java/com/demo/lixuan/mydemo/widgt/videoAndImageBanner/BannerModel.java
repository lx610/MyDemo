package com.demo.lixuan.mydemo.widgt.videoAndImageBanner;

/**
 * 类 名: BannerModel
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/25
 * author lixuan
 */

public class BannerModel {
    private String bannerName;
    private String bannerUrl;
    private int playTime;//播放时长
    private int urlType;//类型：0图片、1视频



    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public int getUrlType() {
        return urlType;
    }

    public void setUrlType(int urlType) {
        this.urlType = urlType;
    }
}
