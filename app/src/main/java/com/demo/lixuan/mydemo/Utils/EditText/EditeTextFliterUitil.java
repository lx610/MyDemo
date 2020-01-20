package com.demo.lixuan.mydemo.Utils.EditText;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.demo.lixuan.mydemo.Utils.EditText.EmojiFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * className: EditeTextFliterUitil
 * description:文本编辑器，过滤器工具
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/1/20 16:53
 */
public class EditeTextFliterUitil {
    /**
     * 空格过滤器
     * @return
     */
    private static InputFilter inhibitInputSpace(){
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        return filter;
    }

    /**
     * 表情过滤器
     * @return
     */
    private static InputFilter inhibitInputEmotion(){

        EmojiFilter emojiFilter = new EmojiFilter();
        return emojiFilter;
    }

    /**
     * 特殊字符过滤器 |&;$%@'"<>()+”.-
     * @return
     */
    private static InputFilter inhibitInputSpecialChar(){
        InputFilter specialCharFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                String regexStr = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                String regexStr = "[|&;$%@'\"<>()+”.-]";
                Pattern pattern = Pattern.compile(regexStr);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.matches()) {
                    return "";
                } else {
                    return null;
                }

            }
        };
        return specialCharFilter;
    }

    /**
     * 中文过滤器
     * @return
     */
    private static InputFilter inhibitInputChinese(){
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (checkNameChese(source.toString())){
                    return "";
                }
                return null;
            }
        };
        return filter;
    }

    /**
     * 长度过滤器
     * @param maxLength 最大长度 (<=0 返回null)
     * @return
     */
    private static InputFilter.LengthFilter inhibitInputMaxLength(int maxLength){
        if (maxLength <= 0){
            return null;
        }
        InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(maxLength);
        return lengthFilter;
    }

    public static void setViewWeight(View view, float weight) {
        view.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, weight));
    }

    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText, int maxlength) {
        // 过滤空格
        InputFilter spaceFilter = inhibitInputSpace();
        // 长度过滤
        InputFilter.LengthFilter lengthFilter = inhibitInputMaxLength(maxlength);
        if (lengthFilter != null) {
            editText.setFilters(new InputFilter[]{spaceFilter, lengthFilter});
        } else {
            editText.setFilters(new InputFilter[]{spaceFilter});
        }
    }

    /**
     * 过滤掉空格,中文,常见的表情
     * @param et
     * @param maxLength 最大长度 (<=0 没有限制)
     */
    public static void setEditTextInhibitInputSpace_Chinese_Emotion(EditText et, int maxLength) {
        if (et == null) {
            return;
        }
        // 空格过滤器
        InputFilter spaceFilter = inhibitInputSpace();
        // 中文过滤器
        InputFilter chineseFilter = inhibitInputChinese();
        //表情过滤器
        InputFilter emojiFilter = inhibitInputEmotion();
        // 长度过滤器
        InputFilter.LengthFilter lengthFilter = inhibitInputMaxLength(maxLength);
        if (lengthFilter != null) {
            et.setFilters(new InputFilter[]{spaceFilter, chineseFilter, emojiFilter, lengthFilter});
        } else {
            et.setFilters(new InputFilter[]{spaceFilter, chineseFilter, emojiFilter});
        }

    }

    /**
     * 过滤掉常见的表情
     * @param et edittext控件
     * @param maxLength 最大长度 (<=0 没有限制)
     */
    public static void setEditTextInhibitInputEmotion(EditText et, int maxLength) {
        if (et == null) {
            return;
        }
        //表情过滤器
        InputFilter emojiFilter = inhibitInputEmotion();
        // 长度过滤器
        InputFilter.LengthFilter lengthFilter = inhibitInputMaxLength(maxLength);
        if (lengthFilter != null) {
            et.setFilters(new InputFilter[]{emojiFilter, lengthFilter});
        } else {
            et.setFilters(new InputFilter[]{emojiFilter});
        }
    }

    /**
     * 判定输入汉字
     * @param c
     * @return
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     * @param name
     * @return
     */
    private static boolean checkNameChese(String name){
        boolean res=false;
        char [] cTemp = name.toCharArray();
        for(int i=0;i<name.length();i++)
        {
            if(isChinese(cTemp[i]))
            {
                res=true;
                break;
            }
        }
        return res;
    }

}
