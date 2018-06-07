package com.demo.lixuan.mydemo.java.designModel.shareObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/7.
 */

public class FlyWeightFactory {
    private Map menuList = new HashMap();
    private static FlyWeightFactory mFlyWeightFactory = new FlyWeightFactory();
    public static FlyWeightFactory getInstance(){
        return mFlyWeightFactory;
    }

    public synchronized Menu factory(String dish){

        if ((menuList.containsKey(dish))){
            return (Menu) menuList.get(dish);
        }else {
            Menu menu =new PersonMenu(dish);
            menuList.put(dish,menu);
            return menu;
        }
    }

   public Menu factory(String[] dish){
       PersonMenuMuch menu = new PersonMenuMuch();
       String key = null;
       for (int i = 0; i < dish.length; i++) {
           key = dish[i];
           menu.add(key,this.factory(key));
       }

       return menu;
   }

    public int getNumber(){
        return menuList.size();
    }


}
