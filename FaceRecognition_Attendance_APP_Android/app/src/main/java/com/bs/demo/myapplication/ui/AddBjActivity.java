package com.bs.demo.myapplication.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.BaseActivity;
import com.bs.demo.myapplication.common.base.LocalBeanInfo;
import com.bs.demo.myapplication.model.BjBean;
import com.bs.demo.myapplication.utils.DateUtil;
import com.bs.demo.myapplication.utils.HttpUtil;
import com.bs.demo.myapplication.widget.NavigationBar;
import com.google.gson.Gson;


//添加笔记模块
public class AddBjActivity extends BaseActivity {

    private EditText edt_add_title;
    private EditText edt_add_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bj);
        initView();
        setNavBarTitle("添加笔记");
        navigationBar.setRightBtnText("提交");
        navigationBar.setNavBarRightBtnListener(new NavigationBar.NavBarRightBtnListener() {
            @Override
            public void onClick() {
                submit();
            }
        });
    }

    private void initView() {
        edt_add_title = (EditText) findViewById(R.id.edt_add_title);
        edt_add_content = (EditText) findViewById(R.id.edt_add_content);
    }

    private void submit() {
        // validate
        String title = edt_add_title.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
            return;
        }

        String content = edt_add_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        BjBean bjBean=new BjBean();
        bjBean.setContent(content);
        bjBean.setTitle(title);
        bjBean.setUserId(LocalBeanInfo.getUserInfo().getId());
        bjBean.setDate(DateUtil.getCurDateStr());
        HttpUtil.newInstance()
                .addParam("info", new Gson().toJson(bjBean))
                .post("addBj", new HttpUtil.HttpListener() {
                    @Override
                    public void onSuccess(String t) {
                        showToast("提交成功!");
                    }

                    @Override
                    public void onError(String msg) {
                        showToast("提交失败!");
                    }
                });


    }
}
