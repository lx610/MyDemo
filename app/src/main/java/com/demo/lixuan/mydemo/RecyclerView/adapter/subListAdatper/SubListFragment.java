package com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper;

import android.os.Bundle;
import android.view.View;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.bean.BaseCompanyDataBean;
import com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.bean.BaseSubListDataBean;
import com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.bean.CompanyGropBean;
import com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.bean.CompanyMember;
import com.demo.lixuan.mydemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * className:
 * description:
 * author：lixuan
 * date: 2020/3/9
 */
public class SubListFragment extends BaseFragment {


    private RecyclerView.Adapter mSubAdapter;
    private List<BaseSubListDataBean> mTotalList;

    @Override
    protected int getLayoutResour() {
        return R.layout.activity_only_recycleview;
    }

    @Override
    protected void initListener(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initData(View view, Bundle savedInstanceState) {


    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        RecyclerView list = view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        List<? extends BaseSubListDataBean> testList = generateTestList();
        mSubAdapter = new CompanySubListAdapter((List<BaseSubListDataBean>) testList,getContext(),getActivity());
        list.setAdapter(mSubAdapter);

    }

    private List<? extends BaseSubListDataBean> generateTestList() {
        List newTestList = new ArrayList();

        CompanyGropBean firstBean = new CompanyGropBean();

        firstBean.setItemType(CompanyGropBean.ITEM_TYPE_DEFAULT_COMPAY_GROUP);
        firstBean.setItemName("0");
        firstBean.setNameBelong(BaseCompanyDataBean.rootItemTag);
        firstBean.setShowSubList(true);
        newTestList.add(firstBean);



        for (int i = 0; i < 30; i++) {
            int belong = i%5;

            if (i < 6){

                CompanyGropBean gropBean = new CompanyGropBean();
                gropBean.setShowSubList(true);
                gropBean.setItemName(i + 1 + "");
                gropBean.setNameBelong(0 +"");
                newTestList.add(gropBean);

            }else {

                CompanyMember member = new CompanyMember();
                member.setItemName(i + 1 + "");
                member.setNameBelong(belong + "");

                newTestList.add(member);

            }

        }
        return newTestList;
    }
}
