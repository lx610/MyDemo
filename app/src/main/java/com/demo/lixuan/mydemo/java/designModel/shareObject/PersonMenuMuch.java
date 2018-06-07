package com.demo.lixuan.mydemo.java.designModel.shareObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018//7.
 */

public class PersonMenuMuch implements Menu {
    private Map MenuList = new HashMap();

    public void add(String key,Menu menu){
        MenuList.put(key,menu);
    }

    @Override
    public void setPersonMenu(String person, List list) {

    }

    @Override
    public List findPersonMenu(String person, List list) {
        List nothing = null;
        return nothing;
    }
}
