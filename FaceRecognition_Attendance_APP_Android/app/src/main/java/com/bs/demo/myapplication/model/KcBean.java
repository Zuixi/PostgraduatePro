package com.bs.demo.myapplication.model;

public class KcBean {
    int id;
    String name;
    String stime;
    String addr;
    String lsId;
    int week;
    int ytime;
    boolean openKq;

    public boolean isOpenKq() {
        return openKq;
    }

    public void setOpenKq(boolean openKq) {
        this.openKq = openKq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getLsId() {
        return lsId;
    }

    public void setLsId(String lsId) {
        this.lsId = lsId;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getYtime() {
        return ytime;
    }

    public void setYtime(int ytime) {
        this.ytime = ytime;
    }
}
