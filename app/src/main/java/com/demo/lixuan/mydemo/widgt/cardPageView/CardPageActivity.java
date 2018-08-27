package com.demo.lixuan.mydemo.widgt.cardPageView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 2018/8/8.
 */

public class CardPageActivity extends BaseActivity implements CardPageViewLeftMove.OnCardClickListener {
    private static final String TAG = "CardPageActivity";
    private ArrayList<String> list;
    private DoubleSelcetDailog mWindow;
    private MyCardAdapter mAdapter;

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

        mAdapter = new MyCardAdapter(this);
        mAdapter.addAll(generateList());
        cardView.setAdapter(mAdapter);
        cardView.setPlayMode(CardPageViewLeftMove.FLAG_PALY_MODE_REPETE);

        final DotTab dotTab = (DotTab) findViewById(R.id.dot_tab);
        dotTab.generateDots(list.size());
        mAdapter.setOnItemSelectListener(new QuickCardHorizonSkipAdapter.OnTopItemChangeListener() {
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
        mWindow = new DoubleSelcetDailog(this);
        mWindow.setAlertText("jing adfasdfsadfsadfad");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        mWindow.setOnDissmissListener(new DoubleSelcetDailog.OnCancelClickListener() {
            @Override
            public void onClick() {
                mAdapter.setDeleteVisiabel(false);
            }
        });

        mWindow.setOkOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick:    onclick");
            }
        });

        mAdapter.setOnDeleteCardListener(new MyCardAdapter.OnDeleteCardListener() {
            @Override
            public void onClick(int position, View convertView, ViewGroup parent) {
                mWindow.showAtCenter(CardPageActivity.this);
            }
        });

        mWindow.setOnDissmissListener(new DoubleSelcetDailog.OnCancelClickListener() {
            @Override
            public void onClick() {
                mAdapter.setDeleteVisiabel(false);
            }
        });
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

//    public class MyCardAdapter extends QuickCardHorizonSkipAdapter<String> {
//
//        boolean isShowDelete;
//        private FrameLayout mFlDetelet;
//
//        public MyCardAdapter(Context context) {
//            super(context);
//        }
//
//
//        @Override
//        protected View getCardView(final int position,
//                                   View convertView, ViewGroup parent) {
//            if(convertView == null) {
//                LayoutInflater inflater = LayoutInflater.from(CardPageActivity.this);
//                convertView = inflater.inflate(R.layout.item_card_page, parent, false);
//            }
//            RelativeLayout relativeLayout=convertView.findViewById(R.id.rl_root);
//            mFlDetelet = convertView.findViewById(R.id.fl_delete);
//            TextView tv = (TextView) convertView.findViewById(R.id.textView1);
//            final String text = getItem(position%list.size());
//            tv.setText(text);
//            if (position%list.size()==2){
//                relativeLayout.setBackgroundColor(getResources().getColor(R.color.pink));
//            }
//            relativeLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    UiUtils.makeText(position + text + "被点击");
//                }
//            });
//
//            relativeLayout.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
//                @Override
//                public void onDoubleClick() {
//                    UiUtils.makeText(position + text + "被双击了");
//                    setDeleteVisiabel(true);
//                }
//            }));
//
//            mFlDetelet.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mWindow.showAtCenter(CardPageActivity.this);
//                }
//            });
//            return convertView;
//        }
//
//        public void setDeleteVisiabel(boolean isVisiable){
//            if (isVisiable){
//                mFlDetelet.setVisibility(View.VISIBLE);
//                isShowDelete =true;
//            }else {
//                mFlDetelet.setVisibility(View.GONE);
//                isShowDelete =false;
//            }
//        }
//
//        @Override
//        public boolean getTurnPageDisEnable() {
//            if (isShowDelete){
//                return true;
//            }else {
//                return false;
//            }
//        }
//
//
//        @Override
//        public boolean InterceptActionMove(View topView, MotionEvent ev, float downX, float downY) {
//            return true;
//        }
//    }
}
