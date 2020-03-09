package com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.controller;

import com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.bean.BaseSubListDataBean;

import java.util.List;

/**
 * className:
 * description:
 * author：lixuan
 * date: 2020/3/9
 */
public class CompanySubListClickMission extends BaseSubListClickMissionController {
    public CompanySubListClickMission(List<BaseSubListDataBean> visibleList, List<BaseSubListDataBean> totalList) {
        super(visibleList, totalList);
    }

    @Override
    protected void removeAllSubBeanFromDataBase(BaseSubListDataBean dataBean) {

    }

    @Override
    protected void doDeletItem(BaseSubListDataBean dataBean, int position) {

    }

    @Override
    public void addCompanyCroupItem(BaseSubListDataBean newCompanyGrop) {

    }

    @Override
    public void addCompanyMember(BaseSubListDataBean newCompanyMember) {

    }
}
