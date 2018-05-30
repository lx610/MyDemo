package com.demo.lixuan.mydemo.java.staticTest;

/**
 * Created by Administrator on 2018/5/30.
 */

public class OutClass {
    int index = 0;

    public Integer addNum(){
      return   ++index;
    }

    public Integer subNum(){
        return   --index;
    }

    public class inerClass{

        public Integer addNum(){
            return   index++;
        }

        public Integer subNum(){
            return   index--;
        }


    }
}
