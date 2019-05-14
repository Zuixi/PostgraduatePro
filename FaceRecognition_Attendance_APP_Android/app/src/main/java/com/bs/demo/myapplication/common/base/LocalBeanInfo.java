package com.bs.demo.myapplication.common.base;

import com.bs.demo.myapplication.model.UserInfoBean;
import com.google.gson.Gson;

/**
 * Author :     钟文清
 * Description: 本地存储数据模型
 */
public class LocalBeanInfo {
    private final static String KEY_USER_INFO ="local_userinfo";
    public static String Addr="";

    public static UserInfoBean getUserInfo() {
        try {
            String is= TApplication.getInstance().getPreference().getString(KEY_USER_INFO,"");
            UserInfoBean userInfoBean=new Gson().fromJson(is,UserInfoBean.class);
            return userInfoBean;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    //更新用户数据，当存在用户注册或后台操纵用户表使表内数据更新时
    public static void refreshUserInfo(UserInfoBean userInfoBean){
        String content=new Gson().toJson(userInfoBean);
        TApplication.getInstance().getPreference().putString(KEY_USER_INFO,content).commit();
    }


}
