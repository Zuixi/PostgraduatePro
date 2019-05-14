package com.bs.demo.myapplication.ui.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.BaseActivity;
import com.bs.demo.myapplication.common.base.LocalBeanInfo;
import com.bs.demo.myapplication.model.UserInfoBean;
import com.bs.demo.myapplication.utils.HttpUtil;
import com.google.gson.Gson;

public class ResetPwdActivity extends BaseActivity {

    private EditText edt_reset_pwd1;
    private EditText edt_reset_pwd2;
    private TextView tvbtn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        initView();
        setNavBarTitle("修改密码");
    }

    private void initView() {
        edt_reset_pwd1 = (EditText) findViewById(R.id.edt_reset_pwd1);
        edt_reset_pwd2 = (EditText) findViewById(R.id.edt_reset_pwd2);
        tvbtn_next = (TextView) findViewById(R.id.tvbtn_next);
        tvbtn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {
        // validate
        String pwd1 = edt_reset_pwd1.getText().toString().trim();
        if (TextUtils.isEmpty(pwd1)) {
            showToast(edt_reset_pwd1.getHint().toString());
            return;
        }

        String pwd2 = edt_reset_pwd2.getText().toString().trim();
        if (TextUtils.isEmpty(pwd2)) {
            showToast(edt_reset_pwd2.getHint().toString());
            return;
        }

        if (!pwd1.equals(pwd2)) {
            showToast("两次输入的密码不一致");
            return;
        }
        HttpUtil.newInstance()
                .addParam("id", LocalBeanInfo.getUserInfo().getId())
                .addParam("password", pwd1)
                .post("resetPwd", new HttpUtil.HttpListener() {
                    @Override
                    public void onSuccess(String t) {
                        showToast("修改成功!");
                        finish();
                    }

                    @Override
                    public void onError(String msg) {
                        showToast("修改失败!");
                    }
                });


    }
}
