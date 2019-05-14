package com.bs.demo.myapplication.common.base;

import android.support.annotation.Nullable;

import com.bs.demo.myapplication.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author:  钟文清
 * Description:
 */
public class BottomListAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public BottomListAdapter(@Nullable List<String> data) {
        super(R.layout.item_bottom_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item_title,item);
    }
}
