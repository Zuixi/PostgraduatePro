package com.bs.demo.myapplication.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.ActKeyConstants;
import com.bs.demo.myapplication.common.base.BaseActivity;
import com.bs.demo.myapplication.common.base.LocalBeanInfo;
import com.bs.demo.myapplication.model.BjBean;
import com.bs.demo.myapplication.model.KqBean;
import com.bs.demo.myapplication.model.ListBean;
import com.bs.demo.myapplication.utils.DateUtil;
import com.bs.demo.myapplication.utils.GsonUtil;
import com.bs.demo.myapplication.utils.HttpUtil;
import com.bs.demo.myapplication.widget.CommonList;
import com.bs.demo.myapplication.widget.NavigationBar;
import com.bs.demo.myapplication.widget.dialog.BottomListDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BjListActivity extends BaseActivity {
    private CommonList clv;
    private List<ListBean> listBeans=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bj_list);
        setNavBarTitle("我的笔记");
        initView();
        navigationBar.setRightBtnText("添加");
        navigationBar.setNavBarRightBtnListener(new NavigationBar.NavBarRightBtnListener() {
            @Override
            public void onClick() {
                startAct(AddBjActivity.class);
            }
        });
        refresh();
        clv.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                BottomListDialog.newInstance()
                        .addItem("删除")
                        .setListener(new BottomListDialog.Listener() {
                            @Override
                            public void onItemClick(String name, int p) {
                                HttpUtil.newInstance()
                                        .addParam("id",bjBeans.get(position).getId()+"")
                                        .post("delBj", new HttpUtil.HttpListener() {
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
            }
        });

    }
    private void refresh(){
        HttpUtil.newInstance()
                .addParam("id", LocalBeanInfo.getUserInfo().getId())
                .post("getUserBjList", new HttpUtil.HttpListener() {
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
    List<BjBean> bjBeans;
    private void handle(String json){
        listBeans.clear();
        clv.delAll();
        bjBeans= GsonUtil.parseStr2List(json, BjBean.class);
        for (BjBean bjBean:bjBeans){
            ListBean listBean=new ListBean();
            listBean.setTitle("题目: "+bjBean.getTitle());
            listBean.setContent("内容: "+bjBean.getContent()+"\n"+bjBean.getDate());
            listBeans.add(listBean);
        }
        clv.addAll(listBeans);
    }
}
