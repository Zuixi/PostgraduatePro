package com.bs.demo.myapplication.common.base;

import android.app.Application;
import android.net.Uri;
import android.os.Parcel;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.bs.demo.myapplication.common.base.BasePreference;
import com.bs.demo.myapplication.utils.MediaLoader;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;

import cn.bmob.v3.Bmob;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

import static android.content.ContentResolver.SCHEME_FILE;

/**
 * Author:  钟文清
 * Description:
 */
public class TApplication extends Application{

    private static TApplication mApplication;
    private static BasePreference preference;



    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        preference = new BasePreference(this);
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);
        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.getDefault())
                .build());
        Bmob.initialize(this, "5317ea4e9f9231768387ca3ebca9bd32");
        RongIM.init(this);
        RongIM.getInstance().setMessageAttachedUserInfo(true);
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String userId) {
                String url= LocalBeanInfo.getUserInfo().getLogo();
                UserInfo userInfo;
                if (url==null||url.equals("")){
                    userInfo= new UserInfo(LocalBeanInfo.getUserInfo().getId(),LocalBeanInfo.getUserInfo().getName(),null);
                }else {
                    userInfo=new UserInfo(LocalBeanInfo.getUserInfo().getId(),LocalBeanInfo.getUserInfo().getName(),Uri.parse(url));
                }

                return userInfo;//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
            }

        }, true);
    }


    public static TApplication getInstance() {
        if (mApplication == null) {
            mApplication = new TApplication();
        }
        return mApplication;
    }

    public BasePreference getPreference() {
        if (preference == null) {
            preference = new BasePreference(this);
        }
        return preference;
    }


}
