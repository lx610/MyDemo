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
