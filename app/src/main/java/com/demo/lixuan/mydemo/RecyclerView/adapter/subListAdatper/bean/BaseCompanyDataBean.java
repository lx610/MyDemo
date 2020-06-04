package com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.bean;


import java.io.Serializable;
import java.util.List;

/**
 * className: BaseCompanyDataBean
 * description:
 * author： lx
 * date: 2020/2/24
 */

public abstract class BaseCompanyDataBean  implements BaseSubListDataBean,Serializable {

    public static  String rootItemTag = "-10";

    @Override
    public String getRootItemBelongTo() {
        return rootItemTag;
    }

    /**
     * 是否展示成员列表
     */
    boolean isShowSubList = true;
    /**
     * 子级列表
     */
    List<BaseSubListDataBean > subList;
    /**
     * 当前层级
     */
    Integer level;

    /**
     * 是否已经加入
     */
    boolean hasAddSubListToVisible;
    private BaseSubListDataBean mSubListOwner;

    private boolean isBussinessData = true;

    @Override
    public boolean isShowSubList() {
        return isShowSubList;
    }

    @Override
    public void setShowSubList(boolean isShow) {
        isShowSubList = isShow;
    }

    @Override
    public void setSubList(List<BaseSubListDataBean> subList) {
        this.subList = subList;
    }

    @Override
    public List< BaseSubListDataBean> getSubList() {
        return (List<BaseSubListDataBean>) subList;
    }


    @Override
    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public Integer getLevel() {
        return level;
    }


    @Override
    public boolean isHasAddSubListToVisible() {
        return hasAddSubListToVisible;
    }

    @Override
    public void setHasAddSubListToVisible(boolean hasSet) {
        hasAddSubListToVisible = hasSet;
    }



    @Override
    public void setSubListOwner(BaseSubListDataBean dataBean) {

        mSubListOwner = dataBean;
    }
    @Override
    public BaseSubListDataBean getSubListOwner() {
        return  mSubListOwner;
    }

    @Override
    public boolean isBussinessData() {
        return isBussinessData;
    }

    public void setBussinessData(boolean bussinessData) {
        isBussinessData = bussinessData;
    }


}
