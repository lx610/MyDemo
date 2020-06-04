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
        return R.layout.fragment_sub_list;
    }

    @Override
    protected void initListener(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initData(View view, Bundle savedInstanceState) {


    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        RecyclerView list = view.findViewById(R.id.sub_list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        List<? extends BaseSubListDataBean> testList = generateTestDeptList();
        mSubAdapter = new CompanySubListAdapter((List<BaseSubListDataBean>) testList,getContext(),getActivity());
        list.setAdapter(mSubAdapter);

    }

//    private List<? extends BaseSubListDataBean> generateTestList() {
//        List newTestList = new ArrayList();
//
//        CompanyGropBean firstBean = new CompanyGropBean();
//
//        firstBean.setItemType(CompanyGropBean.ITEM_TYPE_DEFAULT_COMPAY_GROUP);
//        firstBean.setItemName("0");
//        firstBean.setDeptCodeBelong(BaseCompanyDataBean.rootItemTag);
//        firstBean.setShowSubList(true);
//        newTestList.add(firstBean);
//
//
//
//        for (int i = 0; i < 30; i++) {
//            int belong = i%5;
//
//            if (i < 6){
//
//                CompanyGropBean gropBean = new CompanyGropBean();
//                gropBean.setShowSubList(true);
//                gropBean.setItemName(i + 1 + "");
//                gropBean.setDeptCodeBelong(0 +"");
//                newTestList.add(gropBean);
//
//            }else {
//
//                CompanyMember member = new CompanyMember();
//                member.setItemName(i + 1 + "");
//                member.setNameBelong(belong + "");
//
//                newTestList.add(member);
//
//            }
//
//        }
//        return newTestList;
//    }

    private List<BaseCompanyDataBean> generateTestDeptList() {
        List<BaseCompanyDataBean> deptList = new ArrayList<>();
        String rootId = "100001";
        for (int i = 0; i < 30; i++) {
            if (i == 0){
                CompanyGropBean dept = new CompanyGropBean();
                dept.setItemType(CompanyGropBean.ITEM_TYPE_DEFAULT_COMPAY_GROUP);
                dept.setItemName("根机构");
                dept.setLevel(0);
                dept.setDeptCode(0+"");
                dept.setDeptCodeBelong("-10");
                deptList.add(dept);
            }else if (i < 6){
                CompanyGropBean dept = new CompanyGropBean();
                dept.setDeptCode(i+"");
                dept.setDeptCodeBelong(0+"");
                dept.setItemName("吊死扶伤法国队法国队vavafj阿斯顿发生地方发生大幅安塞懂法守法机构" + i);
                dept.setItemType(CompanyGropBean.ITEM_TYPE_DEFAULT_COMPAY_GROUP);
                deptList.add(dept);
            }
//            else if (i> 4 && i<9 ){
//                if (i % 7 == 2 || i % 7 == 1){
//                    int forid = i % 7;
//                    Dept dept = new Dept(0 + "",forid + "",i + "","","机构" + i,"0","","0");
//                    dept.setItemType(Dept.ITEM_TYPE_DEFAULT_COMPAY_GROUP);
//                    deptList.add(dept);
//                }
//
//            }
            else {

                int orgId = i % 4;
                CompanyMember deptMember;
                if (orgId == 0){

                    deptMember = new CompanyMember();
                    deptMember.setDeptCodeBelongTo(orgId + "");
                    deptMember.setItemName("员工" + i);

                }else {

                    deptMember = new CompanyMember();
                    deptMember.setDeptCodeBelongTo(orgId + "");
                    deptMember.setItemName("员工" + i);
                }

                deptMember.setItemType(CompanyMember.ITEM_TYPE_MEMBER_DEFAULT);
                deptList.add(deptMember);

            }
        }
        return deptList;
    }
}
