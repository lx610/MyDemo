package com.demo.lixuan.mydemo.base;

import androidx.fragment.app.Fragment;

/**
 * 类 名: BaseViewpageFactory
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/16
 * author lixuan
 */

public interface BaseViewpageFactory {
    int getPageCount();

    Fragment getFragment(int position);

    CharSequence getPageTitle(int position);
}
