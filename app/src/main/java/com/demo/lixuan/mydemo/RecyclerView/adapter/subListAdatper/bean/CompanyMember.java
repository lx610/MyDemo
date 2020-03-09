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
    String nameBelong;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getNameBelong() {
        return nameBelong;
    }

    public void setNameBelong(String nameBelong) {
        this.nameBelong = nameBelong;
    }

    @Override
    public String getNameOfCurrentItem() {
        return itemName;
    }

    @Override
    public String getNameOfItemBelongsTo() {
        return nameBelong;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
