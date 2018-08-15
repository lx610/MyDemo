package com.demo.lixuan.mydemo.widgt.cardPageView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.Utils.UiUtils;
import com.demo.lixuan.mydemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2018/8/8.
 */

public class CardPageActivity extends BaseActivity implements CardPageViewLeftMove.OnCardClickListener {
    private ArrayList<String> list;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_card_page_view;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        final CardPageViewLeftMove cardView = (CardPageViewLeftMove) findViewById(R.id.card_list);
        Button btJump = (Button) findViewById(R.id.bt_jump);
        cardView.setOnCardClickListener(this);
//        cardView.setItemSpace(Utils.convertDpToPixelInt(this, 20));

        MyCardAdapter adapter = new MyCardAdapter(this);
        adapter.addAll(generateList());
        cardView.setAdapter(adapter);
        cardView.setPlayMode(CardPageViewLeftMove.FLAG_PALY_MODE_REPETE);

        final DotTab dotTab = (DotTab) findViewById(R.id.dot_tab);
        dotTab.generateDots(list.size());
        adapter.setOnItemSelectListener(new CardAdapter.OnTopItemChangeListener() {
            @Override
            public void refreshTopPosion(int topPosition) {
                dotTab.setPositionDotOnclick(topPosition);
            }
        });


        btJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.setCurrentPosition(5);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    private List<String> generateList() {
        list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        return list;
    }

    @Override
    public void onCardClick(View view, int position) {

    }

    public class MyCardAdapter extends CardAdapter<String>{

        public MyCardAdapter(Context context) {
            super(context);
        }


        @Override
        protected View getCardView(final int position,
                                   View convertView, ViewGroup parent) {
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(CardPageActivity.this);
                convertView = inflater.inflate(R.layout.item_card_page, parent, false);
            }
            RelativeLayout relativeLayout=convertView.findViewById(R.id.rl_root);
            TextView tv = (TextView) convertView.findViewById(R.id.textView1);
            final String text = getItem(position%list.size());
            tv.setText(text);
            if (position%list.size()==2){
                relativeLayout.setBackgroundColor(getResources().getColor(R.color.pink));
            }
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UiUtils.makeText(position + text + "被点击");
                }
            });

            return convertView;
        }




    }
}
