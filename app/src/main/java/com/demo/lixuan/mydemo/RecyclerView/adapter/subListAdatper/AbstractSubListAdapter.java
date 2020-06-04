package com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper;

import android.content.Context;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.bean.BaseSubListDataBean;
import com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.controller.BaseSubListClickMissionController;
import com.demo.lixuan.mydemo.base.BaseApplication;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * className:
 * description:
 * author：lixuan
 * date: 2020/2/21
 */
public  abstract class AbstractSubListAdapter<K extends BaseViewHolder> extends RecyclerView.Adapter {

    List< BaseSubListDataBean> totlalList;
    List<BaseSubListDataBean> visiableItemList;
    //根机构，对应公司名称
    public BaseSubListDataBean rootDataBean;
    private SparseIntArray layouts;
    public   Context mContext;
    public BaseSubListClickMissionController mClickMissionController;

    public AbstractSubListAdapter(List<BaseSubListDataBean> totalDataList, Context context) {
        mContext = context;
        totlalList = totalDataList;
        //根据没有上级所属的特点，找出0级项（公司名）
        refreshAllData();
    }

    /**刷新所有数据
     * @param
     */
    public void refreshAllData() {

        visiableItemList = fliterCurrentItemList(totlalList,null,0, null);
        mClickMissionController = getSubListClickMissionController(visiableItemList,totlalList);
        constructViiableItemList();
    }

    protected abstract BaseSubListClickMissionController getSubListClickMissionController( List< BaseSubListDataBean> visiableItemList,List< BaseSubListDataBean> totlalList);

    /**
     * 可以用来刷新数据，目前由于每次筛查都要遍历totalList，算法负载度是N 的平方；
     */
    private void constructViiableItemList() {
        //多数情况 0级别项目 只有一个，visiableItemList .size =1
        //当 I  = 1 的时候，0 级项目已经筛选出 他的sublist 此时visiableList size 也不是1了
        //当 i = 1  的时候，一定是拿到了第一个1级 项目，开始构建这个项目的subList 以此类推
        //直到把所有的item构建完毕。
        for (int i = 0; i < visiableItemList.size(); i++) {
            BaseSubListDataBean dataBean = visiableItemList.get(i);
            //构建子级列表
            List<BaseSubListDataBean> subList = (List<BaseSubListDataBean>) dataBean.getSubList();
            constructSubList(i, dataBean, subList);
        }
    }

