package com.demo.lixuan.mydemo;

import android.util.Base64;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;

import static org.junit.Assert.assertEquals;

/**
 * className: StringRexTest
 * description:java类作用描述
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/5/18 17:00
 */
public class StringRexTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void match() {
//        String test = "2019-1-1 14:14";
//        String test = "打发点啊，阿asdf发生*12374523。阿斯蒂芬";
//        String test = "{ask}{name}{time}{at}{location}开{topic}{meeting}";
//        String test = "张云俊，曹伟民，陈楚婷，周志华";
//        String test = "曹伟民，陈楚婷，张云俊。";
//        String test = "asd_234f@assa41234dfa.dgd";
//        String test = "槽阿 斯蒂芬adf";
        String test = "123456374";
//        String[] news = test.split("\\{");
//        for (int i = 0; i < news.length; i++) {
//
//        }
//        System.out.println();
        if(test.matches(getTimeFormat())){
            System.out.println("ok ");
        }else {
            System.out.println("wrong");
        }

        if(test.contains(getTimeFormat())){
            System.out.println("googd ");
        }else {
            System.out.println("bad");
        }

//        String[] part = test.split("[^a-zA-Z0-9_\\u4e00-\\u9fa5]");
//        for (int i = 0; i < part.length; i++) {
//            System.out.println(part[i]);
//        }

        String[] newText = test.split(getTimeFormat());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < newText.length; i++) {
            builder.append(newText[i]);
        }
        test = builder.toString();
        System.out.println("test word :" + test);
    }

    @Test
    public void countArg(){
        String test = "{姓名}到某多个｛大师傅｝，要｛姓名｝个语言｛是｝来{姓名}";
        int count = checkArg(test);
        System.out.println(count + "");
    }

    private int checkArg(String test) {
        test = test.replace("{","｛");
        test = test.replace("}","｝");
        String[] count = test.split(getSplitFormat());
        return count.length -1;
    }

    private String getSplitFormat() {
        return "\\｛";
    }

    private String getTimeFormat() {
//        return "[a-zA-Z0-9_]+[@]+[a-zA-Z0-9_]+[.]+[a-zA-Z0-9_]+";
//        return "[a-zA-Z]+";

//        return "[^a-zA-Z0-9_\\u4e00-\\u9fa5]";//匹配任意以中文或数字开始含有标点符号的字符串
//        return "[a-zA-Z0-9_\\u4e00-\\u9fa5]+[^a-zA-Z0-9_\\u4e00-\
// \u9fa5][\\s\\S]*";//匹配任意以中文或数字开始含有标点符号的字符串
//
//        return "[\\s\\S]*";//匹配任意
//            return "[\\s\\S]*[\\u4e00-\\u9fa5]+[\\s\\S]*";//至少一个汉字
//            return "[\\u4e00-\\u9fa5]+";//从汉字开始
//        return "[a-zA-Z0-9_；;,.。， \\u4e00-\\u9fa5]+";//至少一个汉字，数字，或者下划线
//        return "[a-zA-Z0-9\\u4e00-\\u9fa5]+";//至少一个汉字，数字，或者下划线
//        return "^[\\u4e00-\\u9fa5\\d]{0,}$";
//        return "\\d{1,}";
//        return "\\d{4}年\\d{1,2}月\\d{1,2}日\\s{1,}\\d{1,2}\\:\\d{1,2}";
//        return "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$ ";//
//        return "^[0-9]+[\\s\\S]*";
        return "[\\d]+";
    }

    @Test
    public void textCombineArg(){

        String orgText = "｛大师傅｝呀呀呀呀呀呀呀呀呀呀呀呀｛大幅｝呃呃呃呃呃呃呃呃呃呃｛阿道夫｝水水水水水水水水是是是｛告诉对方｝呜呜呜呜呜呜呜呜｛告诉对方｝";
        String[] textPieace = splitArgmentNum(orgText);
        StringBuilder builder = new StringBuilder();
        String start = textPieace[0];
        builder.append(start);
        String[] targetString = new String[]{"12541255","第一个","第二个","第三个","第四个","第5个","第6个"};
        for (int i = 1; i < textPieace.length; i++) {
            String orgPieace = textPieace[i];
            if (i< targetString.length){
                orgPieace.replace("}","｝");
                int startSub = orgPieace.indexOf("｝") +1;
                String outsideArg = orgPieace.substring(startSub);
                String newText = targetString[i] + outsideArg;
                builder.append(newText);
            }else {
                String totalPieace = "｛" + orgPieace;
                builder.append(totalPieace);
            }
        }

        System.out.println(builder.toString());

    }

    private String[] splitArgmentNum( String content) {
        content = content.replace("{","｛");
        content = content.replace("}","｝");
        String[] count = content.split("\\｛");
        return count;
    }

    @Test
    public void testBase64(){
        String textString = "我很好";
        String base64String = Base64.encodeToString(textString.getBytes(), Base64.NO_WRAP);
        System.out.println(base64String);
        String aferText = Base64.encodeToString(base64String.getBytes(), Base64.DEFAULT);
        System.out.println(aferText);
    }

    @Test
    public void testReplace(){
        String rog = "已回复";
        String newContent = rog.replace("已回复的","未回复");
        System.out.println(newContent);

    }

    @Test
    public void textBlockingQueue(){
        ArrayBlockingQueue<String> orgQueue = new ArrayBlockingQueue<String>(5);
        for (int i = 0; i < 5; i++) {
            orgQueue.offer(i + "");
        }


        orgQueue.remove("1");
        int size = new Integer(orgQueue.size());
        for (int i = 0; i < size; i++) {
            String num = orgQueue.poll();
            System.out.println(num);
            orgQueue.offer(num);
        }
        System.out.println("=======================");
        int size2 = new Integer(orgQueue.size());
        for (int i = 0; i < size2; i++) {
            String num = orgQueue.poll();
            System.out.println(num);
        }
    }

}
