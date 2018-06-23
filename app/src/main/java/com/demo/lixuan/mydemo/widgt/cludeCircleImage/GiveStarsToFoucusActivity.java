package com.demo.lixuan.mydemo.widgt.cludeCircleImage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.Utils.UiUtils;
import com.demo.lixuan.mydemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 类 名: GiveStarsToFoucusActivity
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2017/7/17
 * author lixuan
 */

public class GiveStarsToFoucusActivity extends BaseActivity {
    private KeywordsFlow keywordsFlow;
    private String[] keywords={ "口味虾", "牛蛙", "火锅", "真功夫", "料理",
            "密室逃", "天成房", "波比艾" };
    public static final String SEARCH_HISTORY = "search_history";
//    private ArrayList<SearchDataPojo> searchItem;
    private String longhistory;
    private SharedPreferences sp;
    private ArrayList<String> history;
    private EditText world_shopping_search_input;
    private TextView world_city_refresh, clear_history;
    private ImageView toSearch;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_radom_image_in_circle;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        world_shopping_search_input = (EditText) findViewById(R.id.world_shopping_search_input);
        keywordsFlow = (KeywordsFlow) findViewById(R.id.keywordsflow);

        world_city_refresh = (TextView) findViewById(R.id.world_city_refresh);
        world_city_refresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                refreshTags();
            }
        });

        clear_history = (TextView) findViewById(R.id.clear_history);
        clear_history.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                clearSearchHistory();
            }
        });

    }

    @Override
    public void initData() {
//        initSearchHistory();
        refreshTags();
    }

    @Override
    public void initListener() {

    }

    private void refreshTags() {
//        initSearchHistory();
        keywordsFlow.setDuration(800l);
        keywordsFlow.setOnItemClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String keyword = ((CircleTargetView) v).getText().toString();// 获得点击的标签
                world_shopping_search_input.setText(keyword);
                UiUtils.makeText(keyword + "被点击");
            }
        });
        // 添加
        feedKeywordsFlow(keywordsFlow, keywords);
        keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
    }

    private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
        Random random = new Random();
        for (int i = 0; i < KeywordsFlow.MAX; i++) {
            int ran = random.nextInt(arr.length);
            String tmp = arr[ran];
            keywordsFlow.feedKeyword(tmp);
        }
//        for (int i = 0; i < arr.length; i++) {
////            int ran = random.nextInt(arr.length);
//            String tmp = arr[i];
//            keywordsFlow.feedKeyword(tmp);
//        }
    }

    /**
     * 读取历史搜索记录
     */
    private void initSearchHistory() {
//        sp = getSharedPreferences(MainActivity.SEARCH_HISTORY, 0);
//        longhistory = sp.getString(MainActivity.SEARCH_HISTORY, "");
        if (!longhistory.equals("")) {
//            keywords = longhistory.split(",");
//            searchItem = new ArrayList<SearchDataPojo>();
            for (int i = 0; i < keywords.length; i++) {
//                searchItem.add(new SearchDataPojo().setContent(keywords[i]));
            }
        } else {// 如果SharedPreferences没有值得话，就显示默认的数据
            keywords = new String[] { "口味虾", "牛蛙", "火锅", "真功夫", "料理",
                    "密室逃", "天成房", "波比艾" };
        }
    }

    /*
     * 保存搜索记录
     */
    private void saveSearchHistory() {
        String text = world_shopping_search_input.getText().toString().trim();
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        if (!text.equals("") && text != null) {
            if (text.length() < 1) {
                return;
            }
            sp = getSharedPreferences(SEARCH_HISTORY, 0);
            String longhistory = sp.getString(SEARCH_HISTORY, "");
            String[] tmpHistory = longhistory.split(",");
            history = new ArrayList<String>(Arrays.asList(tmpHistory));
            if (history.size() > 0) {
                int i;
                for (i = 0; i < history.size(); i++) {
                    if (text.equals(history.get(i))) {
                        history.remove(i);
                        break;
                    }
                }
                history.add(0, text);
            }
            if (history.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < history.size(); i++) {
                    sb.append(history.get(i) + ",");
                }
                sp.edit().putString(SEARCH_HISTORY, sb.toString()).commit();
            } else {
                sp.edit().putString(SEARCH_HISTORY, text + ",").commit();
            }
            clear_history.setVisibility(View.VISIBLE);
        }
    }

    // 清除历史数据
    private void clearSearchHistory() {
//        searchItem.removeAll(searchItem);
//        sp.edit().clear().commit();
//        Toast.makeText(this, "清除历史记录", Toast.LENGTH_LONG).show();
    }
}
