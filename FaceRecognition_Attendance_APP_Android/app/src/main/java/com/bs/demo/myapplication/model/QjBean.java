package com.bs.demo.myapplication.model;

/**
 * Author:  钟文清
 * Description:返回和获取签到表里的相关信息。这是一个可复用类Bean
 **/
public class QjBean {
    int id;
    String cotent;
    String kcid;
    String time;
    String userId;
    String userName;
    String kcName;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKcName() {
        return kcName;
    }

    public void setKcName(String kcName) {
        this.kcName = kcName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCotent() {
        return cotent;
    }

    public void setCotent(String cotent) {
        this.cotent = cotent;
    }

    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
