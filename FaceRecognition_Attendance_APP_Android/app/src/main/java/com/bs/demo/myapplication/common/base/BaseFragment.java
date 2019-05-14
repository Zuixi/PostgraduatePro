package com.bs.demo.myapplication.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Author:  钟文清
 * Description:自定义基础框架类，实现基本框架的信息获取
 **/
public class BaseFragment extends Fragment {
    public void startAct(Class<?> clazz, Bundle bundle) {
        // 传送基本数据
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    public void startAct(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    //使用toast显示提示信息
    public void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}
