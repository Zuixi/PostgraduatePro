package com.bs.demo.myapplication.ui;


import android.os.Bundle;
import android.util.Log;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.ActKeyConstants;
import com.bs.demo.myapplication.common.base.BaseActivity;
import com.bs.demo.myapplication.model.coursemesBean;
import com.bs.demo.myapplication.model.ListBean;
import com.bs.demo.myapplication.utils.GsonUtil;
import com.bs.demo.myapplication.utils.HttpUtil;
import com.bs.demo.myapplication.widget.CommonList;

import java.util.ArrayList;
import java.util.List;

/**
 * author:钟文清
 * description： 用来显示课程的花名册信息
 */
public class showCourseMesActivity extends  BaseActivity {
    private  CommonList clv;

    private List<ListBean> listBeans=new ArrayList<>();
    List<coursemesBean> showmesBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kq_list);
        initView();
        setNavBarTitle("课程花名册");

        //用来显示相关信息
        //showToast("到此正常显示");
        refresh();
    }

    private void refresh(){
        HttpUtil.newInstance()
                .addParam("courseId",getIntent().getStringExtra(ActKeyConstants.KEY_ID))
                .post("getCourseMes", new HttpUtil.HttpListener() {
                    @Override
                    public void onSuccess(String t) {

                        handle(t);
                        //这里用来测试是否
                        showToast("success");
                    }

                    @Override
                    public void onError(String msg) {
                        showToast("not success");
                    }
                });
    }

    private void initView() {
        clv = (CommonList) findViewById(R.id.clv);
    }
    private void handle(String json){
        showmesBean= GsonUtil.parseStr2List(json, coursemesBean.class);
        listBeans.clear();
        clv.delAll();
        for (coursemesBean showBean:showmesBean){
            ListBean listBean=new ListBean();
            listBean.setTitle("课程编号: "+showBean.getCourseId() +"\t\t课程名字：" + showBean.getCourse_name());
            listBean.setContent("学生名字: "+showBean.getStu_name()+"\t\t学生学号: "+showBean.getStu_id()+"\t\t班级: "+ showBean.getStu_class());
            listBeans.add(listBean);
        }
        clv.addAll(listBeans);
    }

}
