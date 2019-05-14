package com.bs.demo.myapplication.common.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.widget.NavigationBar;


/**
 * Author :     钟文清
 * Description: 自定义基类对话框
 */

public class BaseActivity extends FragmentActivity {
    public void startAct(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    public void startAct(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全局背景色
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(getResources().getColor(R.color.bg_main));
    }

    //使用toast显示提示信息
    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    public NavigationBar navigationBar;
    protected void setNavBarTitle(String title){
        try {
            navigationBar=findViewById(R.id.navbar);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (navigationBar==null){
            return;
        }

        navigationBar.setTitile(title);
        navigationBar.setTitileColor(R.color.white);
        navigationBar.setBackIcon(R.drawable.arrow_left);
        navigationBar.setNavBarBg(R.color.colorPrimary);
        navigationBar.setNavBarBackListener(new NavigationBar.NavBarBackListener() {
            @Override
            public void onClickBack() {
                finish();
            }
        });

    }
}
