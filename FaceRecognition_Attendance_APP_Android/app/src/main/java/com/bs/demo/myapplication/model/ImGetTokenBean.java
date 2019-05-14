package com.bs.demo.myapplication.model;

public class ImGetTokenBean {

    /**
     * code : 200
     * userId : 2
     * token : jWHPlBNcBddKE4tnMlRQ/AzUZGBb+oTHbr2Kr6HRRTUUQZIQqmcnDXxpa/BoTXbtk/26EQ+rx2b2/c6gSRpOYg==
     * 上传头像到云端
     */

    private int code;
    private String userId;
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
