package com.bs.demo.myapplication.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;

import com.bs.demo.myapplication.R;

/**
 * Author:  钟文清
 * Description:
 */
public class DividerUtils {
    public static DividerItemDecoration getDivider(Context context){
        DividerItemDecoration divider=new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(context, R.drawable.line_mainbg_v));
        return  divider;
    }
}
