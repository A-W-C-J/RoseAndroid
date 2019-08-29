package com.rose.android.ui.activity;

import com.rose.android.presenter.newstruct.UserInfoPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by wenen on 2017/12/29.
 */
public class CashActivityTest extends BaseActivityTest {
    CashActivity cashActivity;
    UserInfoPresenter userInfoPresenter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        cashActivity = Mockito.mock(CashActivity.class);
        userInfoPresenter = new UserInfoPresenter(httpClient, cashActivity);
    }

    @Test
    public void getWallet() throws Exception {
        userInfoPresenter.requestUserAccount();
        Mockito.verify(cashActivity).showNext(Mockito.anyObject(), Mockito.anyBoolean(), Mockito.anyObject());
    }

}
