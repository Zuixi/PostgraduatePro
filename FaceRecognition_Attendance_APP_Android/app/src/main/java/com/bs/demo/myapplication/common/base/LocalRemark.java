package com.bs.demo.myapplication.common.base;


/**
 * Author :     钟文清
 * Description: 临时存储
 */
public class LocalRemark {
    public final static String KEY_TOKEN ="remark_token";

    public static String getRemark(String key) {
        try {
            String is= TApplication.getInstance().getPreference().getString(key,"");
            return is;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
    public static void remark(String content,String key){
        TApplication.getInstance().getPreference().putString(key,content).commit();
    }
}
