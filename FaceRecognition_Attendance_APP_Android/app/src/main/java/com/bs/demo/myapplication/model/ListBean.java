package com.bs.demo.myapplication.model;

/**
 * Author:  钟文清
 * Description:返回和获取列表里的相关信息。这是一个可复用类Bean
 **/
public class ListBean {
    String title;
    String content;
    int icon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
