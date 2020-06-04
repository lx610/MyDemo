package com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper;

import android.app.Activity;
import android.content.Context;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.bean.BaseSubListDataBean;
import com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.bean.CompanyGropBean;
import com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.bean.CompanyMember;
import com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.controller.BaseSubListClickMissionController;
import com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.controller.CompanySubListClickMission;
import com.demo.lixuan.mydemo.Utils.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * className: CompanySampleSubListAdapter
 * description: 创建公司组织列表，原理
 * 1.创建部门：创建的时候，先行添加GropMenber addMenber 布局，后续添加成员都位于这个添加部门position位置之前
 * 2.
 * author： lx
 * date: 2020/2/24
 */

public class CompanySubListAdapter extends AbstractSubListAdapter<BaseViewHolder>{
    Activity mActivity;



    public CompanySubListAdapter(List<BaseSubListDataBean> totalDataList, Context context, Activity activity) {
        super(totalDataList,context);
        mActivity = activity;
        addItemType(CompanyGropBean.ITEM_TYPE_DEFAULT_COMPAY_GROUP, R.layout.item_company_group);
//        addItemType(CompanyMember.ITEM_TYPE_MEMBER_DEFAULT, R.layout.item_company_menber);
        addItemType(CompanyMember.ITEM_TYPE_MEMBER_DEFAULT, R.layout.item_company_group);
        addItemType(CompanyGropBean.ITEM_TYPE_ADD_COMPAY_GROUP, R.layout.item_company_group);
        addItemType(CompanyMember.ITEM_TYPE_ADD_MEMBER, R.layout.item_company_group);

    }

    @Override
    protected BaseSubListClickMissionController getSubListClickMissionController(List<BaseSubListDataBean> visiableItemList, List<BaseSubListDataBean> totlalList) {
        return new CompanySubListClickMission(visiableItemList,totlalList);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, BaseSubListDataBean dataBean, int position) {

        int itemType = dataBean.getItemType();

        switch (itemType){

            case CompanyGropBean.ITEM_TYPE_DEFAULT_COMPAY_GROUP:
                bindGroupData(viewHolder,(CompanyGropBean)dataBean,position);
                break;

            case CompanyMember.ITEM_TYPE_MEMBER_DEFAULT:
                bindMemberData(viewHolder,(CompanyMember)dataBean,position);
                break;
            case CompanyGropBean.ITEM_TYPE_ADD_COMPAY_GROUP:
                bindAddGroupData(viewHolder,(CompanyGropBean)dataBean,position);
                break;

            case CompanyMember.ITEM_TYPE_ADD_MEMBER:
                bindAddMemberData(viewHolder,(CompanyMember)dataBean,position);
                break;
                default:
                    break;
        }


    }

