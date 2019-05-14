package com.bs.demo.myapplication.widget.dialog;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.BaseDialog;
import com.bs.demo.myapplication.common.base.BottomListAdapter;
import com.bs.demo.myapplication.utils.DividerUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  钟文清
 * Description:
 */
public class BottomListDialog extends BaseDialog {
    private RecyclerView rlv_bottom_list;
    private CardView cv_dialog_cancel;

    private List<String> list=new ArrayList<>();
    private BottomListAdapter bottomListAdapter=new BottomListAdapter(list);
    public interface Listener{
        void onItemClick(String name, int p);
    }
    private Listener listener;

    public BottomListDialog setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    /**
     *
     * @return 底层对话框的返回
     */
    @Override
    protected int getLayout() {
        return R.layout.dialog_bottom_list;
    }

    @Override
    protected void initView(View view) {
        cv_dialog_cancel=view.findViewById(R.id.cv_dialog_cancel);
        rlv_bottom_list=view.findViewById(R.id.rlv_bottom_list);
        cv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        rlv_bottom_list.addItemDecoration(DividerUtils.getDivider(getContext()));
        rlv_bottom_list.setAdapter(bottomListAdapter);
        bottomListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (listener!=null){
                    listener.onItemClick(list.get(position),position);
                }
                dismiss();
            }
        });
    }

    /**
     *
     * @return
     */
    public static BottomListDialog newInstance() {
        Bundle args = new Bundle();

        BottomListDialog fragment = new BottomListDialog();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     *
     * @param s 界面框的条目
     * @return  返回添加条目后的界面
     */
    public BottomListDialog addItem(String s){
        list.add(s);
        bottomListAdapter.notifyDataSetChanged();
        return this;
    }
    @Override
    public void onStart() {
        super.onStart();
        Window window=getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM);
    }
}
