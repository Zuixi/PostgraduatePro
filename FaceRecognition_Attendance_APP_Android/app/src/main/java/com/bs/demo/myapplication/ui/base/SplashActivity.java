package com.bs.demo.myapplication.ui.base;

import android.Manifest;
import android.os.Bundle;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.BaseActivity;
import com.bs.demo.myapplication.common.base.LocalBeanInfo;
import com.bs.demo.myapplication.model.UserInfoBean;
import com.bs.demo.myapplication.utils.RxCountDownUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION
                ,Manifest.permission.ACCESS_COARSE_LOCATION
                ,Manifest.permission.READ_PHONE_STATE
                ,Manifest.permission.CAMERA
                ,Manifest.permission.READ_EXTERNAL_STORAGE
                ,Manifest.permission.READ_PHONE_STATE
                ,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                if (aBoolean) {
                    new RxCountDownUtils().countdown(3, new RxCountDownUtils.RxCountdownFinishedListener() {
                        @Override
                        public void onFinished() {
                            UserInfoBean bean=LocalBeanInfo.getUserInfo();
                            if (bean==null){
                                startAct(LoginActivity.class);

                            }else {
                                startAct(MainActivity.class);
//                                if (bean.getType().equals("0")){
//
//                                }else if (bean.getType().equals("1")){
//                                    startAct(LsMainActivity.class);
//                                }else if (bean.getType().equals("3")){
//                                    startAct(AdminMainActivity.class);
//                                }
                            }
                            finish();
                        }

                        @Override
                        public void onExecute(Long aLong) {

                        }
                    });
                }else {
                    finish();
                }
            }
        });
    }
}
