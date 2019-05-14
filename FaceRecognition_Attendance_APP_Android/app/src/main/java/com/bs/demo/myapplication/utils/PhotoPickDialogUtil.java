package com.bs.demo.myapplication.utils;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.widget.dialog.PickPhotoDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Author :     钟文清
 * Description: 图片选择对话框
 */
public class PhotoPickDialogUtil {
    private static  PhotoPickDialogUtil photoPickDialogUtil;
    private OnPickListener onPickListener=null;
    private int selectCount=1;
    public static PhotoPickDialogUtil newInstance() {
        if (photoPickDialogUtil==null){
            photoPickDialogUtil=new PhotoPickDialogUtil();
        }
        return photoPickDialogUtil;
    }

    public PhotoPickDialogUtil setOnPickListener(OnPickListener onPickListener) {
        this.onPickListener = onPickListener;
        return this;
    }

    public interface OnPickListener{
        void fromCamera(String path);
        void fromGallery(List<String> paths);
    }
    public PhotoPickDialogUtil setSelectCount(int selectCount) {
        this.selectCount = selectCount;
        return this;
    }

    public void show(final Context context){
        if (selectCount<=0){
            return;
        }
        new RxPermissions((FragmentActivity) context)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        if (aBoolean) {
                            PickPhotoDialog.newInstance().setOnItemClickListener(new PickPhotoDialog.OnDialogItemClickListener() {
                                @Override
                                public void fromAlbum() {
                                    int p_color=context.getResources().getColor(R.color.colorPrimary);
                                    Album.image(context)
                                            .multipleChoice()
                                            .camera(false)
                                            .columnCount(4)
                                            .selectCount(selectCount)
                                            .widget(Widget.newDarkBuilder(context)
                                                    .toolBarColor(p_color)
                                                    .statusBarColor(context.getResources().getColor(R.color.colorPrimaryDark))
                                                    .bucketItemCheckSelector(Color.WHITE,p_color)
                                                    .mediaItemCheckSelector(Color.WHITE, p_color)
                                                    .title("相册").build())
                                            .onResult(new Action<ArrayList<AlbumFile>>() {
                                                @Override
                                                public void onAction(@NonNull ArrayList<AlbumFile> result) {
                                                    Iterator<AlbumFile> iterator=result.iterator();
                                                    List<String> list=new ArrayList<>();
                                                    while (iterator.hasNext()){
                                                        list.add(iterator.next().getPath());
                                                    }
                                                    if (onPickListener!=null) {
                                                        onPickListener.fromGallery(list);
                                                    }
                                                }
                                            }).start();
                                }

                                @Override
                                public void fromTakePhoto() {
                                    Album.camera(context)
                                            .image()
                                            .onResult(new Action<String>() {
                                        @Override
                                        public void onAction(@NonNull String result) {
                                            if (onPickListener!=null) {
                                                onPickListener.fromCamera(result);
                                            }
                                        }
                                    }).start();
                                }
                            }).show(((FragmentActivity) context).getSupportFragmentManager());

                        } else {
                        }
                    }
                });

    }

}
