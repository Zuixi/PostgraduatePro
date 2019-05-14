package com.bs.demo.myapplication.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.ActKeyConstants;
import com.bs.demo.myapplication.common.base.BaseActivity;
import com.bs.demo.myapplication.common.base.LocalBeanInfo;
import com.bs.demo.myapplication.model.UserInfoBean;
import com.bs.demo.myapplication.ui.base.MainActivity;
import com.bs.demo.myapplication.utils.HttpUtil;
import com.bs.demo.myapplication.utils.ImageLoadUtil;
import com.bs.demo.myapplication.widget.NavigationBar;
import com.bs.demo.myapplication.widget.dialog.BottomListDialog;
import com.google.gson.Gson;

public class XsInfoActivity extends BaseActivity {

    private TextView edt_id;
    private EditText edt_name;
    private TextView edt_sex;
    private TextView edt_job;
    private String id;
    UserInfoBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_info);
        initView();
        setNavBarTitle("个人信息");
        id=getIntent().getStringExtra(ActKeyConstants.KEY_ID);
        HttpUtil.newInstance()
                .addParam("id",id)
                .post("getUser", new HttpUtil.HttpListener() {
                    @Override
                    public void onSuccess(String t) {
                        bean = new Gson().fromJson(t,UserInfoBean.class);
                        edt_id.setText(bean.getId());
                        edt_sex.setText(bean.getSex());
                        edt_job.setText(bean.getJob());
                        edt_name.setText(bean.getName());
                    }

                    @Override
                    public void onError(String msg) {
                    }
                });
        navigationBar.setRightBtnText("保存");
        navigationBar.setNavBarRightBtnListener(new NavigationBar.NavBarRightBtnListener() {
            @Override
            public void onClick() {
                submit();
            }
        });
        edt_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomListDialog.newInstance()
                        .addItem("男")
                        .addItem("女")
                        .setListener(new BottomListDialog.Listener() {
                            @Override
                            public void onItemClick(String name, int p) {
                                edt_sex.setText(name);
                                bean.setSex(name);
                            }
                        }).show(getSupportFragmentManager());
            }
        });
        edt_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getType().equals("0")){
                    BottomListDialog.newInstance()
                            .addItem("大一")
                            .addItem("大二")
                            .addItem("大三")
                            .addItem("大四")
                            .setListener(new BottomListDialog.Listener() {
                                @Override
                                public void onItemClick(String name, int p) {
                                    edt_job.setText(name);
                                    bean.setJob(name);

                                }
                            }).show(getSupportFragmentManager());
                }else if (bean.getType().equals("1")){
                    BottomListDialog.newInstance()
                            .addItem("C语言老师")
                            .addItem("英语老师")
                            .addItem("体育老师")
                            .addItem("音乐老师")
                            .addItem("数学老师")
                            .addItem("地理老师")
                            .setListener(new BottomListDialog.Listener() {
                                @Override
                                public void onItemClick(String name, int p) {
                                    edt_job.setText(name);
                                    bean.setJob(name);

                                }
                            }).show(getSupportFragmentManager());
                }

            }
        });

    }

    private void initView() {
        edt_id = (TextView) findViewById(R.id.edt_id);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_sex = (TextView) findViewById(R.id.edt_sex);
        edt_job = (TextView) findViewById(R.id.edt_job);
    }

    private void submit() {
        // validate
        String name = edt_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        bean.setName(name);
        HttpUtil.newInstance()
                .addParam("info", new Gson().toJson(bean))
                .post("resetInfo", new HttpUtil.HttpListener() {
                    @Override
                    public void onSuccess(String t) {
                        showToast("保存成功!");
                    }

                    @Override
                    public void onError(String msg) {
                    }
                });
    }
}
