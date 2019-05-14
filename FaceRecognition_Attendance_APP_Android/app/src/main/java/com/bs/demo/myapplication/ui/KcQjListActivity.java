package com.bs.demo.myapplication.ui;

import android.os.Bundle;
import android.view.View;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.ActKeyConstants;
import com.bs.demo.myapplication.common.base.BaseActivity;
import com.bs.demo.myapplication.model.QjBean;
import com.bs.demo.myapplication.model.ListBean;
import com.bs.demo.myapplication.utils.GsonUtil;
import com.bs.demo.myapplication.utils.HttpUtil;
import com.bs.demo.myapplication.widget.CommonList;
import com.bs.demo.myapplication.widget.dialog.BottomListDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class KcQjListActivity extends BaseActivity {

    // commonlist用来显示大量数据
    private CommonList clv;
    private List<ListBean> listBeans=new ArrayList<>();
    List<QjBean> kqBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kq_list);
        initView();
        setNavBarTitle("请假情况");


        clv.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                BottomListDialog.newInstance()
                        .addItem("审批请假")
                        .addItem("查看学生信息")
                        .setListener(new BottomListDialog.Listener() {
                            @Override
                            public void onItemClick(String name, int p) {
                                switch (p) {
                                    case 0:
                                        BottomListDialog.newInstance()
                                                .addItem("审核通过")
                                                .addItem("审核不通过")
                                                .setListener(new BottomListDialog.Listener() {
                                                    @Override
                                                    public void onItemClick(String name, int p) {
                                                        HttpUtil.newInstance()
                                                                .addParam("qjid",kqBeans.get(position).getId()+"")
                                                                .addParam("status",name)
                                                                .post("resetQjStatus", new HttpUtil.HttpListener() {
                                                                    @Override
                                                                    public void onSuccess(String t) {
                                                                        showToast("提交成功!");
                                                                        refresh();
                                                                    }

                                                                    @Override
                                                                    public void onError(String msg) {
                                                                        showToast("提交失败!");
                                                                    }
                                                                });
                                                    }
                                                }).show(getSupportFragmentManager());
                                        break;
                                    case 1:
                                        Bundle bundle=new Bundle();
                                        bundle.putString(ActKeyConstants.KEY_ID, kqBeans.get(position).getUserId());
                                        startAct(XsInfoActivity.class,bundle);
                                        break;

                                }
                            }
                        }).show(getSupportFragmentManager());

            }
        });

        refresh();
    }
    private void refresh(){
        HttpUtil.newInstance()
                .addParam("kcid",getIntent().getStringExtra(ActKeyConstants.KEY_ID))
                .post("getkcQjList", new HttpUtil.HttpListener() {
                    @Override
                    public void onSuccess(String t) {

                        handle(t);
                    }

                    @Override
                    public void onError(String msg) {
                    }
                });
    }

    private void initView() {
        clv = (CommonList) findViewById(R.id.clv);
    }
    private void handle(String json){
        kqBeans= GsonUtil.parseStr2List(json, QjBean.class);
        listBeans.clear();
        clv.delAll();
        for (QjBean kqBean:kqBeans){
            ListBean listBean=new ListBean();
            listBean.setTitle("课程编号: "+kqBean.getKcid()+"   课程名称: "+kqBean.getKcName());
            listBean.setContent("学生名称: "+kqBean.getUserName()+"\n审核状态: "+kqBean.getStatus() +"   提交时间: "+ kqBean.getTime());
            listBeans.add(listBean);
        }
        clv.addAll(listBeans);
    }
}
