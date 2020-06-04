package com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.bean;


import java.io.Serializable;
import java.util.List;

/**
 * className:
 * description:
 * author：lixuan
 * date: 2020/2/21
 */
public interface BaseSubListDataBean extends MultiItemEntity,Serializable {

    String getDeptTagOfCurrentItem();

    String getDeptTagOfItemBelongsTo();

    /**判断根部的依据
     * @return
     */
    String getRootItemBelongTo();

    /**获取标记，是否展示子级列表,根项目必须设置为true
     * @return
     */
    boolean isShowSubList();

    /**设置标记，是否展示子级列表
     * @param isShow
     */
    void setShowSubList(boolean isShow);

    /**获取子集列表
     * @return
     */
    List<BaseSubListDataBean> getSubList();

    /**设置层级
     * @param level
     */
    void setLevel(Integer level);

    /**获取层级
     * @return
     */
    Integer getLevel();
    /**设置子集列表
     * @return
     * @param itemList
     */
    void setSubList(List<BaseSubListDataBean> itemList);

    /**获取标记，是否已经把子级列表加入展示列表
     * @return
     */
    boolean isHasAddSubListToVisible();

    /**
     * 设置标记，是否已经把子级列表加入展示列表
     */
    void setHasAddSubListToVisible(boolean hasSet);


    /**保存次级列表拥有者指针
     * @param dataBean
     */
     void setSubListOwner(BaseSubListDataBean dataBean);

    /**获取次级列表拥有者指针
     * @return
     */
    BaseSubListDataBean getSubListOwner();

    /**是否是业务数据，true 是 false 是辅助数据
     * @return
     */
    boolean isBussinessData();

    int getItemType();


}
