package com.bs.demo.myapplication.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.baidu.aip.face.AipFace;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.ActKeyConstants;
import com.bs.demo.myapplication.common.base.BaseActivity;
import com.bs.demo.myapplication.common.base.LocalBeanInfo;
import com.bs.demo.myapplication.common.base.LocalRemark;
import com.bs.demo.myapplication.model.UserInfoBean;
import com.bs.demo.myapplication.ui.ConversationFragment;
import com.bs.demo.myapplication.ui.HomeFragment;
import com.bs.demo.myapplication.ui.MineFragment;
import com.bs.demo.myapplication.ui.XsInfoActivity;
import com.bs.demo.myapplication.ui.adapter.ConversationListAdapterEx;
import com.bs.demo.myapplication.utils.HttpUtil;
import com.bs.demo.myapplication.utils.ImageLoadUtil;
import com.bs.demo.myapplication.utils.LogUtil;
import com.bs.demo.myapplication.utils.PhotoPickDialogUtil;
import com.bs.demo.myapplication.widget.NavigationBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ContactNotificationMessage;

public class MainActivity extends BaseActivity{

    private ImageView iv_user_logo;
    private TextView tv_info;
    private TextView tv_reset_pwd;
    private TextView tv_logout;
    private TextView tv_name;

    private HomeFragment homeFragment;
    private MineFragment mineFragment;

