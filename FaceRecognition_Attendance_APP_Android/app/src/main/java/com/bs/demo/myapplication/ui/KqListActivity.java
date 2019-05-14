package com.bs.demo.myapplication.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.ActKeyConstants;
import com.bs.demo.myapplication.common.base.BaseActivity;
import com.bs.demo.myapplication.common.base.LocalBeanInfo;
import com.bs.demo.myapplication.model.KqBean;
import com.bs.demo.myapplication.model.ListBean;
import com.bs.demo.myapplication.model.UserInfoBean;
import com.bs.demo.myapplication.utils.DateUtil;
import com.bs.demo.myapplication.utils.GsonUtil;
import com.bs.demo.myapplication.utils.HttpUtil;
import com.bs.demo.myapplication.widget.CommonList;
import com.bs.demo.myapplication.widget.dialog.BottomListDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KqListActivity extends BaseActivity {
    List<KqBean> kqBeans;
    private CommonList clv;
    private List<ListBean> listBeans=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kq_list);
        initView();
        setNavBarTitle("考勤情况");
        refresh();
        clv.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                BottomListDialog.newInstance()
                        .addItem("设置平时成绩")
                        .addItem("查看学生信息")
                        .addItem("修改考勤状态")
                        .setListener(new BottomListDialog.Listener() {
                            @Override
                            public void onItemClick(String name, int p) {
                                switch (p) {
                                    case 0:
                                        final EditText editText = new EditText(KqListActivity.this);
                                        AlertDialog.Builder inputDialog =
                                                new AlertDialog.Builder(KqListActivity.this);
                                        inputDialog.setTitle("请输入学生成绩").setView(editText);
                                        inputDialog.setPositiveButton("确定",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        String contet=editText.getText().toString();

                                                        HttpUtil.newInstance()
                                                                .addParam("id",kqBeans.get(position).getId()+"")
                                                                .addParam("cj",contet)
                                                                .post("resetKqCj", new HttpUtil.HttpListener() {
                                                                    @Override
                                                                    public void onSuccess(String t) {
                                                                        showToast("提交成功!");
                                                                        refresh();
                                                                    }

                                                                    @Override
                                                                    public void onError(String msg) {
                                                                        showToast("提交失败!");
                                                                    }
                                                                });

                                                    }
                                                }).show();
                                        break;
                                    case 1:
                                        Bundle bundle=new Bundle();
                                        bundle.putString(ActKeyConstants.KEY_ID, kqBeans.get(position).getUserId());
                                        startAct(XsInfoActivity.class,bundle);
                                        break;
                                    case 2:
                                        BottomListDialog.newInstance()
                                                .addItem("正常")
                                                .addItem("迟到")
                                                .addItem("旷课")
                                                .setListener(new BottomListDialog.Listener() {
                                                    @Override
                                                    public void onItemClick(String name, int p) {

                                                        HttpUtil.newInstance()
                                                                .addParam("id",kqBeans.get(position).getId()+"")
                                                                .addParam("status",name)
                                                                .post("resetKqStatus", new HttpUtil.HttpListener() {
                                                                    @Override
                                                                    public void onSuccess(String t) {
                                                                        showToast("提交成功!");
                                                                        refresh();
                                                                    }

                                                                    @Override
                                                                    public void onError(String msg) {
                                                                        showToast("提交失败!");
                                                                    }
                                                                });


                                                    }
                                                }).show(getSupportFragmentManager());
                                        break;

                                }
                            }
                        }).show(getSupportFragmentManager());

            }
        });
    }
    private void refresh(){

        HttpUtil.newInstance()
                .addParam("kcid",getIntent().getStringExtra(ActKeyConstants.KEY_ID))
                .post("getkckqList", new HttpUtil.HttpListener() {
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
        listBeans.clear();
        clv.delAll();
        kqBeans= GsonUtil.parseStr2List(json,KqBean.class);
        for (KqBean kqBean:kqBeans){
            ListBean listBean=new ListBean();
            listBean.setTitle("课程编号: "+kqBean.getKcId()+"   课程名称: "+kqBean.getKcname());
            long time=Long.valueOf(kqBean.getTime());
            listBean.setContent("学生名称: "+kqBean.getUserName()+"\n签到情况: "+kqBean.getStatus() +"   签到时间: "+ DateUtil.date2Str(new Date(time),DateUtil.FORMAT_YMDHM)+"\n平时成绩: "+kqBean.getCj()
                    +"\n签到位置: "+kqBean.getAddr());
            listBeans.add(listBean);
        }
        clv.addAll(listBeans);
    }
}
