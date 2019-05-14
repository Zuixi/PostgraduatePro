package com.bs.demo.myapplication.ui;

import android.os.Bundle;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.ActKeyConstants;
import com.bs.demo.myapplication.common.base.BaseActivity;
import com.bs.demo.myapplication.model.QjBean;
import com.bs.demo.myapplication.model.ListBean;
import com.bs.demo.myapplication.utils.GsonUtil;
import com.bs.demo.myapplication.utils.HttpUtil;
import com.bs.demo.myapplication.widget.CommonList;

import java.util.ArrayList;
import java.util.List;

public class UserQjListActivity extends BaseActivity {

    private CommonList clv;
    private List<ListBean> listBeans=new ArrayList<>();
    List<QjBean> kqBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kq_list);
        initView();
        setNavBarTitle("请假情况");

        refresh();
    }
    private void refresh(){
        HttpUtil.newInstance()
                .addParam("id",getIntent().getStringExtra(ActKeyConstants.KEY_ID))
                .post("getUserQjList", new HttpUtil.HttpListener() {
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
