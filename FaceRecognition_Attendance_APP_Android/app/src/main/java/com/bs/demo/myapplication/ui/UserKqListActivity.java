package com.bs.demo.myapplication.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.ActKeyConstants;
import com.bs.demo.myapplication.common.base.BaseActivity;
import com.bs.demo.myapplication.model.KqBean;
import com.bs.demo.myapplication.model.ListBean;
import com.bs.demo.myapplication.utils.DateUtil;
import com.bs.demo.myapplication.utils.GsonUtil;
import com.bs.demo.myapplication.utils.HttpUtil;
import com.bs.demo.myapplication.widget.CommonList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserKqListActivity extends BaseActivity {
    private CommonList clv;
    private List<ListBean> listBeans=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_kq_list);
        initView();
        setNavBarTitle("个人考勤历史");

        HttpUtil.newInstance()
                .addParam("id",getIntent().getStringExtra(ActKeyConstants.KEY_ID))
                .post("getUserkqList", new HttpUtil.HttpListener() {
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
        List<KqBean> kqBeans= GsonUtil.parseStr2List(json,KqBean.class);
        for (KqBean kqBean:kqBeans){
            ListBean listBean=new ListBean();
            listBean.setTitle("课程编号: "+kqBean.getKcId()+"   课程名称: "+kqBean.getKcname());
            long time=Long.valueOf(kqBean.getTime());
            listBean.setContent("\n签到情况: "+kqBean.getStatus() +"   签到时间: "+ DateUtil.date2Str(new Date(time),DateUtil.FORMAT_YMDHM)+"\n平时成绩: "+kqBean.getCj()
                    +"\n签到位置: "+kqBean.getAddr());
            listBeans.add(listBean);
        }
        clv.addAll(listBeans);
    }
}
