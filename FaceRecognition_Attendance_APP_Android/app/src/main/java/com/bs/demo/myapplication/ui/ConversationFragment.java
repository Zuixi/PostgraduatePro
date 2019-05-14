package com.bs.demo.myapplication.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bs.demo.myapplication.R;
import com.bs.demo.myapplication.common.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;


public class ConversationFragment extends BaseFragment {


    public static ConversationFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ConversationFragment fragment = new ConversationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_conversation, container, false);
        Map<String, Boolean> supportedConversation=new HashMap<>();
        supportedConversation.put(Conversation.ConversationType.PRIVATE.getName(), false);
        RongIM.getInstance().startSubConversationList(getContext(), Conversation.ConversationType.PRIVATE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }
}
