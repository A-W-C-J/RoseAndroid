package com.rose.android.ui.activity;

import com.rose.android.presenter.AuthPhonePresenter;
import com.rose.android.presenter.GetAuthCodePresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Created by wenen on 2017/12/29.
 */
public class AuthPhoneActivityTest extends BaseActivityTest {
    AuthPhoneActivity authPhoneActivity;
    AuthPhonePresenter authPhonePresenter;
    GetAuthCodePresenter getAuthCodePresenter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        authPhoneActivity = Mockito.mock(AuthPhoneActivity.class);
        authPhonePresenter = new AuthPhonePresenter(httpClient, authPhoneActivity);
        getAuthCodePresenter = new GetAuthCodePresenter(httpClient, authPhoneActivity);
    }

    @Test
    public void pathPhone() throws Exception {
        authPhonePresenter.pathPhone("1234", "123456", "123");
        Mockito.verify(authPhoneActivity).showNext(Mockito.anyObject(), Mockito.anyBoolean(), Mockito.anyObject());
    }

    @Test
    public void getAuthCode() throws Exception {
        getAuthCodePresenter.getAuthCode("17603091655", "FIND_BACK_LOGIN_PASSWORD");
        Mockito.verify(authPhoneActivity).requestCallBack(Mockito.anyObject());
    }

}
