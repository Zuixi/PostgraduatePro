package com.bs.demo.myapplication.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.model.ListBean;
import com.bs.demo.myapplication.ui.adapter.CommonListAdapter;
import com.bs.demo.myapplication.utils.DensityUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  钟文清
 * Description:改写RecyclerView类，更改为自己的布局，实现大量数据的展示
 * 作用：每个Item都可以设置自己的监听器，不过监听器在adapter里面实现
 */
public class CommonList extends RecyclerView {
    private List<ListBean> listBeans=new ArrayList<>();
    private CommonListAdapter commonListAdapter;
    private BaseQuickAdapter.OnItemClickListener onItemClickListener =null;
    public CommonList(@NonNull Context context) {
        super(context);
        init();
    }

    public void setShowLeftIcon(boolean showLeftIcon) {
        commonListAdapter.setShowLeftIcon(showLeftIcon);
    }

    public void setShowRightIcon(boolean showRightIcon) {
        commonListAdapter.setShowRightIcon(showRightIcon);
    }

    // 初始化
    public CommonList(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //设置单元选中监听器
    public void setOnItemClickListener(BaseQuickAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void init() {
        commonListAdapter=new CommonListAdapter(listBeans);
        setAdapter(commonListAdapter);
        //设置纵向滑动效果
        setLayoutManager(new LinearLayoutManager(getContext()));
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setClipChildren(false);
        setClipToPadding(false);
        setPadding(0,0,0,DensityUtil.dp2px(getContext(),48));
        commonListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(adapter,view,position);
                }
            }
        });
    }
    //将所有待显示的内容直接添加到
    public void addAll(List<ListBean> list){
        listBeans.addAll(list);
        commonListAdapter.notifyDataSetChanged();
    }
    public void addNextItem(String name){
        ListBean listBean=new ListBean();
        listBean.setTitle(name);
        listBean.setIcon(R.drawable.b1_arrow_righe);
        listBeans.add(listBean);
        commonListAdapter.notifyDataSetChanged();
    }
    public void delAll(){
        listBeans.clear();
        commonListAdapter.notifyDataSetChanged();
    }
}
