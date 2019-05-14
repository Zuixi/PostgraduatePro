package com.bs.demo.myapplication.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.ActKeyConstants;
import com.bs.demo.myapplication.common.base.BaseFragment;
import com.bs.demo.myapplication.common.base.LocalBeanInfo;
import com.bs.demo.myapplication.model.QjBean;
import com.bs.demo.myapplication.model.KcBean;
import com.bs.demo.myapplication.model.UserInfoBean;
import com.bs.demo.myapplication.utils.DateUtil;
import com.bs.demo.myapplication.utils.GsonUtil;
import com.bs.demo.myapplication.utils.HttpUtil;
import com.bs.demo.myapplication.utils.LogUtil;
import com.bs.demo.myapplication.utils.PhotoPickDialogUtil;
import com.bs.demo.myapplication.widget.dialog.BottomListDialog;
import com.google.gson.Gson;
import com.rmondjone.locktableview.DisplayUtil;
import com.rmondjone.locktableview.LockTableView;
import com.rmondjone.xrecyclerview.ProgressStyle;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class HomeFragment extends BaseFragment {
    final ArrayList<ArrayList<String>> mTableDatas = new ArrayList<>();
    private LinearLayout mContentView;
    private String[] week_names={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
    LockTableView mLockTableView;

    /**
     * author   钟文清
     * @return 返回一个基础界面框架
     */
    public static HomeFragment newInstance() {

        //创建一个框架界面实例
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        mContentView = view.findViewById(R.id.contentView);
        initView();
        return view;
    }

    // 用来初始化布局，app登录进去之后所看到的界面

    /**
     *
     */
    private void initView() {
        initDisplayOpinion();
        ArrayList<String> mfristData = new ArrayList<>();
        mfristData.add("时间/星期");
        for (int i = 0; i < 7; i++) {
            mfristData.add(week_names[i]);
        }
        mTableDatas.add(mfristData);
        for (int i = 0; i <=7 ; i++) {
            ArrayList<String> mRowDatas = new ArrayList<String>();
            mRowDatas.add("第"+(i+1)+"节");
            for (int j = 0; j < 7; j++) {
                mRowDatas.add("");
            }
            mTableDatas.add(mRowDatas);
        }
        mLockTableView = new LockTableView(getContext(), mContentView, mTableDatas);
        mLockTableView.setLockFristColumn(true) //是否锁定第一列
                .setLockFristRow(true) //是否锁定第一行
                .setMaxColumnWidth(80) //列最大宽度
                .setMinColumnWidth(80) //列最小宽度
                .setMinRowHeight(40)//行最小高度
                .setMaxRowHeight(40)//行最大高度
                .setTextViewSize(14) //单元格字体大小
                .setFristRowBackGroudColor(R.color.table_head)//表头背景色
                .setTableHeadTextColor(R.color.beijin)//表头字体颜色
                .setTableContentTextColor(R.color.border_color)//单元格字体颜色
                .setCellPadding(8)//设置单元格内边距(dp)
                .setNullableString("  ") //这里显示的是课程表默认信息，空格代表无课
                .setOnItemSelectedListenter(new LockTableView.OnItemSelectedListenter() {

                    /**
                     * @param item 课程单元格单位
                     * @param y   课程上课节数定位符
                     * @param x   课程上课周几定位符
                     * description ： 判定是学生用户还是教师用户，并且在指定的单元格内加入指定的课程
                     */
                    @Override
                    public void onItemSelected(final View item, final int y, final int x) {
                        KcBean kcBean = null;
                        for (KcBean bean:kcBeans){
                            if (bean.getWeek()==x&&bean.getYtime()==y){
                                kcBean=bean;
                            }
                        }
                        //LogUtil.d("x:"+x+"/y:"+y); 这句用来测试位置是否正确
                        if (kcBean==null) {
                            return;
                        }
                        //获取课程名字
                        LogUtil.d(kcBean.getName());

                        /**
                         * 根据用户的类型确定登录后所呈现的界面。
                         * type 0  则是代表学生
                         * type 1 代表教师
                         */
                        final UserInfoBean bean=LocalBeanInfo.getUserInfo();
                        if (bean.getType().equals("0")){
                            final KcBean finalKcBean = kcBean;
                            /**
                             * 使用BottomListDealog添加学生用户点击课程后所弹出的功能选项
                             */
                            BottomListDialog.newInstance()
                                    .addItem("签到")
                                    .addItem("请假")
                                    .addItem("上课提醒")
                                    .addItem("课内交流")
                                    .setListener(new BottomListDialog.Listener() {
                                        @Override
                                        public void onItemClick(String name, int p) {
                                            switch (p) {
                                                case 0:
                                                    //用户还未上传自己的头像时提示用户上传头像，便于考勤签到后续操作
                                                    if (bean.getLogo()==null||bean.getLogo().equals("")){
                                                        showToast("请在主页右侧上方上传真实个人头像");
                                                        return;
                                                    }
                                                    if (bean.getName()==null||bean.getName().equals("")){
                                                        showToast("请在个人信息界面中设置姓名");
                                                        return;
                                                    }
                                                    PhotoPickDialogUtil.newInstance().setSelectCount(1).setOnPickListener(new PhotoPickDialogUtil.OnPickListener() {
                                                        @Override
                                                        public void fromCamera(String path) {
                                                            upload(path, finalKcBean);
                                                        }

                                                        @Override
                                                        public void fromGallery(List<String> paths) {

                                                            upload(paths.get(0), finalKcBean);
                                                        }
                                                    }).show(getContext());
                                                    break;
                                                case 1:
                                                    final QjBean qjBean=new QjBean();
                                                    qjBean.setUserId(LocalBeanInfo.getUserInfo().getId());
                                                    qjBean.setUserName(LocalBeanInfo.getUserInfo().getName());
                                                    qjBean.setKcid(finalKcBean.getId()+"");
                                                    qjBean.setKcName(finalKcBean.getName());

                                                    final EditText editText = new EditText(getContext());
                                                    AlertDialog.Builder inputDialog =
                                                            new AlertDialog.Builder(getContext());
                                                    inputDialog.setTitle("请输入请假内容").setView(editText);
                                                    inputDialog.setPositiveButton("确定",
                                                            new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                    String contet=editText.getText().toString();
                                                                    qjBean.setTime(DateUtil.getCurDateStr(DateUtil.FORMAT_YMDHM));
                                                                    qjBean.setCotent(contet);
                                                                    HttpUtil.newInstance()
                                                                            .addParam("info",new Gson().toJson(qjBean))
                                                                            .post("addqj", new HttpUtil.HttpListener() {
                                                                                @Override
                                                                                public void onSuccess(String t) {
                                                                                    showToast("提交成功!");
                                                                                }

                                                                                @Override
                                                                                public void onError(String msg) {
                                                                                    showToast("提交失败!");
                                                                                }
                                                                            });

                                                                }
                                                            }).show();
                                                    break;
                                                case 2:
                                                    startAct(SetLockMainActivity.class);
                                                    break;
                                                case 3:
                                                    RongIM.getInstance().startPrivateChat(getContext(),finalKcBean.getLsId(),finalKcBean.getName()+"老师");
                                                    break;
                                            }
                                        }
                                    }).show(getChildFragmentManager());
                        }
                        /**
                         * 登录用户是教师所呈现的界面
                         */
                        else if (bean.getType().equals("1")){
                            final KcBean finalKcBean1 = kcBean;
                            if (!finalKcBean1.getLsId().equals(LocalBeanInfo.getUserInfo().getId())){
                                showToast("没有权限");
                                return;
                            }
                            BottomListDialog.newInstance()
                                    //这个开启关闭功能暂时还没有实现，时间不够可以删除
                                    .addItem("开启/关闭签到")
                                    .addItem("查看签到情况")
                                    .addItem("查看请假情况")
                                   // .addItem("设置上课提醒")
                                    .addItem("查看花名册")
                                    .setListener(new BottomListDialog.Listener() {
                                        @Override
                                        public void onItemClick(String name, int p) {
                                            Bundle bundle=new Bundle();
                                            switch (p) {
                                                case 0:
                                                    BottomListDialog.newInstance()
                                                            .addItem("开启")
                                                            .addItem("关闭")
                                                            .setListener(new BottomListDialog.Listener() {
                                                                @Override
                                                                public void onItemClick(String name, int p) {
                                                                    switch (p) {
                                                                        case 0:
                                                                            setIsOpen(true,finalKcBean1.getId()+"");
                                                                            break;
                                                                        case 1:
                                                                            setIsOpen(false,finalKcBean1.getId()+"");
                                                                            break;

                                                                    }
                                                                }
                                                            }).show(getChildFragmentManager());
                                                    break;
                                                case 1:
                                                    bundle.putString(ActKeyConstants.KEY_ID, finalKcBean1.getId()+"");
                                                    startAct(KqListActivity.class,bundle);
                                                    break;
                                                case 2:
                                                    bundle.putString(ActKeyConstants.KEY_ID, finalKcBean1.getId()+"");
                                                    startAct(KcQjListActivity.class,bundle);
                                                    break;
                                                    /*
                                                case 3:
                                                    startAct(SetLockMainActivity.class);
                                                    break;
                                                    */
                                                case 3:
                                                    //showToast(finalKcBean1.getName());
                                                    bundle.putString(ActKeyConstants.KEY_ID,finalKcBean1.getId()+"");
                                                    //showToast("success");
                                                    startAct(showCourseMesActivity.class,bundle);
                                                    break;

                                            }
                                        }
                                    }).show(getChildFragmentManager());
                        }
                    }
                })
                .setOnItemSeletor(R.color.dashline_color)//设置Item被选中颜色
                .show(); //显示表格,此方法必须调用
        mLockTableView.getTableScrollView().setPullRefreshEnabled(false);
        mLockTableView.getTableScrollView().setLoadingMoreEnabled(false);
        mLockTableView.getTableScrollView().setRefreshProgressStyle(ProgressStyle.SquareSpin);
    }

    private void setIsOpen(boolean b,String kcid) {
        HttpUtil.newInstance()
                .addParam("isopen",b+"")
                .addParam("kcid",kcid)
                .post("resetKqOpen", new HttpUtil.HttpListener() {
                    @Override
                    public void onSuccess(String t) {
                        showToast("设置成功!");
                    }

                    @Override
                    public void onError(String msg) {
                        showToast("设置失败!");
                    }
                });
    }

    List<KcBean> kcBeans;
    @Override
    public void onResume() {
        super.onResume();
        HttpUtil.newInstance()
                .post("getKcList", new HttpUtil.HttpListener() {
                    @Override
                    public void onSuccess(String t) {
                        mTableDatas.clear();
                        kcBeans= GsonUtil.parseStr2List(t,KcBean.class);
                        ArrayList<String> mfristData = new ArrayList<>();
                        mfristData.add("时间/星期");
                        for (int i = 0; i < 7; i++) {
                            mfristData.add(week_names[i]);
                        }
                        mTableDatas.add(mfristData);
                        for (int i = 0; i <= 7; i++) {
                            ArrayList<String> mRowDatas = new ArrayList<String>();
                            mRowDatas.add("第"+(i+1)+"讲");
                            for (int j = 0; j < 7; j++) {
                                mRowDatas.add("");
                            }
                            mTableDatas.add(mRowDatas);
                        }
                        // 从后台数据库里读取设置好的课程到手机app中
                        for (KcBean kcBean:kcBeans){
                            mTableDatas.get(kcBean.getYtime()+1).set(kcBean.getWeek()+1,kcBean.getName());
                        }
                        mLockTableView.setTableDatas(mTableDatas);
                    }

                    @Override
                    public void onError(String msg) {
                    }
                });
    }

    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getContext(), dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(getContext(), dm.heightPixels);
    }
    private void upload(String path, final KcBean bean) {
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    Observable.create(new ObservableOnSubscribe<String>() {
                        @Override
                        public void subscribe(final ObservableEmitter<String> emitter) {
                            AipFace client = new AipFace(ActKeyConstants.APP_ID,ActKeyConstants.API_KEY, ActKeyConstants.SECRET_KEY);
                            MatchRequest req1 = new MatchRequest(LocalBeanInfo.getUserInfo().getLogo(), "URL");
                            MatchRequest req2 = new MatchRequest(bmobFile.getFileUrl(), "URL");
                            ArrayList<MatchRequest> requests = new ArrayList<>();
                            requests.add(req1);
                            requests.add(req2);
                            JSONObject res = client.match(requests);
                            try {
                                JSONObject result=res.getJSONObject("result");
                                emitter.onNext(result.getString("score"));
                            } catch (Exception e1) {
                                e1.printStackTrace();

                            }
                        }
                    }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) {
                                    Double re=Double.valueOf(s);
                                    // 如果两者相似度大于80%，则认为是同一个人，否则识别失败
                                    if (re>80){
                                        signUp(bean);
                                    }else {
                                        showToast("识别失败,验证不通过!");
                                    }
                                }
                            });
                }else{
                    showToast("上传失败!");
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }

    //在课程签到时根据识别精度判断是否是同一个人，若是，则识别通过，否则是签到失败
    private void signUp(KcBean kcBean) {
        HttpUtil.newInstance()
                .addParam("userId", LocalBeanInfo.getUserInfo().getId())
                .addParam("userName", LocalBeanInfo.getUserInfo().getName())
                .addParam("time", System.currentTimeMillis()+"")
                .addParam("kcinfo",new Gson().toJson(kcBean))
                .addParam("addr",LocalBeanInfo.Addr)    //显示考勤地址信息
                .post("addkq", new HttpUtil.HttpListener() {
                    @Override
                    public void onSuccess(String t) {
                        showToast("识别成功,签到通过!");
                    }

                    @Override
                    public void onError(String msg) {
                        showToast("，识别失败，签到失败!");
                    }
                });
    }
}
