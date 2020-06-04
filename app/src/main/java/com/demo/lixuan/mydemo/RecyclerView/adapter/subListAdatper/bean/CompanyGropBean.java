package com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.bean;

/**
 * className: CompanyGropBean
 * description: 公司部门信息
 * author： lx
 * date: 2020/2/24
 */

public class CompanyGropBean extends BaseCompanyDataBean {

    public static final int ITEM_TYPE_DEFAULT_COMPAY_GROUP = 400;
    public static final int ITEM_TYPE_ADD_COMPAY_GROUP = 401;

    int itemType = ITEM_TYPE_DEFAULT_COMPAY_GROUP;

    String itemName;
    String DeptCodeBelong;
    String deptCode = "-1";

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDeptCode() {

        return deptCode;
    }

    public void setDeptCode(String deptCode) {

        this.deptCode = deptCode;
    }

    public String getDeptCodeBelong() {
        return DeptCodeBelong;
    }

    public void setDeptCodeBelong(String deptCodeBelong) {
        this.DeptCodeBelong = deptCodeBelong;
    }

    @Override
    public String getDeptTagOfCurrentItem() {
        return deptCode + "";
    }

    @Override
    public String getDeptTagOfItemBelongsTo() {
        return DeptCodeBelong + "";
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
