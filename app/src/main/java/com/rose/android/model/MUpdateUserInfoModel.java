package com.rose.android.model;

/**
 * Created by xiaohuabu on 17/9/6.
 */

public class MUpdateUserInfoModel extends BaseModel {

    /**
     * code : 200
     * msg : success
     * data : {}
     */

    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
