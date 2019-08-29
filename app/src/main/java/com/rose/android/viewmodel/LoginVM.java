package com.rose.android.viewmodel;

import com.rose.android.model.MLoginModel;
import com.rose.android.model.newstruct.UserInfoModel;
import com.rose.android.model.newstruct.UserSigninModel;
import com.rose.android.utils.Utils;

import java.math.BigDecimal;

/**
 * Created by xiaohuabu on 17/9/5.
 */

public class LoginVM {
    private static LoginVM loginVM;

    private LoginVM() {
    }

    public static LoginVM getInstance() {
        if (loginVM == null) {
            loginVM = new LoginVM();
        }
        return loginVM;
    }

    private MLoginModel loginModel;
    private UserSigninModel userSigninModel;

    public MLoginModel getLoginModel() {
        return loginModel;
    }

    public String getHeadUrl() {
        return loginModel.getData().getHeadUrl();
    }

    public void setHeadUrl(String url) {
        loginModel.getData().setHeadUrl(url);
    }

    public String getPhone() {
        if (loginModel != null && loginModel.getData() != null &&
                loginModel.getData().getPhone() != null &&
                loginModel.getData().getPhone().length() == 11) {
            char[] chars = loginModel.getData().getPhone().toCharArray();
            StringBuilder phone = new StringBuilder();
            for (int i = 3; i < 7; i++) {
                chars[i] = '*';
            }
            for (int j = 0; j < chars.length; j++) {
                phone.append(chars[j]);
            }
            return phone.toString();
        }
        return "";
    }

    public String getCashAsset() {
        if (loginModel != null) {
            BigDecimal bigDecimal = new BigDecimal(loginModel.getData().getCashAsset());
            return Utils.formatWithThousandsSeparator(bigDecimal.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_EVEN));
        } else {
            return "";
        }

    }

    public String getToken() {
        return loginModel.getData().getToken();
    }

    public void setLoginModel(MLoginModel loginModel) {
        this.loginModel = loginModel;
    }

    public UserSigninModel getUserSigninModel() {
        return userSigninModel;
    }

    public void setUserSigninModel(UserSigninModel userSigninModel) {
        this.userSigninModel = userSigninModel;
    }

    public void udpateUserInfo(UserInfoModel userInfoModel) {
        if (null != this.userSigninModel.getData()) {
            this.userSigninModel.getData().setUser(userInfoModel.getData().getUser());
        }
    }
}
