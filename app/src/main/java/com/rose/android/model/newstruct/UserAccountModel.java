package com.rose.android.model.newstruct;

import com.google.gson.Gson;
import com.rose.android.model.BaseModel;
import com.rose.android.model.newstruct.dto.UserAccountDTO;

public class UserAccountModel extends BaseModel {

    private UserAccountDTO data;

    public UserAccountDTO getData() {
        return data;
    }

    public void setData(UserAccountDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return "UserAccountModel:" + gson.toJson(this);
    }
}
