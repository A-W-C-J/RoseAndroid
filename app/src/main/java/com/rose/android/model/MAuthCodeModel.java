package com.rose.android.model;

/**
 * Created by xiaohuabu on 17/9/5.
 */

public class MAuthCodeModel extends BaseModel {

    /**
     * code : 200
     * msg : success
     * data : {"captcha":"31976","duration":5}
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
        /**
         * captcha : 391976
         * duration : 5
         */

        private String captcha;
        private int duration;

        public String getCaptcha() {
            return captcha;
        }

        public void setCaptcha(String captcha) {
            this.captcha = captcha;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }
}
