package com.bs.demo.myapplication.ui.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.BaseActivity;
import com.bs.demo.myapplication.utils.HttpUtil;

public class RegisterActivity extends BaseActivity {

    private EditText edt_reg_account;
    private EditText edt_reg_pwd;
    private TextView tvbtn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setNavBarTitle("注册");
        initView();
    }

    private void initView() {

        edt_reg_account = (EditText) findViewById(R.id.edt_reg_account);
        edt_reg_pwd = (EditText) findViewById(R.id.edt_reg_pwd);
        tvbtn_next = (TextView) findViewById(R.id.tvbtn_next);
        tvbtn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {
        String account = edt_reg_account.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            showToast(edt_reg_account.getHint().toString());
            return;
        }

        String pwd = edt_reg_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            showToast(edt_reg_pwd.getHint().toString());
            return;
        }

        HttpUtil.newInstance()
                .addParam("account", account)
                .addParam("password", pwd)
                .post("register", new HttpUtil.HttpListener() {
                    @Override
                    public void onSuccess(String t) {
                        showToast("注册成功!");
                        finish();
                    }

                    @Override
                    public void onError(String msg) {
                        showToast("注册失败");
                    }
                });

    }
}
