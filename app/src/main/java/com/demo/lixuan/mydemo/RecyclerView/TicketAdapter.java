package com.demo.lixuan.mydemo.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.lixuan.mydemo.R;

import java.util.List;

/**
 * 类 名: TicketAdapter
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2017/12/12
 * author lixuan
 */

public class TicketAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    int itemCountToShow=2;

    public TicketAdapter(List<String> data) {
        super(R.layout.item_ticket, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_text,item);
    }

    public void setItemContToShow(int i) {
        itemCountToShow=i;
    }

    @Override
    public int getItemCount() {
        return itemCountToShow;
    }
}
