package com.demo.lixuan.mydemo;

import com.demo.lixuan.mydemo.annotation.AnnotationTest;
import com.demo.lixuan.mydemo.annotation.GET;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    private static final String TAG = "ExampleUnitTest";
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void regionCode(){
        String regionCode= Locale.getDefault().getCountry();
        System.out.println("getCountry()==================" + regionCode);

        String regionCode2 = phoneUtil.getRegionCodeForCountryCode(86);
        System.out.println("getRegionCodeForCountryCode()==================" + regionCode2);


        int contrycode = phoneUtil.getCountryCodeForRegion(regionCode);
        System.out.println("getCountryCodeForRegion()==================" + contrycode);



    }

    @Test
    public void testAnnotaionGet(){
        Method[] methods = AnnotationTest.class.getDeclaredMethods();
        for (Method m : methods){
            GET get = m.getAnnotation(GET.class);
            System.out.println(get.value());
        }
    }


}