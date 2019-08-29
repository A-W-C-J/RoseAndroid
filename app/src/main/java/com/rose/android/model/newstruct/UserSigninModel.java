package com.rose.android.model.newstruct;

import com.google.gson.Gson;
import com.rose.android.model.BaseModel;
import com.rose.android.model.newstruct.dto.UserDTO;

public class UserSigninModel extends BaseModel {
    public static class DataBean {
        private String token;
        private UserDTO user;

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
        return "UserSigninModel:" + gson.toJson(this);
    }
}
