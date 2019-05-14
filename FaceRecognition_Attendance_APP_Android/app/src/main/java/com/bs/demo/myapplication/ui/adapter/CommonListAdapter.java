package com.bs.demo.myapplication.ui.adapter;

import android.support.annotation.Nullable;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.model.ListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author:  钟文清
 * Description:改写的commonlist适配器，用于为item提供数据
 */
public class CommonListAdapter extends BaseQuickAdapter<ListBean, BaseViewHolder> {
    public CommonListAdapter(@Nullable List<ListBean> data) {
        super(R.layout.item_common, data);
    }
    private boolean isShowRightIcon=false;
    private boolean isShowLeftIcon=false;

    public void setShowLeftIcon(boolean showLeftIcon) {
        isShowLeftIcon = showLeftIcon;
    }

    public void setShowRightIcon(boolean showRightIcon) {
        isShowRightIcon = showRightIcon;
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        helper.setText(R.id.tv_item_content,item.getContent());
        helper.setText(R.id.tv_item_title,item.getTitle());
        helper.setGone(R.id.iv_item_left,isShowLeftIcon);
        helper.setGone(R.id.iv_item_right,isShowRightIcon);
        if (isShowLeftIcon){
            helper.setImageResource(R.id.iv_item_left,item.getIcon());
        }
        if (isShowRightIcon){
            helper.setImageResource(R.id.iv_item_right,item.getIcon());
        }
        if (item.getContent()==null||item.getContent().isEmpty()){
            helper.setGone(R.id.tv_item_content,false);
        }else {
            helper.setGone(R.id.tv_item_content,true);
        }
    }
}
