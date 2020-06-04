package com.demo.lixuan.mydemo.RecyclerView.adapter.subListAdatper.bean;

/**
 * className: CompanyMember
 * description: 公司员工信息
 * author： lx
 * date: 2020/2/24
 */

public class CompanyMember extends BaseCompanyDataBean {

    public static final int ITEM_TYPE_MEMBER_DEFAULT = 100;
    public static final int ITEM_TYPE_ADD_MEMBER = 101;
    int itemType = ITEM_TYPE_MEMBER_DEFAULT;

    String itemName;
    String currentDeptCode = "-1";
    String deptCodeBelongTo = "-1";

    public String getCurrentDeptCode() {

        return currentDeptCode;
    }

    public void setCurrentDeptCode(String currentDeptCode) {

        this.currentDeptCode = currentDeptCode;
    }

    public String getDeptCodeBelongTo() {

        return deptCodeBelongTo;
    }

    public void setDeptCodeBelongTo(String deptCodeBelongTo) {

        this.deptCodeBelongTo = deptCodeBelongTo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    @Override
    public String getDeptTagOfCurrentItem() {
        return currentDeptCode;
    }

    @Override
    public String getDeptTagOfItemBelongsTo() {
        return deptCodeBelongTo;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
