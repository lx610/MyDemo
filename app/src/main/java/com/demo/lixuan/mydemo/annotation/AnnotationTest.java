package com.demo.lixuan.mydemo.annotation;

/**
 * 类名： AnnotationTest
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:   Copyright  2018
 * 公       司:   深圳市旅联网络科技有限公司
 * version   2.0
 * date   2018/5/7
 * author lixuan
 * Created by elk-lx on 2018/5/7.
 */

public class AnnotationTest {

    @Swordsman(age = 23)
    public void fighting(){

    }
    @GET(value = "http://ip.taobao.com/59.108.54.37")
    public String getIpMsg(){
        return "";
    }

    @GET(value = "http://ip.taobao.com/")
    public String getIp(){
        return "";
    }
}
