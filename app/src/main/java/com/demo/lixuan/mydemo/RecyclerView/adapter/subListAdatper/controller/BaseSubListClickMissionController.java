package com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.controller;

import android.util.Log;


import com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.bean.BaseSubListDataBean;

import java.util.List;

/**
 * Created by lx on 2020/2/24
 * examples
 */
public abstract class BaseSubListClickMissionController{

    private static final String TAG = "BaseSubListClickMission";

    List<BaseSubListDataBean> visibleList;
    List<BaseSubListDataBean> totalList;

    public BaseSubListClickMissionController(List< BaseSubListDataBean> visibleList, List< BaseSubListDataBean> totalList) {
        this.visibleList = visibleList;
        this.totalList =  totalList;
    }

    /**
     * @param item  需要折叠的部门
     */
    public void setAllSubListToHide(BaseSubListDataBean item){
        if (item.getSubList() == null || item.getSubList().size() == 0){
            return;
        }

        try {
            List<BaseSubListDataBean> subList = (List<BaseSubListDataBean>) item.getSubList();
            int size = item.getSubList().size();
            for (int i = 0; i < item.getSubList().size(); i++) {
                BaseSubListDataBean subItem = subList.get(i);
                subItem.setShowSubList(false);
                //如果又是一个部门，先去关闭这个部门里的
                setAllSubListToHide(subItem);
            }
        }catch (Exception e){
            Log.d(TAG, "setAllSubListToHide: " + "subList item is different type from group item");
            return;
        }

    }

    /**发起删除删除
     * @param dataBean
     * @param position
     */
    public void deleteItem(BaseSubListDataBean dataBean, int position) {
        doDeletItem(dataBean,position);
    }

    /**吧项目从可视列表 和业务列表中删除
     * @param dataBean
     */
    public void removeItemFromAllList(BaseSubListDataBean dataBean) {
        if (dataBean.getSubList()!=null && dataBean.getSubList().size()>0){
            //部门对象
            //把子列表极其所有子列表从总列表移除
            //把子列表从可见列表极其所有子列表移除
            //从总列表移除
            //从上级记录列表移除
            //从可见列表移除
            removeAllSublistFrom(totalList,dataBean);
            removeAllSublistFrom(visibleList,dataBean);
            removeAllSubBeanFromDataBase(dataBean);
            totalList.remove(dataBean);
           
            if (dataBean.getSubListOwner()!=null){
                dataBean.getSubListOwner().getSubList().remove(dataBean);
            }

            visibleList.remove(dataBean);

        }else {

            //员工对象
            //从总列表移除
            //从上级记录列表移除
            //从可见列表移除
            if (totalList.contains(dataBean)){
                totalList.remove(dataBean);
            }
            dataBean.getSubListOwner().getSubList().remove(dataBean);
            if (visibleList.contains(dataBean)){
                visibleList.remove(dataBean);
            }
        }
    }

    protected abstract void removeAllSubBeanFromDataBase(BaseSubListDataBean dataBean);


    private void removeAllSublistFrom(List<? extends BaseSubListDataBean> totalList, BaseSubListDataBean dataBean) {
        List<BaseSubListDataBean> subList = dataBean.getSubList();
        if (subList==null || subList.size()==0){
            return;
        }
        for (int i = 0; i < subList.size(); i++) {
            BaseSubListDataBean subListDataBean =  subList.get(i);
            removeAllSublistFrom(totalList,subListDataBean);
            //删除所有成员
            if (totalList.contains(subListDataBean)){
                totalList.remove(subListDataBean);
            }
        }
        //删除部门
        if (totalList.contains(dataBean)){
            totalList.remove(dataBean);
        }
    }


    protected abstract void doDeletItem(BaseSubListDataBean dataBean, int position);

    public abstract void addCompanyCroupItem(BaseSubListDataBean newCompanyGrop);

    public abstract void addCompanyMember(BaseSubListDataBean newCompanyMember);
}