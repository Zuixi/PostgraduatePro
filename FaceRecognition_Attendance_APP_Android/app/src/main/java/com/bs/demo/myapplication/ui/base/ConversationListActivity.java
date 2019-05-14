package com.bs.demo.myapplication.ui.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.BaseActivity;

public class ConversationListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        setNavBarTitle("会话列表");
    }
}
