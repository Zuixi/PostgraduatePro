package com.bs.demo.myapplication.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.ActKeyConstants;
import com.bs.demo.myapplication.common.base.BaseActivity;
import com.bs.demo.myapplication.common.base.LocalBeanInfo;
import com.bs.demo.myapplication.model.BjBean;
import com.bs.demo.myapplication.model.ListBean;
import com.bs.demo.myapplication.model.coursemesBean;
import com.bs.demo.myapplication.utils.DateUtil;
import com.bs.demo.myapplication.utils.GsonUtil;
import com.bs.demo.myapplication.utils.HttpUtil;
import com.bs.demo.myapplication.widget.CommonList;
import com.bs.demo.myapplication.widget.NavigationBar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


//花名册显示模块
public class MesListActivity extends BaseActivity {

    private CommonList clv;

    private List<ListBean> listBeans=new ArrayList<>();
    List<coursemesBean> showmesBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kq_list);
        initView();
        setNavBarTitle("课程花名册");

        //用来显示相关信息
        showToast("到此正常显示");
        refresh();
    }

    private void initView() {
        clv = (CommonList) findViewById(R.id.clv);
    }

    private void refresh(){
        HttpUtil.newInstance()
                .addParam("course_name",getIntent().getStringExtra(ActKeyConstants.KEY_ID))
                .post("getCourseMes", new HttpUtil.HttpListener() {
                    @Override
                    public void onSuccess(String t) {

                        handle(t);
                        showToast("success");
                    }

                    @Override
                    public void onError(String msg) {
                        showToast("not success");
                    }
                });
    }

    private void handle(String json){
        showmesBean= GsonUtil.parseStr2List(json, coursemesBean.class);
        listBeans.clear();
        clv.delAll();
        for (coursemesBean showBean:showmesBean){
            ListBean listBean=new ListBean();
            listBean.setTitle("课程名称: "+showBean.getCourse_name());
            listBean.setContent("学生名字: "+showBean.getStu_name()+"\n学生学号 "+showBean.getStu_id()+"班级: "+ showBean.getStu_class());
            listBeans.add(listBean);
        }
        clv.addAll(listBeans);
    }

}
