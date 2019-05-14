package com.bs.demo.myapplication.ui.base;

import android.os.Bundle;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.BaseActivity;
import com.bs.demo.myapplication.model.ListBean;
import com.bs.demo.myapplication.model.UserInfoBean;
import com.bs.demo.myapplication.utils.GsonUtil;
import com.bs.demo.myapplication.utils.HttpUtil;
import com.bs.demo.myapplication.widget.CommonList;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends BaseActivity {

    private CommonList clv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        initView();
        setNavBarTitle("列表");
        HttpUtil.newInstance()
                .post("getAllUser", new HttpUtil.HttpListener() {
                    @Override
                    public void onSuccess(String t) {
                        List<UserInfoBean> userInfoBeans=GsonUtil.parseStr2List(t,UserInfoBean.class);
                        List<ListBean> listBeans=new ArrayList<>();
                        for (UserInfoBean u:userInfoBeans){
                            ListBean listBean=new ListBean();
                            listBean.setTitle(u.getName());
                            listBean.setContent("编号: "+u.getId());
                            listBeans.add(listBean);
                        }
                        clv.addAll(listBeans);
                    }

                    @Override
                    public void onError(String msg) {

                    }
                });

    }

    private void initView() {
        clv = (CommonList) findViewById(R.id.clv);
    }
}
