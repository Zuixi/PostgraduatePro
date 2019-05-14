package com.bs.demo.myapplication.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.ActKeyConstants;
import com.bs.demo.myapplication.common.base.BaseFragment;
import com.bs.demo.myapplication.common.base.LocalBeanInfo;
import com.bs.demo.myapplication.widget.CommonList;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;


/**
 * author :钟文清
 * description：定义学生端查看个人使用记录的主界面，主要包含三个子功能模块
 * 1.我的考勤记录模块，记录用户个人的所有考勤记录，并且以列表形式表示
 * 2.我的请假记录模块，记录个人的所有请假记录，并且以列表形式表示
 * 3.我的笔记模块，用户可以用APP实现对课程进行笔记的记录，并且可以在这一栏进行查看相关笔记
 */
public class MineFragment extends BaseFragment {


    //继承RecyclerView，定义用于显示大量数据的页面布局
    private CommonList clv;

    /**
     *
     * @return 主界面
     */
    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        clv = (CommonList) view.findViewById(R.id.clv);
        clv.addNextItem("考勤历史");
        clv.addNextItem("请假历史");
        clv.addNextItem("我的笔记");
        clv.setShowRightIcon(true);
        clv.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putString(ActKeyConstants.KEY_ID, LocalBeanInfo.getUserInfo().getId());
                switch (position){
                    case 0:
                        startAct(UserKqListActivity.class,bundle);
                        break;
                    case 1:
                        startAct(UserQjListActivity.class,bundle);
                        break;
                    case 2:
                        startAct(BjListActivity.class);
                        break;
                }

            }
        });

    }
}