    private void bindAddMemberData(BaseViewHolder viewHolder, CompanyMember dataBean, int position) {
        TextView tvName = viewHolder.getView(R.id.tv_name);
        View infoContainer = viewHolder.getView(R.id.cl_info_container);
        View container = viewHolder.getView(R.id.ll_add_container);


        TextView addMember = viewHolder.getView(R.id.tv_add_member);
        EditText addGroup = viewHolder.getView(R.id.tv_add_group);

        addMember.setText("");
        addGroup.setText("");


        addMember.setHint("+成员");
        addGroup.setHint("+部门");

        View editButton = viewHolder.getView(R.id.view_edit);
        View deleteButton = viewHolder.getView(R.id.view_delete);
        tvName.setText("");


//        tvName.setHint("设置员工姓名");

        memberMission(viewHolder);
        //公共任务
        commonMission(viewHolder, dataBean, position);

        //始终隐藏编辑，删除，名称
        infoContainer.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);

    }

    private void bindAddGroupData(BaseViewHolder viewHolder, CompanyGropBean dataBean, int position) {
        TextView tvName = viewHolder.getView(R.id.tv_name);
        tvName.setText("");
        tvName.setHint("添加公司名称");

        //公共任务
        commonMission(viewHolder, dataBean, position);

    }


    private void bindGroupData(BaseViewHolder viewHolder, final CompanyGropBean dataBean, final int position) {

        final TextView tvName = viewHolder.getView(R.id.tv_name);

        int allMenberCount = getAllMemberCount(dataBean);
        if (dataBean.getSubListOwner()!=null){

            tvName.setText("公司部门 ：" + dataBean.getItemName() +"(" + allMenberCount + ")");
        }else {

            tvName.setText("公司部门 ：" + dataBean.getItemName()  +"(" + allMenberCount + ")");
        }

        tvName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tvName.hasFocus()){
                    dataBean.setItemName(s.toString());
                }
            }
        });

        //展示展开列表按钮
        ImageView imageView = viewHolder.getView(R.id.iv_show_list);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataBean.getSubList()!=null && dataBean.isShowSubList()){
                    //隐藏次级列表
                    hideSublist(dataBean);
                }else if (dataBean.getSubList()!=null && !dataBean.isShowSubList()){
                    //展开次级列表
                    dataBean.setShowSubList(true);
                }
                CompanySubListAdapter.this.notifyDataSetChanged();
            }
        });
//        //检测是否显示添加或折叠按钮
//        checkToShowAddContainer(viewHolder,dataBean,position);
//        //设置添加成员，部门的按钮点击事件
//        setAddButtonClickMission(viewHolder,dataBean,position);
//        //删除操作
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteItem(dataBean,position);
//            }
//        });
        commonMission(viewHolder, dataBean, position);
    }

    @Override
    public int getAllMemberCount(BaseSubListDataBean dataBean) {
        int count = 0;
        List<BaseSubListDataBean> subList = dataBean.getSubList();
        if (dataBean.getItemType()  ==  CompanyMember.ITEM_TYPE_MEMBER_DEFAULT){
            //说明这个dataBean 是成员
            count = count + 1;
        }else {
            if (subList!=null && subList.size()>0){
                for (int i = 0; i < subList.size(); i++) {
                    BaseSubListDataBean subData = subList.get(i);
                    if (subData.isBussinessData()){
                        int newCount = getAllMemberCount(subData);
                        count = count + newCount;
                    }
                }
            }
        }
        return count;
    }


    private List<Integer> generateList(Integer level) {
        List<Integer> newLineList = new ArrayList<>();
        for (Integer i = 0; i < level; i++) {
            newLineList.add(i);
        }
        return newLineList;
    }

    private void bindMemberData(BaseViewHolder viewHolder, final CompanyMember dataBean, final int position) {

        EditText tvName = viewHolder.getView(R.id.tv_name);

        if (dataBean.getSubListOwner()!=null){

            tvName.setText("公司员工 ：" + dataBean.getItemName() + "  lever :" + dataBean.getLevel() + "  belong:" + dataBean.getDeptCodeBelongTo() );
        }else {
            tvName.setText("公司员工 ：" + dataBean.getItemName() + "  lever :" + dataBean.getLevel() + "  belong:" + dataBean.getDeptCodeBelongTo() );

        }


        memberMission(viewHolder);
        //公共任务
        commonMission(viewHolder, dataBean, position);

    }

    /**员工任务
     * @param viewHolder
     */
    private void memberMission(BaseViewHolder viewHolder) {
        ImageView imageView = viewHolder.getView(R.id.iv_show_list);
        imageView.setVisibility(View.INVISIBLE);
    }

    /**公用处理时间
     * @param viewHolder
     * @param dataBean
     * @param position
     */
    private void commonMission(BaseViewHolder viewHolder, final BaseSubListDataBean dataBean, final int position) {

        final EditText tvName = viewHolder.getView(R.id.tv_name);
        View editButton = viewHolder.getView(R.id.view_edit);
        View deleteButton = viewHolder.getView(R.id.view_delete);

        //设置层级线
        setLevelLine(viewHolder, dataBean.getLevel());
        //检测是否显示添加成员按钮
        checkToShowAddContainer(viewHolder,dataBean,position);
        //设置添加成员，部门的按钮点击事件
        setAddButtonClickMission(viewHolder,dataBean,position);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(dataBean,position);
            }
        });

        tvName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    tvName.setFocusable(false);
