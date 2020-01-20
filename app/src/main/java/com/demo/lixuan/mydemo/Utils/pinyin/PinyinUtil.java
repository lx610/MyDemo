package com.demo.lixuan.mydemo.Utils.pinyin;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * className: PinyinUtil
 * description:java类作用描述
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/1/20 17:39
 */
public class PinyinUtil {
    /**
     * 得到全拼
     */
    public static String getPingYin(String str) {
        if (TextUtils.isEmpty(str)){
            return "";
        }
        str = str.replaceAll("\\p{P}","");//完全清除标点;//去掉所有标点符号
        char[] t1 = null;
        t1 = str.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                //判断是否为汉字字符
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else {
                    t4 += Character.toString(t1[i]);
                }
            }
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }

    /**
     * 得到拼音声母首字母
     */
    public static String getHeadChar(String str) {
        str = str.replaceAll("\\p{P}","");//完全清除标点;//去掉所有标点符号
        String convert = "";
        if (str == null) {
            return convert.toUpperCase();
        }
        char word = str.charAt(0);
        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
        if (pinyinArray != null) {
            convert += pinyinArray[0].charAt(0);
        } else {
            convert += word;
        }
        return convert.toUpperCase();
    }

    /**
     * 得到中文拼音声母首字母缩写
     */
    public static String getPinYinHeadChar(String str) {
        if (TextUtils.isEmpty(str)){
            return "";
        }
        str = str.replaceAll("\\p{P}","");//完全清除标点;//去掉所有标点符号
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert.toUpperCase();
    }

    /**获取汉语拼音韵母
     * @param hanziStr 汉字字符串
     * @return
     */
    public static String[] getPinYinYunMu(String hanziStr){
        if (hanziStr==null||TextUtils.isEmpty(hanziStr)){
            return null;
        }
        hanziStr = hanziStr.replaceAll("\\p{P}","");//完全清除标点;//去掉所有标点符号
        //因为输入的汉字字符串可能包含标点符号，使用获取声母的方法剔除
//        String shenMuHeadChar = getPinYinHeadChar(hanziStr).toLowerCase();
        String[] yunmu = new String[hanziStr.length()];
        for (int i = 0; i < hanziStr.length(); i++) {
            String singleHanzi = hanziStr.charAt(i) + "";
            String shenmu = getPinYinHeadChar(singleHanzi).toLowerCase();
            String pinyinString = getPingYin(singleHanzi);
            String orgintYunMu =pinyinString.substring(pinyinString.indexOf(shenmu) +1,pinyinString.length());
            orgintYunMu = orgintYunMu.replace("h","");
            if (orgintYunMu!=null&&!orgintYunMu.equals("")){
                yunmu[i] = orgintYunMu;
            }
        }
        return yunmu;
    }

    /**获取汉字拼音韵母首字母
     * @param hanziStr
     * @return
     */
    public static String getPinyinYunmuHeadChar(String hanziStr){
        if (TextUtils.isEmpty(hanziStr)){
            return "";
        }
        hanziStr = hanziStr.replaceAll("\\p{P}","");//完全清除标点;//去掉所有标点符号
        String[] yunmu = getPinYinYunMu(hanziStr);
        if (yunmu==null){
            return hanziStr;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < yunmu.length; i++) {
            String yunString = yunmu[i];
            if (yunString!=null){
                builder.append(yunString.charAt(0) + "");
            }
        }
        return builder.toString();
    }

}
