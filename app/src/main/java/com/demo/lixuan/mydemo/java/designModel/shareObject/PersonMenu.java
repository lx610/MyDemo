package com.demo.lixuan.mydemo.java.designModel.shareObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2018/6/7.
 */

public class PersonMenu implements Menu {

    private  String dish;

    public PersonMenu(String dish) {
        this.dish = dish;
    }

    @Override
    public void setPersonMenu(String person, List list) {
        list.add(person);
        list.add(dish);
    }

    @Override
    public List findPersonMenu(String person, List list) {
        List dishList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()){
            if (person.equals(it.next()))
                dishList.add(it.next());
        }
        return dishList;

    }
}