//                    tvName.requestFocus();
                }
            }
        });
        //编辑点击响应
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvName.setEnabled(true);
                tvName.requestFocus();
                tvName.setCursorVisible(true);
                tvName.setSelection(0);
            }
        });

        KeyboardUtils.observeKeyboardShow(mActivity, new KeyboardUtils.KeyboardChangeListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                if (!isShow){
                    if (tvName.isFocusable()){
                        tvName.setFocusable(false);
                        tvName.setEnabled(false);


                    }
                }
            }
        });
    }

    private void deleteItem(BaseSubListDataBean dataBean, int position) {


        mClickMissionController.deleteItem(dataBean,position);
        notifyDataSetChanged();
    }

    private void setAddButtonClickMission(BaseViewHolder viewHolder, final BaseSubListDataBean dataBean, final int position) {
        final EditText tvName = viewHolder.getView(R.id.tv_name);
        final TextView addMember = viewHolder.getView(R.id.tv_add_member);
        final EditText addGroup = viewHolder.getView(R.id.tv_add_group);

        addGroup.setTag(position);
        addMember.setTag(position);


        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (tvName.isFocusable()){
//                    tvName.setFocusable(false);
//                }
                CompanyMember newCompanyMember = generateCompanyMember("xin menmber", dataBean);

                addMenberItem(addMember,dataBean,  (Integer) addMember.getTag(),newCompanyMember);


            }
        });

        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvName.isFocusable()){
                    tvName.setFocusable(false);
                }

            }
        });

        KeyboardUtils.observeKeyboardShow(mActivity, new KeyboardUtils.KeyboardChangeListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                if (!isShow){
//                    if (addMember.isFocused()){
//                        addMenberItem(addMember,dataBean, position);
//                    }

                    if (!TextUtils.isEmpty(addGroup.getText())){
                        BaseSubListDataBean clickData = visiableItemList.get((Integer) addGroup.getTag());
                        addGroupItem(addGroup,clickData, (Integer) addGroup.getTag());
                        addGroup.setText("");
                    }

                }
            }
        });
    }

    private CompanyMember generateCompanyMember(String nameString, BaseSubListDataBean dataBean) {
        CompanyMember newCompanyMember = new CompanyMember();
        newCompanyMember.setItemName(nameString);
        if (dataBean.getItemType() == CompanyGropBean.ITEM_TYPE_DEFAULT_COMPAY_GROUP){

            newCompanyMember.setDeptCodeBelongTo(dataBean.getDeptTagOfCurrentItem());
        }else {
            newCompanyMember.setDeptCodeBelongTo(dataBean.getDeptTagOfItemBelongsTo());
        }
        newCompanyMember.setItemType(CompanyMember.ITEM_TYPE_MEMBER_DEFAULT);
        newCompanyMember.setSubListOwner(dataBean.getSubListOwner());
        if (dataBean.getItemType() == CompanyGropBean.ITEM_TYPE_DEFAULT_COMPAY_GROUP){
            newCompanyMember.setLevel(dataBean.getLevel() + 1);
        }else {
            newCompanyMember.setLevel(dataBean.getLevel());
        }

        return newCompanyMember;
    }

    /**添加公司
     * @param addGroup
     * @param dataBean
     * @param position
     */
    private void addGroupItem(EditText addGroup, BaseSubListDataBean dataBean, int position) {
        CompanyGropBean newCompanyGrop = new CompanyGropBean();
        newCompanyGrop.setItemName(addGroup.getText().toString());
        if (dataBean.getItemType() == CompanyGropBean.ITEM_TYPE_DEFAULT_COMPAY_GROUP){
            newCompanyGrop.setDeptCodeBelong(dataBean.getDeptTagOfCurrentItem());
        }else {
            newCompanyGrop.setDeptCodeBelong(dataBean.getDeptTagOfItemBelongsTo());
        }

        newCompanyGrop.setItemType(CompanyGropBean.ITEM_TYPE_DEFAULT_COMPAY_GROUP);

        newCompanyGrop.setSubListOwner(dataBean.getSubListOwner());
        if (dataBean.getItemType() == CompanyGropBean.ITEM_TYPE_DEFAULT_COMPAY_GROUP){
            //0级对象的添加，不会有0的项目出现
            newCompanyGrop.setLevel(dataBean.getLevel() + 1);
        }if (dataBean.getItemType() == CompanyMember.ITEM_TYPE_ADD_MEMBER){
            newCompanyGrop.setLevel(dataBean.getLevel());
        }if (dataBean.getItemType() == CompanyGropBean.ITEM_TYPE_DEFAULT_COMPAY_GROUP){
            newCompanyGrop.setLevel(dataBean.getLevel() + 1);
        }else {
            newCompanyGrop.setLevel(dataBean.getLevel());
        }


        //放一个添加用的布局
        List<BaseSubListDataBean> subListDataBeanList = new ArrayList<>();
        CompanyMember companyMember = generateAddButton(dataBean, newCompanyGrop);
        companyMember.setLevel(newCompanyGrop.getLevel() + 1);
        subListDataBeanList.add(companyMember);

        newCompanyGrop.setSubList(subListDataBeanList);
        if (position < visiableItemList.size() -1){

            if (dataBean.getSubListOwner()!=null){

                dataBean.getSubListOwner().getSubList().add(newCompanyGrop);
            }

            if (dataBean.getItemType() == CompanyMember.ITEM_TYPE_ADD_MEMBER){
                visiableItemList.add(position,newCompanyGrop);
            }else {
                visiableItemList.add(position + 1,newCompanyGrop);
            }


        }else {

            if (dataBean.getSubListOwner()!=null){

                dataBean.getSubListOwner().getSubList().add(newCompanyGrop);
            }
            visiableItemList.add(position,newCompanyGrop);

        }
        notifyDataSetChanged();
    }

    /**
     * @param dataBean         触发按钮的item
     * @param sublListOwner 所在列表的持有者
     * @return
     */
    private CompanyMember generateAddButton(BaseSubListDataBean dataBean, BaseSubListDataBean sublListOwner) {
        CompanyMember companyMember = new CompanyMember();
        companyMember.setItemName("");
        companyMember.setSubListOwner(sublListOwner);
        companyMember.setItemType(CompanyMember.ITEM_TYPE_ADD_MEMBER);
        companyMember.setBussinessData(false);
        if (dataBean.getItemType() == CompanyGropBean.ITEM_TYPE_DEFAULT_COMPAY_GROUP){

            companyMember.setDeptCodeBelongTo(sublListOwner.getDeptTagOfCurrentItem());
        }else {
            companyMember.setDeptCodeBelongTo(dataBean.getDeptTagOfItemBelongsTo());
        }
        //要等newCompanyGrop 设置层级先
        if (dataBean.getItemType() == CompanyGropBean.ITEM_TYPE_DEFAULT_COMPAY_GROUP){
            companyMember.setLevel(dataBean.getLevel() + 1);
        }else {
            companyMember.setLevel(dataBean.getLevel());
        }
        return companyMember;
    }

    /** 构建子级列表，这里覆写后，增加在列表末尾添加 添加按钮的 操作
     * @param position 目标item在visibleList 中的位置
     * @param dataBean 目标item
     * @param subList  属于目标item的subList
     */
    @Override
    public void constructSubList(int position, BaseSubListDataBean dataBean, List<BaseSubListDataBean> subList) {
        if (subList == null){

            Integer currentLevel = dataBean.getLevel();
            if (currentLevel != null){

                List<BaseSubListDataBean> itemList = fliterCurrentItemList(totlalList, dataBean.getDeptTagOfCurrentItem(), currentLevel + 1,dataBean);
                if (dataBean.isBussinessData()){
                    if (itemList!=null && itemList.size()>0){
                        CompanyMember addItem = generateAddButton(dataBean, dataBean);
                        addItem.setLevel(dataBean.getLevel() + 1);
                        itemList.add(addItem);
                    }else if (dataBean.getItemType() == CompanyGropBean.ITEM_TYPE_DEFAULT_COMPAY_GROUP){
                        //对于没有员工的部门，仍然添加添加按钮
                        itemList = new ArrayList<>();
                        CompanyMember addItem = generateAddButton(dataBean, dataBean);
                        addItem.setLevel(dataBean.getLevel() + 1);
                        itemList.add(addItem);

                    }

                    dataBean.setSubList(itemList);

                    //根目录下，全是没有成员的部门，这些部门会无法展开
                    if (itemList!=null && itemList.size()>0){
                        for (int i = 0; i < itemList.size(); i++) {
                            BaseSubListDataBean subBean = itemList.get(i);
                            constructSubList(position,subBean,subBean.getSubList());
                        }
                    }

                }
            }
        }else {

            //如果有次级列表，展开他的次级列表
            if (dataBean.isShowSubList() && !dataBean.isHasAddSubListToVisible()){
                dataBean.setHasAddSubListToVisible(true);
                addSubListIntoItemList(subList,position);

            }
        }
    }

    private void addMenberItem(TextView addMember, BaseSubListDataBean dataBean, int position, CompanyMember newCompanyMember) {

        if (position < visiableItemList.size() -1){
            if (dataBean.getSubListOwner()!=null)
            dataBean.getSubListOwner().getSubList().add(newCompanyMember);
            if (dataBean.getItemType() == CompanyMember.ITEM_TYPE_ADD_MEMBER){
                visiableItemList.add(position,newCompanyMember);
            }else {
                visiableItemList.add(position + 1,newCompanyMember);
            }
        }else {
            if (dataBean.getSubListOwner()!=null)
            dataBean.getSubListOwner().getSubList().add(newCompanyMember);
            if (dataBean.getItemType() == CompanyMember.ITEM_TYPE_ADD_MEMBER){
                visiableItemList.add(position,newCompanyMember);
            }else {
                visiableItemList.add(newCompanyMember);
            }

        }


        notifyDataSetChanged();
    }

    /**
     * @param viewHolder
     * @param dataBean
     * @param position
     */
    private void checkToShowAddContainer(BaseViewHolder viewHolder, BaseSubListDataBean dataBean, int position) {
        View container = viewHolder.getView(R.id.ll_add_container);
        if (position < visiableItemList.size() - 1){
            BaseSubListDataBean nextBean = visiableItemList.get(position +1);
            if (nextBean.getLevel() < dataBean.getLevel()){
                if (position!=0){
                    container.setVisibility(View.VISIBLE);
                }else {
                    container.setVisibility(View.GONE);
                }
            }else {
                container.setVisibility(View.GONE);
            }

        }else {
            container.setVisibility(View.VISIBLE);
        }

    }

    /**设置层级线
     * @param viewHolder
     * @param level
     */
    private void setLevelLine(BaseViewHolder viewHolder, Integer level) {
        RecyclerView line_list = viewHolder.getView(R.id.line_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        line_list.setLayoutManager(linearLayoutManager);
        List<Integer> lineList = generateList(level);
        LineAdapter lineAdapter = new LineAdapter(lineList);
        line_list.setAdapter(lineAdapter);
        lineAdapter.notifyDataSetChanged();
    }

    public class LineAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

        public LineAdapter( @Nullable List<Integer> data) {
            super(R.layout.item_sublist_vertical_line, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, Integer item) {
        }
    }
}
