package com.rose.android.model;

/**
 * Created by xiaohuabu on 17/9/5.
 */

public class MLoginModel extends BaseModel {

    /**
     * code : 200
     * msg : success
     * data : {"token":"7ead7742b85545d49cc863106f9d97da","headUrl":"http://res.yibeidiao.com/user/userInfo_head.png","phone":"17603091636","cashAsset":0}
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
         * token : 7ead7742b85545d49cc863106f9d97da
         * headUrl : http://res.yibeidiao.com/user/userInfo_head.png
         * phone : 17603091636
         * cashAsset : 0
         */

        private String token;
        private String headUrl;
        private String phone;
        private int cashAsset;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getCashAsset() {
            return cashAsset;
        }

        public void setCashAsset(int cashAsset) {
            this.cashAsset = cashAsset;
        }
    }
}
