package com.demo.lixuan.mydemo.java.designModel.shareObject;

import java.util.List;

/**
 * Created by Administrator on 2018/6/7.
 */

public interface Menu{
    public void setPersonMenu(String person, List list);
    public List findPersonMenu(String person, List list);
}