    private BottomNavigationBar bottom_navigation_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        final LocationClient locationClient = new LocationClient(getApplicationContext());
        LocationClientOption locationOption = new LocationClientOption();
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationOption.setIsNeedAddress(true);
        locationClient.setLocOption(locationOption);
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                LocalBeanInfo.Addr=bdLocation.getAddrStr();
                locationClient.stop();
            }
        });
        locationClient.start();

        navigationBar.setRightBtnText("消息");
        navigationBar.setNavBarRightBtnListener(new NavigationBar.NavBarRightBtnListener() {
            @Override
            public void onClick() {
                Map<String, Boolean> supportedConversation=new HashMap<>();
                supportedConversation.put(Conversation.ConversationType.PRIVATE.getName(), false);
                RongIM.getInstance().startConversationList(MainActivity.this,supportedConversation);
            }
        });
        navigationBar.hideBack();
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().disconnect();
                RongIM.getInstance().logout();
                LocalBeanInfo.refreshUserInfo(null);
                startAct(LoginActivity.class);
                finish();
            }
        });
        iv_user_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickDialogUtil.newInstance().setSelectCount(1).setOnPickListener(new PhotoPickDialogUtil.OnPickListener() {
                    @Override
                    public void fromCamera(String path) {
                        upload(path);
                    }

                    @Override
                    public void fromGallery(List<String> paths) {

                        upload(paths.get(0));
                    }
                }).show(MainActivity.this);
            }

        });
        tv_reset_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAct(ResetPwdActivity.class);
            }
        });
        if (LocalBeanInfo.getUserInfo().getType().equals("3")){
            tv_info.setVisibility(View.GONE);
        }
        tv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LocalBeanInfo.getUserInfo().getType().equals("0")){
                    Bundle bundle=new Bundle();
                    bundle.putString(ActKeyConstants.KEY_ID,LocalBeanInfo.getUserInfo().getId());
                    startAct(XsInfoActivity.class,bundle);
                }else if (LocalBeanInfo.getUserInfo().getType().equals("1")){

                }
            }
        });
        initIm();


    }

    private void initIm() {
        RongIM.connect(LocalRemark.getRemark(LocalRemark.KEY_TOKEN), new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                LogUtil.d("onTokenIncorrect");
            }

            @Override
            public void onSuccess(String s) {
                LogUtil.d("IM登陆成功");


            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtil.d("IM登陆失败:"+errorCode.getValue()+"/"+errorCode.getMessage());
            }
        });
    }

    private void upload(String path) {
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {

                if(e==null){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            AipFace client = new AipFace(ActKeyConstants.APP_ID,ActKeyConstants.API_KEY, ActKeyConstants.SECRET_KEY);
                            HashMap<String, String> options = new HashMap<>();
                            options.put("user_info", LocalBeanInfo.getUserInfo().getName());
                            options.put("quality_control", "NORMAL");
                            options.put("liveness_control", "LOW");
                            String image =  bmobFile.getFileUrl();
                            String imageType = "URL";
                            String groupId = "groupzjw";
                            String userId = LocalBeanInfo.getUserInfo().getId();
                            JSONObject res = client.updateUser(image,imageType,groupId,userId,options);
                            LogUtil.d(res.toString());
                            try {
                                if (res.getString("error_code").equals("223103")){
                                    JSONObject res2 = client.addUser(image,imageType,groupId,userId,options);
                                    LogUtil.d(res2.toString());
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }).start();
                    HttpUtil.newInstance()
                            .addParam("id", LocalBeanInfo.getUserInfo().getId())
                            .addParam("logo", bmobFile.getFileUrl())
                            .post("resetLogo", new HttpUtil.HttpListener() {
                                @Override
                                public void onSuccess(String t) {
                                    ImageLoadUtil.loadCirclePic(MainActivity.this, bmobFile.getFileUrl(), iv_user_logo);
                                }

                                @Override
                                public void onError(String msg) {
                                }
                            });
                }else{
                    LogUtil.d(e.toString());
                    showToast("上传失败!");
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }

    private void initView() {
        iv_user_logo = (ImageView) findViewById(R.id.iv_user_logo);
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_reset_pwd = (TextView) findViewById(R.id.tv_reset_pwd);
        tv_logout = (TextView) findViewById(R.id.tv_logout);
        tv_name = (TextView) findViewById(R.id.tv_name);
        bottom_navigation_bar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottom_navigation_bar.setMode(BottomNavigationBar.MODE_FIXED);

        bottom_navigation_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setBarBackgroundColor(R.color.white)
                .addItem(new BottomNavigationItem(R.drawable.selector_tab_home, "课程表").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.color_999999))
                .setFirstSelectedPosition(0);
        if (LocalBeanInfo.getUserInfo().getType().equals("0")){
            bottom_navigation_bar.addItem(new BottomNavigationItem(R.drawable.selector_tab_mine, "我的").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.color_999999));
        }

        bottom_navigation_bar.initialise();
        bottom_navigation_bar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

                showFragment(position);
            }

            @Override
            public void onTabUnselected(int position) {


            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        showFragment(0);
    }

    public void showFragment(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        hideAllFragment(ft);
        switch (position) {
            case 0:
                setNavBarTitle("课程表");
                if (homeFragment != null) {
                    ft.show(homeFragment);
                } else {
                    homeFragment = HomeFragment.newInstance();
                    ft.add(R.id.frame_layout, homeFragment);
                }
                break;

            case 1:
                setNavBarTitle("我的");
                if (mineFragment != null) {
                    ft.show(mineFragment);
                } else {
                    mineFragment = MineFragment.newInstance();
                    ft.add(R.id.frame_layout, mineFragment);
                }
                break;

        }

        ft.commitAllowingStateLoss();
    }

    public void hideAllFragment(FragmentTransaction ft) {

        if (homeFragment != null) {
            ft.hide(homeFragment);
        }
        if (mineFragment != null) {
            ft.hide(mineFragment);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpUtil.newInstance()
                .addParam("id", LocalBeanInfo.getUserInfo().getId())
                .post("getUser", new HttpUtil.HttpListener() {
                    @Override
                    public void onSuccess(String t) {
                        UserInfoBean bean = new Gson().fromJson(t,UserInfoBean.class);
                        LocalBeanInfo.refreshUserInfo(bean);
                        if (bean.getLogo()==null||bean.getLogo().equals("")){
                            RongIM.getInstance().refreshUserInfoCache(new UserInfo(bean.getId(), bean.getName(), null));

                        }else {
                            RongIM.getInstance().refreshUserInfoCache(new UserInfo(bean.getId(), bean.getName(), Uri.parse(LocalBeanInfo.getUserInfo().getLogo())));

                        }
                        tv_name.setText(bean.getName());
                        if (bean.getLogo() != null) {
                            ImageLoadUtil.loadCirclePic(MainActivity.this, bean.getLogo(), iv_user_logo);
                        }
                        if (bean.getType().equals("0")) {
                            if (bean.getLogo() == null || bean.getLogo().equals("")) {
                                showToast("请在主页右侧上传真实个人头像");
                            }
                        }
                    }

                    @Override
                    public void onError(String msg) {
                    }
                });
    }

}