    /**生成nameOfItem 的子列表
     * @param totalDataList 完整数据
     * @param deptTagOfCurrentItem  本条列表的所属
     * @param level 层级
     * @param dataBean  列表所有者对象
     * @return
     */
    public List<BaseSubListDataBean> fliterCurrentItemList(List<? extends BaseSubListDataBean> totalDataList, String deptTagOfCurrentItem, int level, BaseSubListDataBean dataBean) {

        List<BaseSubListDataBean> newList = new ArrayList<>();

        for (int i = 0; i < totalDataList.size(); i++) {
            BaseSubListDataBean bean = totalDataList.get(i);
            if (bean == null){
                Log.e("daf",dataBean.getDeptTagOfCurrentItem());
            }

            if (TextUtils.isEmpty(deptTagOfCurrentItem)){
                //初始化项目，
                if (bean.getDeptTagOfItemBelongsTo().equals(bean.getRootItemBelongTo())){
                    //0级别项目
                    if (bean.getLevel() == null){
                        bean.setLevel(level);
                    }
                    rootDataBean = bean;
                    newList.add(bean);
                }
            }
//            else  if (TextUtils.isEmpty(bean.getNameOfCurrentItem())){
//
//                if (!TextUtils.isEmpty(bean.getNameOfItemBelongsTo())){
//                    if (nameOfItem.equals(bean.getNameOfItemBelongsTo())){
//                        //添加按钮
//                        //0级别项目
//                        bean.setLevel(level);
//                        newList.add(bean);
//                    }
//                }
//            }
            else {
                //次级列表
                if (!bean.getDeptTagOfItemBelongsTo().equals(bean.getRootItemBelongTo())){
                    if (deptTagOfCurrentItem != null){
                        if (deptTagOfCurrentItem.equals(bean.getDeptTagOfItemBelongsTo())){
                            //把属于 nameOfUpItem 加入这个列表
                            if (bean.getLevel() == null || bean.getLevel()==0){
                                bean.setLevel(level);
                            }
                            //把这个列表的拥有者复制
                            bean.setSubListOwner(dataBean);
                            newList.add(bean);
                        }
                    }

                }
            }
        }

        //是否拥有子集列表是区分部门和成员的依据
        if (newList.size() == 0){
            return null;
        }else {
            return newList;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        if (layouts == null){
            try {
                throw new Exception("need set method addItemType() in construct");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return createItemLayoutView( layouts.get(type));
    }

    private K createItemLayoutView(int resId) {
        View view = View.inflate(BaseApplication.getContext(),resId,null);
        K holder = createBaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        BaseSubListDataBean dataBean = (BaseSubListDataBean) visiableItemList.get(position);

        //列表的展开与折叠
        if (dataBean.isShowSubList()){
            //展开
            List<BaseSubListDataBean> subList = (List<BaseSubListDataBean>) dataBean.getSubList();
            if (subList !=null  && subList.size()>0){
                //如果没有次级列表，查阅他的次级列表，设置并展开
                if (dataBean.isBussinessData()){
                    constructSubList(position, dataBean, subList);
                }
            }


        }else {

            //折叠
            //如果有次级列表，从展示列表中移除
            List<BaseSubListDataBean> subList = (List<BaseSubListDataBean>) dataBean.getSubList();
            if (subList != null){
                removeSubListFromVisibleList(subList);
                dataBean.setHasAddSubListToVisible(false);
            }
        }


        //分发数据
        convert((K)viewHolder,dataBean,position);
    }

    /** 创建组织页面复写了这个方法
     * @param position  目标item在visibleList 中的位置
     * @param dataBean  目标item
     * @param subList   属于目标item的subList
     */
    public void constructSubList(int position, BaseSubListDataBean dataBean, List<BaseSubListDataBean> subList) {
        if (subList == null){

            Integer currentLevel = dataBean.getLevel();
            if (currentLevel != null){
                List<BaseSubListDataBean> itemList = fliterCurrentItemList(totlalList, dataBean.getDeptTagOfCurrentItem(), currentLevel + 1,dataBean);
                if (dataBean.isBussinessData()){
                    dataBean.setSubList(itemList);
                }


                //如果有次级列表，展开他的次级列表
                if (!dataBean.isHasAddSubListToVisible()){
                    if (dataBean.isBussinessData()){
//                        dataBean.setHasAddSubListToVisible(true);
                        addSubListIntoItemList(itemList,position);
                    }
                }
            }
        }else {

            //如果有次级列表，展开他的次级列表
            if (dataBean.isShowSubList() && !dataBean.isHasAddSubListToVisible()){
//                dataBean.setHasAddSubListToVisible(true);
                addSubListIntoItemList(subList,position);
            }
        }
    }


    /**把子集菜单加入展示列表
     * @param itemList
     * @param belongItemPositionInItemList
     */
    public void addSubListIntoItemList(List<BaseSubListDataBean> itemList, int belongItemPositionInItemList) {
        if (itemList == null || itemList.size() == 0){
            return;
        }
        if (belongItemPositionInItemList < visiableItemList.size()-1){
            visiableItemList.addAll(belongItemPositionInItemList + 1,itemList);

        }else {
            visiableItemList.addAll(itemList);
        }

    }

    /**隐藏次级列表
     * @param dataBean
     */
    public void hideSublist(BaseSubListDataBean dataBean) {
        mClickMissionController.setAllSubListToHide(dataBean);
        removeSubListFromVisibleList(dataBean.getSubList());
        dataBean.setShowSubList(false);
    }

    /**通过从展示列表中移除子级列表，折叠列表
     * @param subList
     */
    public void removeSubListFromVisibleList(List<? extends BaseSubListDataBean> subList) {
        for (int i = 0; i < subList.size(); i++) {
            BaseSubListDataBean item = subList.get(i);
            if (item.getSubList()!=null && item.getSubList().size()>0){
                removeSubListFromVisibleList(item.getSubList());
            }
        }
        if (visiableItemList.containsAll(subList)){
            visiableItemList.removeAll(subList);
        }
    }

    /**获取所有成员数目
     * @param dataBean
     */
    public int getAllMemberCount(BaseSubListDataBean dataBean) {
        int count = 0;
        List<BaseSubListDataBean> subList = dataBean.getSubList();
        if (subList == null && dataBean.isBussinessData()){
            //说明这个dataBean 是成员

        }else {
            count =  subList.size();
            if (subList.size()>0){
                for (int i = 0; i < subList.size(); i++) {

                    BaseSubListDataBean subData = subList.get(i);

                    if (subData.isBussinessData()){
                        int newCount = getAllMemberCount(subData);
                        if (subData.getSubList() !=null ){
                            //说明这个subData 是个部门，要减去一个
                            count = count + newCount - 1;
                        }else {
                            count = count + newCount;
                        }
                        Log.d("getAllMemberCount", "getAllMemberCount: " + dataBean.getDeptTagOfCurrentItem() + newCount);
                    }

                }
            }
        }
        return count;
    }

    protected abstract void convert(K viewHolder, BaseSubListDataBean dataBean, int position);

    @Override
    public int getItemCount() {
        return visiableItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return  visiableItemList.get(position).getItemType();
    }

    protected void addItemType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {
            layouts = new SparseIntArray();
        }
        layouts.put(type, layoutResId);
    }


    /**
     * if you want to use subclass of BaseViewHolder in the adapter,
     * you must override the method to create new ViewHolder.
     *
     * @param view view
     * @return new ViewHolder
     */
    @SuppressWarnings("unchecked")
    protected K createBaseViewHolder(View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        K k;
        // 泛型擦除会导致z为null
        if (z == null) {
            k = (K) new BaseViewHolder(view);
        } else {
            k = createGenericKInstance(z, view);
        }
        return k != null ? k : (K) new BaseViewHolder(view);
    }

    /**
     * get generic parameter K
     *
     * @param z
     * @return
     */
    private Class getInstancedGenericKClass(Class z) {
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (BaseViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                } else if (temp instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) temp).getRawType();
                    if (rawType instanceof Class && BaseViewHolder.class.isAssignableFrom((Class<?>) rawType)) {
                        return (Class<?>) rawType;
                    }
                }
            }
        }
        return null;
    }

    /**
     * try to create Generic K instance
     *
     * @param z
     * @param view
     * @return
     */
    @SuppressWarnings("unchecked")
    private K createGenericKInstance(Class z, View view) {
        try {
            Constructor constructor;
            // inner and unstatic class
            if (z.isMemberClass() && !Modifier.isStatic(z.getModifiers())) {
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                constructor.setAccessible(true);
                return (K) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (K) constructor.newInstance(view);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List< BaseSubListDataBean> getTotlalList() {
        return totlalList;
    }

    public List<BaseSubListDataBean> getVisiableItemList() {
        return visiableItemList;
    }
}