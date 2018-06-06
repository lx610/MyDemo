package com.demo.lixuan.mydemo.java.designModel.Stragety;

import com.demo.lixuan.mydemo.Utils.UiUtils;

/**
 * Created by Administrator on 2018/6/6.
 */

public class Fighter {

    FightStragety fightStragety;
    public void learnFight(FightStragety fightStragety){
        this.fightStragety = fightStragety;
    }

    public void fight(){
        if (fightStragety!=null){
            fightStragety.fight();
        }else {
            UiUtils.makeText("他还不会武功");
        }
    }
}
