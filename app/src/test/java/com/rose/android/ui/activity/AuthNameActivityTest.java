package com.rose.android.ui.activity;

import com.rose.android.presenter.AuthRealNamePresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Created by wenen on 2017/12/29.
 */
public class AuthNameActivityTest extends BaseActivityTest {
    AuthNameActivity authNameActivity;
    AuthRealNamePresenter authRealNamePresenter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        authNameActivity = Mockito.mock(AuthNameActivity.class);
        authRealNamePresenter = new AuthRealNamePresenter(authNameActivity, httpClient);
    }
    @Test
    public void postRealName() throws Exception {
        authRealNamePresenter.postRealName("Wenen", "123455");
        Mockito.verify(authNameActivity).showNext(Mockito.anyObject(), Mockito.anyBoolean(), Mockito.anyObject());
    }

}
