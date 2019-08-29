package com.rose.android.model.newstruct;

import com.google.gson.Gson;
import com.rose.android.model.BaseModel;
import com.rose.android.model.newstruct.dto.UserBankDTO;
import com.rose.android.model.newstruct.dto.UserDTO;

public class UserInfoModel extends BaseModel {
    public static class DataBean {
        private String token;
        private UserDTO user;
        private UserBankDTO user_bank;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserDTO getUser() {
            return user;
        }

        public void setUser(UserDTO user) {
            this.user = user;
        }

        public UserBankDTO getUser_bank() {
            return user_bank;
        }

        public void setUser_bank(UserBankDTO user_bank) {
            this.user_bank = user_bank;
        }
    }

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return "UserInfoModel:" + gson.toJson(this);
    }
}
