package com.bs.demo.myapplication.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * Author:  钟文清
 * Description:用于不同的头像框加载头像的工具类
 */
public class ImageLoadUtil {


    /**
     * 普通图片加载
     */
    public static void loadPic(Context c,String url,ImageView iv){
        Glide.with(c).load(url).apply(new RequestOptions().transforms(new CenterCrop())).into(iv);
    }

    /**
     * 圆形图片加载
     */
    public static void loadCirclePic(Context c,String url,ImageView iv){
        Glide.with(c).load(url).apply(new RequestOptions().transforms(new CenterCrop(),new CircleCrop())).into(iv);
    }
    /**
     * 圆角图片加载
     */
    public static void loadPic(Context c,String url,int r,ImageView iv){
        Glide.with(c).load(url).apply(new RequestOptions().transforms(new CenterCrop(),new RoundedCorners(r))).into(iv);
    }
}
