package com.rose.android.ui.activity;

import com.rose.android.presenter.LoginPresenter;
import com.rose.android.ui.activity.newstruct.UserSigninActivity;
import com.rose.android.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by wenen on 2017/12/29.
 */
public class LoginActivityTest extends BaseActivityTest {
    UserSigninActivity userSigninActivity;
    LoginPresenter loginPresenter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        userSigninActivity = Mockito.mock(UserSigninActivity.class);
        loginPresenter = new LoginPresenter(httpClient, userSigninActivity);
    }

    @Test
    public void login() throws Exception {
        loginPresenter.login("17603091655", Utils.parseStrToMd5L32("wmj141519"));
        Mockito.verify(userSigninActivity).requestCallBack(Mockito.anyObject());
    }

    @Test
    public void loginOnError() throws Exception {
        loginPresenter.login("17603091655", Utils.parseStrToMd5L32("error"));
        Mockito.verify(userSigninActivity).showNext(Mockito.anyObject(), Mockito.anyBoolean(), Mockito.anyObject());
    }
}
